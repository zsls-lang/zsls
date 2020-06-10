/**
 * Company
 * Copyright (C) 2004-2020 All Rights Reserved.
 */
package com.zsls.service.impl;

import com.zsls.model.InputModel;
import com.zsls.service.ICalculateService;
import com.zsls.strategy.CalculateStrategy;
import com.zsls.strategy.CalculatelHandlerOperationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zsls
 * @version $Id CalculateServiceImpl.java, v 0.1 2020-06-10 18:10  Exp $$
 */
@Service
public class CalculateServiceImpl implements ICalculateService {
    @Autowired CalculatelHandlerOperationContext handlerOperationContext;

    @Override public Double handleOrder(InputModel inputModel) {
        CalculateStrategy calculateStrategy = handlerOperationContext.strategySelect(inputModel.getType());
        return calculateStrategy.doOperation(inputModel);
    }
}
