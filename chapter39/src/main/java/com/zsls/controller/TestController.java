package com.zsls.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test001",method = RequestMethod.GET)
    public String test001(String name){
        logger.info("testController:::what is name 哈哈,{}",name);
        return "uuuusdfsdfiusdiufisdfisif"+name;
    }

}
