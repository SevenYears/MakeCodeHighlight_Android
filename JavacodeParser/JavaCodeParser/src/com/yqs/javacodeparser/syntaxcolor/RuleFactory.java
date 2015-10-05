package com.yqs.javacodeparser.syntaxcolor;

import com.yqs.javacodeparser.rule.IRule;
import com.yqs.javacodeparser.rule.MultiLineRule;
import com.yqs.javacodeparser.rule.SingleLineRule;
import com.yqs.javacodeparser.rule.WordRule;
import com.yqs.javacodeparser.style.SyntaxColorStyle;

public final class RuleFactory {

	//¹Ø¼ü×Ö
	public static IRule createKeywordRule(String name, boolean isCaseSenstive,
			SyntaxColorStyle style, String... words) {
		return new WordRule(name, isCaseSenstive, style, words);
	}
	
	public static IRule createKeywordRule(String name, String endStr, boolean isCaseSenstive,
			SyntaxColorStyle style, String... words) {
		return new WordRule(name, endStr, isCaseSenstive, style, words);
	}
	
	//¶àÐÐ×¢ÊÍ   ¡¢ Javadoc
	public static IRule createMultiLineIRule(String name, String startStr, String endStr, SyntaxColorStyle style){
		
		return new MultiLineRule(name, startStr, endStr, style);
	}
	
	public static IRule createMultiLineIRule(String name, String startStr, String endStr, SyntaxColorStyle style, 
			String exceptStart){
		return new MultiLineRule(name, startStr, endStr, style, exceptStart);
	}
	
	//µ¥ÐÐ×¢ÊÍ¡¢ ×Ö·û´® ¡¢×Ö·û
	public static IRule createSingleLineRule(String name, String startStr, SyntaxColorStyle style){
		return new SingleLineRule(name, startStr, style);
	}
	
	public static IRule createSingleLineRule(String name, String startStr, String endStr, char escapChar, 
			SyntaxColorStyle style){
		return new SingleLineRule(name, startStr, endStr, escapChar, style);
	}
}
