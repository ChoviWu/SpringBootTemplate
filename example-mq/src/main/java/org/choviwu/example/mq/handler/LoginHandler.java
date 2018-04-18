package org.choviwu.example.mq.handler;

import com.rabbitmq.client.Channel;
import org.choviwu.example.common.model.BasUser;
import org.choviwu.example.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ChoviWu on 2018/04/11
 * Description:登陆消费
 */
@Component("loginConsumer")
public class LoginHandler implements ChannelAwareMessageListener{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        BasUser user = JsonUtils.jackon2ToObject(message);
        logger.info(JsonUtils.toJson(user));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
