package com.zsls.consume;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Optional;

@Service
public class KafkaReceiver {
    @Value("${kafka.topic.topic-name}")
    private String topic;

    @KafkaListener
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("record =" + record);
            System.out.println("message =" + message);

        }
    }

    /**
     *
     * PostConstruct 注释用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化。此方法必须在将类放入服务之前调用。
     * 支持依赖关系注入的所有类都必须支持此注释。即使类没有请求注入任何资源，用 PostConstruct 注释的方法也必须被调用。
     * 只有一个方法可以用此注释进行注释。应用 PostConstruct 注释的方法必须遵守以下所有标准：该方法不得有任何参数，
     * 除非是在 EJB 拦截器 (interceptor) 的情况下，根据 EJB 规范的定义，在这种情况下它将带有一个 InvocationContext 对象 ；
     * 该方法的返回类型必须为 void；该方法不得抛出已检查异常；
     * 应用 PostConstruct 的方法可以是 public、protected、package private 或 private；除了应用程序客户端之外
     * ，该方法不能是 static；该方法可以是 final；如果该方法抛出未检查异常，那么不得将类放入服务中，
     * 除非是能够处理异常并可从中恢复的 EJB
     *
     *
     * @PostConstruct的应用场景之一是在初始化Servlet时加载一些缓存数据等
     * 应用场景之二是与spring结合使用，通过@PostConstruct 和 @PreDestroy 方法 实现bean初始化之后和bean销毁之前进行的操作
     *
     *
     * 如果想在生成对象时候完成某些初始化操作，而偏偏这些初始化操作又依赖于依赖注入，那么就无法在构造函数中实现。
     * 为此，可以使用@PostConstruct注解一个方法来完成初始化，@PostConstruct注解的方法将会在依赖注入完成后被自动调用。
     *spring中Constructor、@Autowired、@PostConstruct的顺序
     * Constructor >> @Autowired >> @PostConstruct
     */
    
    /**
     *
     *initKafkaHandler()这个方法的作用是把配置中的多个topic读到注解的参数中  例如spring.kafkaListenerList = topic1,topic2
     * 其实下面的方法initKafkaHandler()可以采用这个方法代替@KafkaListener(topics = "#{'${spring.kafkaListenerList}'.split(',')}")
     *
     */
    @PostConstruct
    public void initKafkaHandler() throws NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        String topics = topic;
        String[] topicArray = topics.split(",");
        //反射，listen是方法名，ConsumerRecord.class是参数的类，找到这个监听方法修改topics的值
        Method listen = KafkaReceiver.class.getDeclaredMethod("listen", ConsumerRecord.class);
        KafkaListener kafkaListener = listen.getAnnotation(KafkaListener.class);
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(kafkaListener);
        Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
        //默认的访问权限是不行的，要修改成true才能修改属性的值
        hField.setAccessible(true);
        Map memberValues = (Map) hField.get(invocationHandler);
        memberValues.put("topics", topicArray);
    }


}