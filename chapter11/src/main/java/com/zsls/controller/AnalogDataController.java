package com.zsls.controller;

import com.zsls.base.BaseController;
import com.zsls.service.impl.AnalogDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalogDataController extends BaseController {
    @Autowired
    private AnalogDataServiceImpl analogDataServiceImpl;
}