package org.choviwu.example.common.constants;

import org.springframework.amqp.core.Queue;

import java.util.Map;

/**
 * Created by ChoviWu on 2018/04/13
 * Description:
 */
public class MqQueue extends Queue{

    public static final String LOGIN = "login";

    public MqQueue(String name) {
        super(name, true,false,false);
    }


    public MqQueue(String name, boolean durable) {
        super(name, durable);
    }

    public MqQueue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
        super(name, durable, exclusive, autoDelete);
    }

    public MqQueue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        super(name, durable, exclusive, autoDelete, arguments);
    }

//    public static Queue[] list(){
//        List<Queue> list = new LinkedList<>();
//        list.add(new MqQueue(LOGIN));
//
//
//
//        Queue[] queues = new Queue[list.size()];
//        for (int i = 0;i<queues.length;i++){
//            queues[i] = list.get(i);
//        }
//        return queues;
//    }


}
