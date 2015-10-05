package com.yqs.javacodeparser.style;


/**
 * 着色样式
 * @author YangQingshuai
 *
 */
public class SyntaxColorStyle implements Cloneable {
	
	private String fontFamily;

	private int fontSize;
	
	private int foreground;
	
	private int background;
	
	private boolean bold;
	
	private boolean italic;
	
	//private Style style;
	
	public SyntaxColorStyle clone()
	{
		SyntaxColorStyle obj = new SyntaxColorStyle();
		obj.background       = background;
		obj.bold             = bold;
		obj.fontFamily       = fontFamily;
		obj.fontSize         = fontSize;
		obj.foreground       = foreground;
		obj.italic           = italic;
		
		return obj;
	}
	
	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getForeground() {
		return foreground;
	}

	public void setForeground(int foreground) {
		this.foreground = foreground;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}
}
