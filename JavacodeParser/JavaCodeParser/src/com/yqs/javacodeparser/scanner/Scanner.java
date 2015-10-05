package com.yqs.javacodeparser.scanner;

import android.text.Editable;

/**
 * ɨ��EditText�е�code
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
	 * ɨ��һ���ַ�������ƶ�һλ
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
	 * ����һλ
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
	 * �ж��Ƿ����ַ����Զ�ȡ
	 * @return
	 */
	public boolean hasMoreChar() {
		return this.offset < this.target.length();
	}
}
