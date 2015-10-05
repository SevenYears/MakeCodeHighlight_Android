package com.yqs.javacodeparser.scanner;

import android.text.Editable;

/**
 * 扫描EditText中的code
 * @author YangQingshuai
 *
 */
public class Scanner {
	
	int offset = 0;
	
	Editable target = null;
	
	public Scanner(){
		
	}
	
	public void setTarget(Editable target){
		this.target = target;
	}
	
	/**
	 * 扫描一个字符，向后移动一位
	 * @return
	 */
	public int read(){
		int ch = -1;
		try {
			if(offset < target.length()){
				ch = target.charAt(offset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		offset++;
		return ch;
	}
	
	/**
	 * 回溯一位
	 * @return
	 */
	public boolean unread(){
		if(offset > 0){
			offset--;
			return true;
		}
		return false;
	}
	
	public int getOffset(){
		return offset;
	}
	
	public void setOffset(int offset){
		this.offset = offset;
	}
	
	/**
	 * 判断是否还有字符可以读取
	 * @return
	 */
	public boolean hasMoreChar() {
		return this.offset < this.target.length();
	}
}
