package com.zsls.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Deacription 消息生产者service类
 * @Author zsls-lang
 * @Date 2021-09-08 2021/9/8 10:52
 * @Version 1.0
 **/
@Component("producerService")
public class ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送简单消息
     */
    public void sendMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.convertAndSend("testRocket001", "rocketMq,来了" + i);
        }
    }

    /**
     * 发送同步消息
     */
    public void sendSyncMessage() {
        for (int i = 0; i < 10; i++) {
            final SendResult syncSend = rocketMQTemplate.syncSend("testRocket001", "同步信息,来了" + i);
            System.out.println(syncSend);
        }
    }

    /**
     * 发送异步消息
     */
    public void sendAsyncMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.asyncSend("testRocket001", "异步信息,来了" + i, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败");
                }
            });
        }
    }

    /**
     * 发送单向消息
     */
    public void sendOneWayMessage() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.sendOneWay("testRocket001", "单向信息,来了" + i);
        }
    }

    /**
     * 发送同步顺序消息
     */
    public void syncSendOrderlyMessage() {
        // hashkey 用来决定计算消息发送到哪个队列 一般是订单Id 产品Id等
        rocketMQTemplate.syncSendOrderly("testRocket001-orderly","123456789 创建", "123456789");
        rocketMQTemplate.syncSendOrderly("testRocket001-orderly","123456789 支付", "123456789");
        rocketMQTemplate.syncSendOrderly("testRocket001-orderly","123456789 完成", "123456789");

        rocketMQTemplate.syncSendOrderly("testRocket001-orderly","1234567891 创建", "1234567891");
        rocketMQTemplate.syncSendOrderly("testRocket001-orderly","1234567891 支付", "1234567891");
        rocketMQTemplate.syncSendOrderly("testRocket001-orderly","1234567891 完成", "1234567891");
    }
    /**
     * 发送延迟消息
     */
    public void syncSendDelayMessage() {
        rocketMQTemplate.syncSend("testRocket001-orderly", MessageBuilder.withPayload("延迟0秒消息").build(),3000,0);
        rocketMQTemplate.syncSend("testRocket001-orderly", MessageBuilder.withPayload("延迟1秒消息").build(),3000,1);
        rocketMQTemplate.syncSend("testRocket001-orderly", MessageBuilder.withPayload("延迟5秒消息").build(),3000,2);
        rocketMQTemplate.syncSend("testRocket001-orderly", MessageBuilder.withPayload("延迟10秒消息").build(),3000,3);
    }
    /**
     * 发送事务消息
     */
    public void sendMessageInTransaction() {
        rocketMQTemplate.sendMessageInTransaction("testRocket001-transaction",MessageBuilder.withPayload("事务消息").build(),null);
    }

    @RocketMQTransactionListener
    class TransactionListerImpl implements RocketMQLocalTransactionListener {
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
            System.out.println("executeLocalTransaction");
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
            System.out.println("checkLocalTransaction");
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
    /**
     * 发送带tag消息 测试根据tag过滤消息
     */
    public void syncSendTagMessage() {
        rocketMQTemplate.convertAndSend("testRocket001-tag"+":"+"TAG1",MessageBuilder.withPayload("过滤消息-TAG1").build());
        rocketMQTemplate.convertAndSend("testRocket001-tag"+":"+"TAG2",MessageBuilder.withPayload("过滤消息-TAG2").build());
        rocketMQTemplate.convertAndSend("testRocket001-tag"+":"+"TAG3",MessageBuilder.withPayload("过滤消息-TAG3").build());
    }

    /**
     *  rocket  broker.conf配置文件需要增加 允许sql配置 enableProperty=true
     * 发送带SQL表达式头的消息 测试根据SQL表达式过滤消息
     */
    public void syncSendSQLMessage() {
        final Message<String> msg1 = MessageBuilder.withPayload("过滤消息-1").build();
        Map<String,Object>  header1 = new HashMap<>();
        header1.put("type","pay");
        header1.put("a",10);
        final Message<String> msg2 = MessageBuilder.withPayload("过滤消息-1").build();
        Map<String,Object>  header2 = new HashMap<>();
        header1.put("type","store");
        header1.put("a",5);
        final Message<String> msg3 = MessageBuilder.withPayload("过滤消息-1").build();
        Map<String,Object>  header3 = new HashMap<>();
        header1.put("type","user");
        header1.put("a",102);
        rocketMQTemplate.convertAndSend("testRocket001",msg1,header1);
        rocketMQTemplate.convertAndSend("testRocket001",msg2,header2);
        rocketMQTemplate.convertAndSend("testRocket001",msg3,header3);
    }

}
