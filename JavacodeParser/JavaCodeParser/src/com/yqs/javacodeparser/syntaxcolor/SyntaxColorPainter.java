package com.yqs.javacodeparser.syntaxcolor;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import com.yqs.javacodeparser.rule.IRule;
import com.yqs.javacodeparser.scanner.Scanner;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.token.Token;

/**
 *  为Token着色
 * @author YangQingshuai
 *
 */
public class SyntaxColorPainter {

	public static final String DEFAULT       = "default";
	
	private SyntaxColorStyle   defaultStyle  = StyleFactory.fromDefault();
	
	private EditText           editText       = null;
	
	private List<Token>        tokenList      = new ArrayList<Token>();
	
	private Scanner            scanner        = null;
	
	private List<IRule>        rules          = new ArrayList<IRule>(); 
	
	private int                lastPos        = 0;
	
	public SyntaxColorPainter(EditText teditText){
		
		this.editText = teditText;
		
		scanner = new Scanner();
		
		editText.addTextChangedListener(new TextWatcher() {
			
			private int offset = 0;
			private int length = 0;
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {	

				//arg3是新添加的字符个数，arg2是删除的字符个数
				offset = arg1;
				if(arg3!=0){
					length = arg3;
				}
				else {
					length = -arg2;
				}
			}
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//do nothing
			}
			
			@Override
			public void afterTextChanged(Editable code) {
				
				processPaint(code, offset, length);
			}
		});
	}
	
	public void processPaint(Editable code, final int startOffset, final int shift){
		
		Token firstAffectToken = null;
		int index = 0;
		
		if((startOffset == lastPos || (shift < 0 && startOffset==lastPos-1)) && !tokenList.isEmpty()){   //若是在最后修改 则不必查找了
			firstAffectToken = tokenList.get(tokenList.size()-1);
			index = tokenList.size()-1;
		}
		else {
			for(Token t : tokenList){
				//修改了这这一分段的中间部分
				if(t.getOffset() < startOffset && t.getOffset() + t.getLength() > startOffset){
					firstAffectToken = t;
					break;
				}
				if(t.getOffset()==startOffset && index > 0){
					firstAffectToken = tokenList.get(--index);
					break;
				}
				if(t.getOffset()+t.getLength()==startOffset && index > 0){
					firstAffectToken = tokenList.get(--index);
					break;
				}
				
				++index;
			}//for(t)
		}
			
		//最开始没有分段
		if(null == firstAffectToken){
			index = 0;
			firstAffectToken = new Token(0, 0, DEFAULT, defaultStyle);
		}
		
		//找到了第一个可能受影响的Token，根据需要依次检查看是否需要修改其后的Token
		setSyntaxColor(code, firstAffectToken, index, shift);
		
		if(!tokenList.isEmpty()){
			lastPos = tokenList.get(tokenList.size()-1).getOffset() 
					+ tokenList.get(tokenList.size()-1).getLength();
		}
		else {
			lastPos = 0;
		}

	}
	
//	protected void setSyntaxColor(Editable code, Token starToken, int affectIndex, int shift) {
//		
//		Token defauToken = new Token(starToken.getOffset(), 0, DEFAULT, defaultStyle);
//		
//		scanner.setTarget(code);
//		scanner.setOffset(defauToken.getOffset());
//		
//		List<Token> newList = new ArrayList<Token>();
//		
//		//没有发生变化的
//		for(int i = 0; i < affectIndex; ++i){
//			newList.add(tokenList.get(i));
//		}
//		
//		Token newToken = null;
//		boolean matched = false;
//		
//		while(scanner.hasMoreChar()){
//			matched = false;
//			
//			for(IRule rule : rules){
//				newToken = rule.evaluate(scanner);
//				if(null != newToken){
//					if(defauToken.getLength() > 0){
//						if(isExists(newList, affectIndex, defauToken, shift)){
//							return;
//						}
//						paintToken(code, defauToken); //暂时在此画
//						newList.add(defauToken);
//					}
//					if(isExists(newList, affectIndex, defauToken, shift)){
//						return;
//					}
//					paintToken(code, newToken);
//					newList.add(newToken);
//					matched = true;
//					break;
//				}
//			}//for()
//			
//			if(matched){
//				defauToken = new Token(scanner.getOffset(), 0, DEFAULT, defaultStyle);
//			}
//			else{
//				defauToken.increaseLength();
//				scanner.read();
//			}
//		}//while()
//		
//		if(defauToken.getLength() > 0){
//			paintToken(code, defauToken);
//			newList.add(defauToken);
//		}
//		
//		tokenList = newList;
//	}
	
