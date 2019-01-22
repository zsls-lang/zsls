package com.zsls.controller;

import com.zsls.entity.Author;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping
public class ThymeleafController {

	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView view = new ModelAndView();
		// 设置跳转的视图 默认映射到 src\main\resources\templates\{viewName}.html
		view.setViewName("index");
		// 设置属相
		view.addObject("title","thymeleaf页面");
		view.addObject("desc","welcome to thymeleaf");
		Author author = new Author();
		author.setAge(22);
		author.setEmail("guanren@qq.com");
		author.setName("柴进");
		view.addObject(author);

		return view;
	}

	@RequestMapping(value = "/index1",method = RequestMethod.GET)
	public String index1(HttpServletRequest request){

		// 设置属相
		request.setAttribute("title","thymeleaf页面");
		request.setAttribute("desc","welcome to thymeleaf");
		Author author = new Author();
		author.setAge(22);
		author.setEmail("guanren@qq.com");
		author.setName("柴进大官人");

		// 设置跳转的视图 默认映射到 src\main\resources\templates\{viewName}.html
		request.setAttribute("author",author);

		return "index";
	}


}
