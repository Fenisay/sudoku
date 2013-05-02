package utils;

import java.io.Serializable;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8304388028718913800L;
	private int code; 
	private Object[] args;
	
	public Message(int code, Object[] args){
		this.code = code;
		this.args = args;
	}
	
	public Message(int code){
		this(code, null);
	}
	
	public int getCode(){
		return code;
	}
	
	public Object[] getArgs(){
		return args;
	}
}