protected void setSyntaxColor(Editable code, Token starToken, int affectIndex, int shift) {
	
		Token defauToken = new Token(starToken.getOffset(), 0, DEFAULT, defaultStyle);
		
		scanner.setTarget(code);
		scanner.setOffset(defauToken.getOffset());
			
//		if(affectIndex<=0){
//			tokenList.clear();
//		}
//		else {
//			while(!tokenList.isEmpty()&&tokenList.size()>=affectIndex){
//				Token tmp = tokenList.remove(tokenList.size()-1);
//				code.removeSpan(tmp.getStyleSpan());
//				code.removeSpan(tmp.getForegroundColorSpan());
//			}
//		}
		
		while(!tokenList.isEmpty()&&tokenList.size()>affectIndex){
			Token tmp = tokenList.remove(tokenList.size()-1);       //移除受影响的Token
			code.removeSpan(tmp.getStyleSpan());         //移除code上失效的Span
			code.removeSpan(tmp.getForegroundColorSpan());
		}
	
		Token newToken = null;
		boolean matched = false;
		
		while(scanner.hasMoreChar()){
			matched = false;
			
			for(IRule rule : rules){
				newToken = rule.evaluate(scanner);
				if(null != newToken){
					if(defauToken.getLength() > 0){
						if(isExists(tokenList, affectIndex, defauToken, shift)){
							return;
						}
						paintToken(code, defauToken); 
						tokenList.add(defauToken);
					}
					if(isExists(tokenList, affectIndex, defauToken, shift)){
						return;
					}
					paintToken(code, newToken);
					tokenList.add(newToken);
					matched = true;
					break;
				}
			}//for()
			
			if(matched){
				defauToken = new Token(scanner.getOffset(), 0, DEFAULT, defaultStyle);
			}
			else{
				defauToken.increaseLength();
				scanner.read();
			}
		}//while()
		
		if(defauToken.getLength() > 0){
			paintToken(code, defauToken);
			tokenList.add(defauToken);
		}
	}
	
private void paintToken(Editable code, Token token){

	    final int NORMAL = Typeface.NORMAL;
	    final int BOLD = Typeface.BOLD;
	    
	    int start = token.getOffset();
	    int end = token.getOffset() + token.getLength();
	    int bold = ((token.getStyle().isBold() == true) ? BOLD : NORMAL);
	    
	    token.setStyleSpan(new StyleSpan(bold));
	    token.setForegroundColorSpan(new ForegroundColorSpan(token.getStyle().getForeground()));
	   
	    code.setSpan(token.getStyleSpan(), start, end, Spanned.SPAN_INTERMEDIATE);
	   
	    code.setSpan(token.getForegroundColorSpan(), start, end, Spanned.SPAN_INTERMEDIATE);
	}
	
	public void addRule(IRule... paramIRules){
		
		for(IRule rule : paramIRules){
			this.rules.add(rule);
		}
	}
	
//	public void setDefaultStyle(SyntaxColorStyle defaultStyle){
//		if(null == defaultStyle){
//			this.defaultStyle = StyleFactory.fromDefault();
//		}
//		else {
//			this.defaultStyle = defaultStyle;
//		}
//	}
	
	public boolean isExists(List<Token> newList, int fromIndex, Token token, int shift){
		
		for(int index = fromIndex; index < tokenList.size(); ++index){
			
			if(token.equalsWithShift(tokenList.get(index), shift)){
				for(int i = index; i < tokenList.size(); ++i){
					if(tokenList.get(i).shift(shift)){
						newList.add(tokenList.get(i));
					}
				}
				tokenList = newList;
				return true;
			}
		}
		return false;
	}
	
	public void clearTokenList(){
		tokenList.clear();
	}
}
