package com.zsls.controller;

import com.zsls.properties.MyProperties1;
import com.zsls.properties.MyProperties2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/propreties")
public class PropertiesController {

	private final Logger logger = LoggerFactory.getLogger(PropertiesController.class);

	@Value("${myName}")
	private String myName;

	@Autowired
	private MyProperties1 myProperties1;

	@Autowired
	private MyProperties2 myProperties2;

	@RequestMapping(value = "/1",method = RequestMethod.GET)
    public MyProperties1 getMyProperties1(){
		logger.info(myProperties1.toString());
    	return myProperties1;
	}


	@RequestMapping(value = "/2",method = RequestMethod.GET)
	public MyProperties2 getMyProperties2(){
		logger.info(myProperties2.toString());
		logger.info("myName:"+myName);
		return myProperties2;
	}


}
