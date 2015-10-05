package com.yqs.javacodeparser.rule;

import com.yqs.javacodeparser.scanner.Scanner;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.token.Token;

public class PatternRule implements IRule {

	private String     name         = "string";
	
	private String     startStr     = "\"";
	
	private String     endStr[]     = new String[]{"\n", "\""};
	
	private char       escapChar    = '\\';
	
	private SyntaxColorStyle style;
	
	public PatternRule(String name, String startStr, String[] endStr,
			char escapChar, SyntaxColorStyle style){
		super();
		this.name        = name;
		this.startStr    = startStr;
		this.endStr      = endStr;
		this.escapChar   = escapChar;
		this.style       = style;
	}
	
	@Override
	public Token evaluate(Scanner scanner) {
		
		int offset = scanner.getOffset();
		int startIndex = 0;
		int ch = scanner.read();
		
		//检查 头字符串是否符合
		while(startIndex < startStr.length() && ch == startStr.charAt(startIndex)){
			ch = scanner.read();
			startIndex++;
		}
		
		if(startIndex < startStr.length()){
			scanner.setOffset(offset);
			return null;
		}
		
		while(ch > 0){
			if(ch == escapChar){
				ch = scanner.read();
				ch = scanner.read();				
				continue;
			}
			
			int subOffset = scanner.getOffset();
			int subCh = ch;
			
			for(String s : endStr){
				startIndex = 0;
				while(startIndex < s.length() && s.charAt(startIndex) == subCh){
					subCh = scanner.read();
					startIndex++;
				}
			
				
				if(startIndex == s.length()){
					scanner.unread();
					return new Token(offset, scanner.getOffset()-offset, name, style);
				}
				scanner.setOffset(subOffset);
				subCh = ch;
			}//for()
			ch = scanner.read();
		}
		
		return new Token(offset, scanner.getOffset() - offset-1, name, style);
	}

	@Override
	public SyntaxColorStyle getStyle() {
	
		return style;
	}

	@Override
	public String getName() {

		return name;
	}

}
