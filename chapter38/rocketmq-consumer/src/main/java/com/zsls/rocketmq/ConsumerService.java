package com.zsls.rocketmq;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Deacription 消费者
 * @Author zsls-lang
 * @Date 2021-09-08 2021/9/8 11:14
 * @Version 1.0
 *
 * MessageModel.BROADCASTING 广播模式
 * MessageModel.CLUSTERING 负载模式 默认模式
 *
 * ConsumeMode.ORDERLY 顺序接收
 **/
@Component
//@RocketMQMessageListener(topic = "testRocket001-orderly", consumerGroup = "${rocketmq.consumer.group}",consumeMode = ConsumeMode.ORDERLY)
//@RocketMQMessageListener(topic = "testRocket001-transaction", consumerGroup = "${rocketmq.consumer.group}")
//@RocketMQMessageListener(topic = "testRocket001-tag", consumerGroup = "${rocketmq.consumer.group}",selectorExpression = "TAG1 || TAG2",selectorType = SelectorType.TAG)
//@RocketMQMessageListener(topic = "testRocket001", consumerGroup = "${rocketmq.consumer.group}")
@RocketMQMessageListener(topic = "testRocket001", consumerGroup = "${rocketmq.consumer.group}",selectorExpression = "type='user' or a<7",selectorType = SelectorType.SQL92)
public class ConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("收到消息了：" + s);
    }
}
