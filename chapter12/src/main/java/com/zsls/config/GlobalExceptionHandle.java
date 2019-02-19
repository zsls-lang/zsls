package com.zsls.config;

import com.zsls.exception.CustomException;
import com.zsls.exception.ErrorResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

/**
 *@ClassName GlobalExceptionHandle
 *@Description 全局异常处理
 *@Author zsls
 *@Date 2019/2/18 17:13
 *@Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {

	/**
	 * @Author zsls
	 * @Description 定义要捕获的异常 可以多个 @ExceptionHandler({})
	 * @Date 17:28 2019/2/18
	 * @Param [request, e, response]
	 * @return com.zsls.exception.ErrorResponseEntity
	 */
	@ExceptionHandler(CustomException.class)
	public ErrorResponseEntity customExceptionHandler(HttpServletRequest request, final Exception e,
			HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		CustomException exception = (CustomException) e;
		return new ErrorResponseEntity(exception.getCode(), exception.getMessage());
	}


	/**
	 * @Author zsls
	 * @Description //TODO
	 * * TODO  如果你觉得在一个 exceptionHandler 通过  if (e instanceof xxxException) 太麻烦
	 * * TODO  那么你还可以自己写多个不同的 exceptionHandler 处理不同异常
	 * @Date 18:11 2019/2/18
	 * @Param [request, e, response]
	 * @return com.zsls.exception.ErrorResponseEntity
	 */
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponseEntity runtimeExceptionHandler(HttpServletRequest request, final Exception e,
			HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		RuntimeException exception = (RuntimeException) e;
		return new ErrorResponseEntity(400, exception.getMessage());
	}


	/**
	 * @Author zsls
	 * @Description //通用的接口映射异常处理方
	 * @Date 18:31 2019/2/18
	 * @Param [ex, body, headers, status, request]
	 * @return org.springframework.http.ResponseEntity<java.lang.Object>
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
			return new ResponseEntity<>(new ErrorResponseEntity(status.value(),
					exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), status);
		}
		if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
			logger.error("参数转换失败，方法：" + exception.getParameter().getMethod().getName() + "，参数：" + exception.getName()
					+ ",信息：" + exception.getLocalizedMessage());
			return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
		}
		return new ResponseEntity<>(new ErrorResponseEntity(status.value(), "参数转换失败"), status);
	}
}
