package com.zsls.controller;

import com.zsls.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName ExceptionController
 *@Description TODO
 *@Author zsls
 *@Date 2019/2/18 18:40
 *@Version 1.0
 */
@RestController
public class ExceptionController {
	@GetMapping("/test1")
	public String test1() {
		// TODO 这里只是模拟异常，假设业务处理的时候出现错误了，或者空指针了等等...
		int i = 10 / 0;
		return "test1";
	}

	@GetMapping("/test2")
	public Map<String, String> test2() {
		Map<String, String> result = new HashMap<>(16);
		try {
			int i = 10 / 0;
			result.put("code", "200");
			result.put("data", "具体返回的结果集");
		} catch (Exception e) {
			result.put("code", "500");
			result.put("message", "请求错误");
		}
		return result;
	}

	@GetMapping("/test3")
	public String test3( Integer num) {
		// TODO 演示需要，实际上参数是否为空通过 @RequestParam(required = true)  就可以控制
		if (num == null) {
			throw new CustomException(400, "num不能为空");
		}
		int i = 10 / num;
		return "result:" + i;
	}

}
