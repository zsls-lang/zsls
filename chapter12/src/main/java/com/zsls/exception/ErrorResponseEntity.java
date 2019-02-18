package com.zsls.exception;

/**
 *@ClassName ErrorResponseEntity
 *@Description TODO
 *@Author zsls
 *@Date 2019/2/18 17:01
 *@Version 1.0
 */
public class ErrorResponseEntity {
	private Integer code;
	private String message;

	public ErrorResponseEntity() {
	}

	public ErrorResponseEntity(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
