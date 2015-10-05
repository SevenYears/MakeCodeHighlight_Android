package com.yqs.javacodeparser.token;

import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.yqs.javacodeparser.style.SyntaxColorStyle;


public class Token {
	
	private int    offset;
	
	private int    length;
	
	private String name;
	
	private SyntaxColorStyle style;
	
	private StyleSpan styleSpan = null;

	private ForegroundColorSpan foregroundColorSpan = null;
	
	public Token(int offset, int length, String name, SyntaxColorStyle style){
		super();
		this.offset   = offset;
		this.length   = length;
		this.name     = name;
		this.style    = style;
	}
	
	public int getOffset(){
		return offset;
	}
	
	public int getLength(){
		return length;
	}
	
	public String getName(){
		return name;
	}
	
	public void increaseLength(){
		this.length++;
	}
	
	public SyntaxColorStyle getStyle(){
		return style;
	}
	
	public StyleSpan getStyleSpan() {
		return styleSpan;
	}

	public void setStyleSpan(StyleSpan styleSpan) {
		this.styleSpan = styleSpan;
	}

	public ForegroundColorSpan getForegroundColorSpan() {
		return foregroundColorSpan;
	}

	public void setForegroundColorSpan(ForegroundColorSpan foregroundColorSpan) {
		this.foregroundColorSpan = foregroundColorSpan;
	}
	
	/**
	 * 平移Token
	 * @param shiftSize
	 * @return
	 */
	public boolean shift(int shiftSize){
		if(this.offset+shiftSize<0){
			return false;
		}
		this.offset += shiftSize;
		return true;
	}
	
	/**
	 * 比较两个Token是否相同
	 * @param token
	 * @param shift
	 * @return
	 */
	public boolean equalsWithShift(Token token, int shift){
		return this.length==token.length&&this.name.equals(token.name)
				&&this.offset==token.offset+shift;
	}
}
