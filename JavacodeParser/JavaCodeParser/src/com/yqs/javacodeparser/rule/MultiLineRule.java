package com.yqs.javacodeparser.rule;


import com.yqs.javacodeparser.scanner.Scanner;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.token.Token;

public class MultiLineRule extends PatternRule{

	private String excepStart = null;
	
 	public MultiLineRule(String name, String startStr, String endStr,
			SyntaxColorStyle style) {
		
		super(name, startStr, new String[]{endStr}, '\0', style);     // ��/*  */
	}
 
 	public MultiLineRule(String name, String startStr, String endStr,
			SyntaxColorStyle style, String exceptStart) {
		// ��Javadoc   /**   */
 		this(name, startStr, endStr, style);
 		this.excepStart = exceptStart;
	}
 	
 	public Token evaluate(Scanner scanner){
 		
 		Token t = super.evaluate(scanner);
 		if(null == t || this.excepStart == null){
 			return t;
 		}
 		
 		//�����Ƕ�  /** */���ֵ����⴦��
 		int offset = scanner.getOffset();
 		scanner.setOffset(t.getOffset());
 		int startIndex = 0;
 		int ch = scanner.read();
 		while(startIndex < excepStart.length() && ch == excepStart.charAt(startIndex)){
 			ch = scanner.read();
 			startIndex++;
 		}
 		
 		if(startIndex == excepStart.length()){
 			scanner.setOffset(t.getOffset());
 			return null;
 		}
 		
 		scanner.setOffset(offset);
 		
 		return t;
 	}
}
