package com.zsls.strategy.impl;

import com.zsls.strategy.CalculateHandlerOperationType;
import com.zsls.enums.InputEnum;
import com.zsls.model.InputModel;
import com.zsls.strategy.CalculateStrategy;
import org.springframework.stereotype.Component;

@Component
@CalculateHandlerOperationType(InputEnum.ADD)
public class AddOperation implements CalculateStrategy {
    @Override
    public double doOperation(InputModel inputModel) {
        return inputModel.getFirst() + inputModel.getNext();
    }

}