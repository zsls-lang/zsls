package com.zsls.controller;

import com.sun.javafx.application.PlatformImpl;
import com.zsls.annotation.DateTime;
import com.zsls.groups.Groups;
import com.zsls.pojo.Book;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *@ClassName ValidateController
 *@Description 参数校验
 *@Author zsls
 *@Date 2019/2/19 10:36
 *@Version 1.0
 */
@RestController
@Validated
public class ValidateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController.class);

	@GetMapping("/test1")
	public String test1(String name) {
		if (name == null) {
			throw new NullPointerException("name 不能为空");
		}
		if (name.length() < 2 || name.length() > 10) {
			throw new RuntimeException("name 长度必须在 2 - 10 之间");
		}
		return "success";
	}

	@GetMapping("/test2")
	public String test2(@NotBlank(message = "name 不能为空") @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间") String name) {
		return "success";
	}

	@GetMapping("/test3")
	public String test3(@Validated Book book) {
		return "success";
	}

	@GetMapping("/test4")
	public String test4(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") String date) {
		return "success";
	}

	@GetMapping("/insert")
	public String insert(@Validated(value = Groups.Default.class) Book book) {
//		// 如果有捕获到参数不合法
//		if (bindingResult.hasErrors()) {
//			// 得到全部不合法的字段
//			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//			// 遍历不合法字段
//			fieldErrors.forEach(fieldError -> {
//				// 获取不合法的字段名和不合法原因
//				LOGGER.info("error field is : {} ,message is : {}", fieldError.getField(), fieldError.getDefaultMessage());
//			});
//		}
		return "insert";
	}

	@GetMapping("/update")
	public String update(@Validated(value = {Groups.Default.class,Groups.Update.class}) Book book) {
		return "update";
	}

}
