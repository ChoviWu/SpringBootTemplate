package org.choviwu.example.mq.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by ChoviWu on 2018/04/11
 * Description:失败MQ回调
 */
@Component("callBackHandler")
public final class CallBackHandler implements RabbitTemplate.ReturnCallback{

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//        System.out.println("This is Return Back Logic");
//        System.out.println(message.getMessageProperties().getDeliveryTag());
//        System.out.println(message.getMessageProperties().getType());
//        System.out.println(message.getMessageProperties().getUserId());
//        System.out.println(message.getMessageProperties().getAppId());
//        System.out.println(message.getMessageProperties().getClusterId());
//        System.out.println(message.getMessageProperties().getConsumerQueue());
//        System.out.println(message.getMessageProperties().getConsumerTag());
//        System.out.println(message.getMessageProperties().getContentEncoding());
//        System.out.println(message.getMessageProperties().getHeaders());
//        System.out.println(message.getMessageProperties().getMessageId());
//        System.out.println(message.getMessageProperties().getReceivedExchange());
//        System.out.println(message.getMessageProperties().getReceivedRoutingKey());
//        System.out.println(message.getMessageProperties().getReceivedRoutingKey());
//        System.out.println(message.getMessageProperties().getReceivedUserId());
//        System.out.println(message);
//        message.getMessageProperties().get
    }
}
