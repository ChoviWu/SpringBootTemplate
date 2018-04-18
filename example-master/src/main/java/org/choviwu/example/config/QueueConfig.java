package org.choviwu.example.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ChoviWu on 2018/04/11
 * Description: 消息队列管理
 */
@Configuration
public class QueueConfig {

    @Value("${rabbitmq.topic.queue.login}")
    private String login;
    @Value("${rabbitmq.topic.login.routKey}")
    private String routKey;
    @Value("${spring.rabbitmq.exchange.topic}")
    private String topicExchange;

    /**
     * 声明队列  并且将队列标记到rabbitAdmin
     * 是否持久化
     */
    @Bean
    public Queue login(RabbitAdmin rabbitAdmin){
        Queue queue =  new Queue(login, true,false,false);
        rabbitAdmin.declareQueue(queue);
        System.out.println("ToPic login实例化完成");
        return queue;
    }

    /**
     * 主题型交换机 本系统仅应用此类型
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public TopicExchange topicExchange(RabbitAdmin rabbitAdmin){
        TopicExchange contractTopicExchange = new TopicExchange(topicExchange);
        rabbitAdmin.declareExchange(contractTopicExchange);
        System.out.println("完成主题型交换机bean实例化");
        return contractTopicExchange;
    }

    /**
     * 给指定的exchange、routKey绑定队列
     * @param rabbitAdmin
     * @param login          队列名
     * @param topicExchange  主题类型
     * @return
     */
    @Bean
    public Binding binding(RabbitAdmin rabbitAdmin,Queue login,TopicExchange topicExchange){
        Binding binding = BindingBuilder.bind(login).to(topicExchange).with(routKey);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
    /**
     * @param cachingConnectionFactory
     * @return
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory cachingConnectionFactory){
        return new RabbitAdmin(cachingConnectionFactory);
    }

//    @Bean
//    public MessageRecoverer messageRecoverer(RabbitTemplate template){
//        return new RepublishMessageRecoverer(template, "exchangemsxferror", "routingkeymsxferror");
//    }

}
