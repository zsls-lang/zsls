package com.zsls.controller;

import com.zsls.annotation.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *@ClassName BookController
 *@Description TODO
 *@Author zsls
 *@Date 2019/2/26 16:22
 *@Version 1.0
 */
@RestController
@RequestMapping("/books")
public class BookController {

	@LocalLock(key = "book:arg[0]")
	@GetMapping("/query")
	public String query(@RequestParam String token) {
		return "success - " + token;
	}

}
