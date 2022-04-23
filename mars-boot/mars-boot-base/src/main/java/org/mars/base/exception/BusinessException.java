package org.mars.base.exception;
/**
 * @Author: Mars
 * @Description: 业务异常
 * @Date: 2022年4月23日
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Throwable cause)
	{
		super(cause);
	}
	
	public BusinessException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
