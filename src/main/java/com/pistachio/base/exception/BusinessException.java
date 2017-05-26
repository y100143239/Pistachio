package com.pistachio.base.exception;

/**
 * Business exception classࡣ
 * errorCode used to display returning message, errMsg only used to print log
 * advice: errorCode not related to funcNo
 */
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private int errorCode;//find corresponding display message according to errorCode from the interceptor
	private String[] params = null;//the parameter in hint message. ex: ${0}, ${1}
	public int getErrorCode() {
		return errorCode;
	}
	public String[] getParams() {
		return params;
	}
	
	public BusinessException(int errorCode){
		super();
		this.errorCode = errorCode;
	}
	public BusinessException(int errorCode, String errMsg){
		super(errMsg);
		this.errorCode = errorCode;
	}
	public BusinessException(int errorCode, Throwable t){
		super(t);
		this.errorCode = errorCode;
	}
	public BusinessException(int errorCode, String errMsg, Throwable t){
		super(errMsg, t);
		this.errorCode = errorCode;
	}
	public BusinessException(int errorCode, String[] params){
		super();
		this.errorCode = errorCode;
		this.params = params;
	}
	public BusinessException(int errorCode, String errMsg, String[] params){
		super(errMsg);
		this.errorCode = errorCode;
		this.params = params;
	}
	public BusinessException(int errorCode, Throwable t, String[] params){
		super(t);
		this.errorCode = errorCode;
		this.params = params;
	}
	public BusinessException(int errorCode, String errMsg, Throwable t, String[] params){
		super(errMsg, t);
		this.errorCode = errorCode;
		this.params = params;
	}
}
