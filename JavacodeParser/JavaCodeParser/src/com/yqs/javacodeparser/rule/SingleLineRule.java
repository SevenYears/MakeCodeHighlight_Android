package com.yqs.javacodeparser.rule;

import com.yqs.javacodeparser.style.SyntaxColorStyle;

public class SingleLineRule extends PatternRule {

	public SingleLineRule(String name, String startStr, String endStr,
			char escapChar, SyntaxColorStyle style) {
		
		super(name, startStr, new String[]{endStr, "\n"}, escapChar, style);	
	}
	
	public SingleLineRule(String name, String startStr, SyntaxColorStyle style) {
		
		super(name, startStr, new String[]{"\n"}, '\0', style);	
	}

}
