package org.choviwu.example.config;

import org.choviwu.example.common.enums.MqEnums;
import org.choviwu.example.mq.callback.CallBackHandler;
import org.choviwu.example.mq.callback.ConfirmCallBack;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:RabbitMQ配置
 */
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;
    @Value("${spring.rabbitmq.port}")
    private String port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;
    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean publisherReturns;
    @Value("${spring.rabbitmq.template.mandatory}")
    private Boolean madatory;


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public ConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setAddresses(addresses+":"+port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setPublisherReturns(publisherReturns);
        /** 如果要进行消息回调，则这里必须要设置为true */
        cachingConnectionFactory.setPublisherConfirms(publisherConfirms);
        return cachingConnectionFactory;
    }

    @Bean
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConfirmCallBack confirmCallBack,
                                         Jackson2JsonMessageConverter jsonMessageConverter,
                                         ConnectionFactory cachingConnectionFactory) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory());
        template.setMessageConverter(jsonMessageConverter);
        template.setConfirmCallback(confirmCallBack);//确认消息
        template.setReturnCallback(new CallBackHandler());//异常消息
        template.setMandatory(madatory);
        template.setExchange(MqEnums.TOPIC.getValue());
        template.setConnectionFactory(cachingConnectionFactory);
        //设置重试机制
        return template;
    }


}
