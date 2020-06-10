package com.zsls.strategy;

import com.zsls.enums.InputEnum;
import com.zsls.strategy.CalculateHandlerOperationType;
import com.zsls.strategy.CalculateStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略核心功能,获取所有策略注解的类型
 * 并将对应的class初始化到HandlerContext中
 */
@Component
public class CalculatelHandlerOperationContext implements ApplicationContextAware {

    private final Map<String, CalculateStrategy> strategyMap = new ConcurrentHashMap<>();

    private ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    @PostConstruct
    public void register() {
        this.strategyMap.clear();
        //获取所有策略注解的Bean
        Map<String, Object> strategyClassMap = context.getBeansWithAnnotation(CalculateHandlerOperationType.class);
        strategyClassMap.forEach((k,v)->{
            Class<CalculateStrategy> strategyClass = (Class<CalculateStrategy>) v.getClass();
            InputEnum inputEnum = strategyClass.getAnnotation(CalculateHandlerOperationType.class).value();
            //将class加入map中,type作为key
            strategyMap.put(inputEnum.getMessage(),context.getBean(strategyClass));
        });
        System.out.println(this.strategyMap);
    }

    public CalculateStrategy strategySelect(String mode) {
//        Preconditions.checkArgument(!StringUtils.isEmpty(mode), "不允许输入空字符串");
        return this.strategyMap.get(mode);
    }
}