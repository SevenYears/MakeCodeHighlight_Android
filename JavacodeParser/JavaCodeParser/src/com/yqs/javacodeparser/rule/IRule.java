package com.yqs.javacodeparser.rule;

import com.yqs.javacodeparser.scanner.Scanner;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.token.Token;

public interface IRule {
	
	/**
	 * ������ǰ��һ���Ƿ����ĳ�������rule
	 * @param scanner
	 * @return
	 */
	Token evaluate(Scanner scanner);
	
	SyntaxColorStyle getStyle();
	
	String getName();
}
