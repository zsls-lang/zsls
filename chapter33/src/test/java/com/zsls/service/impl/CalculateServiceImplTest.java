package com.zsls.service.impl;

import com.zsls.enums.InputEnum;
import com.zsls.model.InputModel;
import com.zsls.service.ICalculateService;
import com.zsls.strategy.CalculateHandlerOperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateServiceImplTest {

    @Autowired ICalculateService calculateService;
    @Test void handleOrder() {

        InputModel inputModel = InputModel.build();
        inputModel.setFirst(1d);
        inputModel.setNext(2d);
        inputModel.setType(InputEnum.ADD.getMessage());
        System.out.println( calculateService.handleOrder(inputModel));;
    }
}