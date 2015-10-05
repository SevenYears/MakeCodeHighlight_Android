package com.yqs.javacodeparser.rule;

import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import com.yqs.javacodeparser.scanner.Scanner;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.token.Token;

public class WordRule implements IRule {
	
	private String      name = null;
	
	private Set<String> words = new HashSet<String>();
	
	private String      endStr   = "";
	
	private boolean     isCaseSentive = true;
	
	private SyntaxColorStyle style;
	
	public WordRule(String name, String endStr, boolean isCaseSenstive,
			SyntaxColorStyle style, String... words){
		super();
		this.name    = name;
		this.endStr  = endStr;
		this.isCaseSentive = isCaseSenstive;
		this.style   = style;
		
		addWord(words);
	}
	
	public WordRule(String name, boolean isCaseSenstive, SyntaxColorStyle style,
			String... words){
		this(name, " \n\r\t()[]{};:'\",<.>/?\\|`~!@#%^&*+-=\0", isCaseSenstive, style, words);
	}
	
	public void addWord(String... ws){
		for(String word : ws){
			if(!isCaseSentive){
				words.add(word.toUpperCase());
			}
			else{
				words.add(word);
			}
		}
	}

	/**
	 * 实现接口函数，判断是不是关键字
	 */
	@Override
	public Token evaluate(Scanner scanner) {
		
		StringBuffer sb = new StringBuffer();
		int offset = scanner.getOffset();
		char prifixChar = 0;
		if(scanner.unread()){
			prifixChar = (char)scanner.read();
		}
		
		//当前字符的前一个字符若不是分隔符则他不可能是关键字
		if(prifixChar > 0 && endStr.indexOf(prifixChar) < 0){
			return null;
		}
		
		int ch = scanner.read();
		while(ch > 0 && (endStr.indexOf(ch) < 0)){
			sb.append((char)ch);
			ch = scanner.read();
		}
		
		String result = sb.toString();
		if(!"".equals(result)){
			if((!isCaseSentive && words.contains(result.toUpperCase())) || (isCaseSentive && words.contains(result))){
				scanner.unread();
				return new Token(offset, result.length(), name, style);
			}
		}
		
		scanner.setOffset(offset);
		
		return null;
	}

	@Override
	public SyntaxColorStyle getStyle() {
		
		return this.style;
	}

	@Override
	public String getName() {
		
		return name;
	}

}
