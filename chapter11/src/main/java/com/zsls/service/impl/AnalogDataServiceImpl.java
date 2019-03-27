package com.zsls.service.impl;

import com.github.zsls.base.BaseServiceImpl;
import com.zsls.dao.AnalogDataMapper;
import com.zsls.entity.AnalogData;
import com.zsls.service.IAnalogDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalogDataServiceImpl extends BaseServiceImpl<AnalogData> implements IAnalogDataService {
    @Autowired
    private AnalogDataMapper analogDataMapper;
}