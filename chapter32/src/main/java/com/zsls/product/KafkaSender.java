package com.zsls.product;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zsls.model.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;

@Service
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.topic-name}")
    private String topic;
    private Gson gson = new GsonBuilder().create();

    public void send() {
        Messages message = Messages.builder().id(System.currentTimeMillis()).msg("222").sendTime(new Date()).build();
        ListenableFuture<SendResult<String, String>> test0 = kafkaTemplate.send(topic, gson.toJson(message));
    }

}