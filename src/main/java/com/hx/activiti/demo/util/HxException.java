package com.hx.activiti.demo.util;

/**
 * 异常类
 * 
 * @author lb
 *
 */
public class HxException extends Exception {

	/** 错误编码 */
	private int code = 0;

	/**
	 * 获取错误编码
	 * 
	 * @return 错误编码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 构造函数
	 * 
	 */
	public HxException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常消息
	 */
	public HxException(String message) {
		super(message);
		this.code = 9999;
	}

	/**
	 * 构造函数
	 * 
	 * @param cause 异常原因
	 */
	public HxException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常消息
	 * @param cause 异常原因
	 */
	public HxException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param code 错误编码
	 * @param message 异常消息
	 */
	public HxException(int code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 构造函数
	 * 
	 * @param code 错误编码
	 * @param message 异常消息
	 * @param cause 异常原因
	 */
	public HxException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
}
