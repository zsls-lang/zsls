package com.zsls;

import com.zsls.product.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class Chapter32Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Chapter32Application.class, args);
        KafkaSender sender = context.getBean(KafkaSender.class);
        for (int i = 0; i < 1000; i++) {
            sender.send();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
