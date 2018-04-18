package org.choviwu.example.config.consumer;

import org.choviwu.example.mq.handler.LoginHandler;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ChoviWu on 2018/04/14
 * Description:
 */
@Configuration
public class LoginConsumerConfig {


    @Bean("jsonMessageConverter")
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean("loginMessageListenerContainer")
    public MessageListenerContainer loginMessageListenerContainer(LoginHandler loginConsumer, Queue login,
                                                                  ConnectionFactory cachingConnectionFactory,
                                                                  Jackson2JsonMessageConverter jsonMessageConverter){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setQueues(login);
        messageListenerContainer.setMessageListener(loginChannelAwareMessageListener(loginConsumer));
        messageListenerContainer.setConnectionFactory(cachingConnectionFactory);
        messageListenerContainer.setMessageConverter(jsonMessageConverter);
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);//手动确认
        messageListenerContainer.setExposeListenerChannel(true);
        messageListenerContainer.setMaxConcurrentConsumers(1);
        messageListenerContainer.setConcurrentConsumers(1);
        messageListenerContainer.setPrefetchCount(5);//每次处理5条消息
        return messageListenerContainer;
    }

    @Bean("loginChannelAwareMessageListener")
    public ChannelAwareMessageListener loginChannelAwareMessageListener(LoginHandler loginConsumer){
        return loginConsumer;
    }

}
