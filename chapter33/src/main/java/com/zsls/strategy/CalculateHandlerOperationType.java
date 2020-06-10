package com.zsls.strategy;

import com.zsls.enums.InputEnum;

import java.lang.annotation.*;

@Target(ElementType.TYPE)  //作用在类上
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited  //子类可以继承此注解
public @interface CalculateHandlerOperationType {
    /**
     * 策略类型
     * @return
     */
    InputEnum value();
}