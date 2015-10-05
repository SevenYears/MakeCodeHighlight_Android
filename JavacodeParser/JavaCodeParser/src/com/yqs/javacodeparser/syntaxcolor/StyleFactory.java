package com.yqs.javacodeparser.syntaxcolor;

import android.graphics.Color;

import com.yqs.javacodeparser.style.SyntaxColorStyle;


/**
 * 
 * @author YangQingshuai
 *
 */
public final class StyleFactory {
	
	public static SyntaxColorStyle clone(SyntaxColorStyle org){
		if(null!=org){
			return org.clone();
		}
		return null;
	}
	
	public static SyntaxColorStyle fromDefault(){
		
		SyntaxColorStyle style = new SyntaxColorStyle();
		style.setBackground(Color.rgb(255, 255, 255));
		style.setBold(false);
		style.setFontFamily("Tahoma");
		style.setFontSize(14);
		style.setForeground(Color.rgb(0, 0, 0));
		style.setItalic(false);
		
		return style;
	}
	
	public static SyntaxColorStyle fromDefault(String fontFamily, int fontSize){
		
		SyntaxColorStyle style = fromDefault();

		if(fontFamily != null && !"".equals(fontFamily)){
			style.setFontFamily(fontFamily);
		}
		
		style.setFontSize(fontSize);
		
		return style;
	}
}


