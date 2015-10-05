package com.yqs.javacodeparser.rule;

import com.yqs.javacodeparser.scanner.Scanner;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.token.Token;

public interface IRule {
	
	/**
	 * 评估当前的一段是否符合某个具体的rule
	 * @param scanner
	 * @return
	 */
	Token evaluate(Scanner scanner);
	
	SyntaxColorStyle getStyle();
	
	String getName();
}
