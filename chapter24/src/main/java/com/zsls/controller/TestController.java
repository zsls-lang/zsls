package com.zsls.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("index")
	public String getTest() {
		return "测试";
	}
}
