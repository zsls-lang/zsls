package com.zsls.exception;

/**
 *@ClassName CustomException
 *@Description TODO
 *@Author zsls
 *@Date 2019/2/18 17:03
 *@Version 1.0
 */
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 3403305078626794929L;
	private Integer code;

	public CustomException(Integer code,String message) {
		super(message);
		this.setCode(code);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
