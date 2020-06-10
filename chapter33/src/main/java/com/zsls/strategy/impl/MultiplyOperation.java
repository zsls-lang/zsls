package com.zsls.strategy.impl;

import com.zsls.model.InputModel;
import org.springframework.stereotype.Component;

import com.zsls.strategy.CalculateHandlerOperationType;
import com.zsls.enums.InputEnum;
import com.zsls.strategy.CalculateStrategy;

@Component
@CalculateHandlerOperationType(InputEnum.MULTIPLY)
public class MultiplyOperation implements CalculateStrategy {
    @Override
    public double doOperation(InputModel inputModel) {
        return inputModel.getFirst() * inputModel.getNext();
    }

}