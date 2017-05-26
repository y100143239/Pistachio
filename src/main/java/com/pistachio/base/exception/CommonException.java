package com.pistachio.base.exception;

/**
 * business exception class(referenced InvokeException in  the former gateway-server jar, gradually replace and abolish InvokeException class  )
 * errMsg used to print log, and used to display returning message(Attention: this is different with BusinessException)
 * The field errorType in this class should be converted into errorCode in interceptor or basic class
 */
public class CommonException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private int errorType;//
	public int getErrorType() {
		return errorType;
	}
	
	/**
	 * @param errorType range from 0 to 99, the bottom layer will put the function no and the error type together as the errorCode
	 */
	public CommonException(int errorType){
		super();
		this.errorType = errorType;
	}
	/**
     * @param errorType range from 0 to 99, the bottom layer will put the function no and the error type together as the errorCode
	 * @param errMsg the message gived to the caller
	 */
	public CommonException(int errorType, String errMsg){
		super(errMsg);
		this.errorType = errorType;
	}
	/**
     * @param errorType range from 0 to 99, the bottom layer will put the function no and the error type together as the errorCode
	 * @param t exception that caused error
	 */
	public CommonException(int errorType, Throwable t){
		super(t);
		this.errorType = errorType;
	}
	/**
     * @param errorType range from 0 to 99, the bottom layer will put the function no and the error type together as the errorCode
     * @param errMsg the message gived to the caller
     * @param t exception that caused error
	 */
	public CommonException(int errorType, String errMsg, Throwable t){
		super(errMsg, t);
		this.errorType = errorType;
	}
}