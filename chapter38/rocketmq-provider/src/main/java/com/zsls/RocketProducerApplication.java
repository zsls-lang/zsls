package com.zsls;

import com.zsls.rocketmq.ProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Deacription
 * @Author zsls-lang
 * @Date 2021-09-08 2021/9/8 11:02
 * @Version 1.0
 **/
@SpringBootApplication
public class RocketProducerApplication {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(RocketProducerApplication.class, args);
        final ProducerService producerService = (ProducerService) context.getBean("producerService");
//        producerService.sendMessage();
//    producerService.sendSyncMessage();
//    producerService.sendAsyncMessage();
//    producerService.sendOneWayMessage();
//    producerService.syncSendOrderlyMessage();
//    producerService.syncSendDelayMessage();
//    producerService.sendMessageInTransaction();
//    producerService.syncSendTagMessage();
    producerService.syncSendSQLMessage();
    }
}
