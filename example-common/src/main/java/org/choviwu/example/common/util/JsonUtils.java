package org.choviwu.example.common.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static String toJson(Object value){
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
           json= mapper.writeValueAsString(value);
        }catch (Exception e){

        }
        return json;
    }

    //将json 对象转为Map
    public static Map json2Map(String jsondata){

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();

        try {
            map=mapper.readValue(jsondata, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    //将json对象转为指定对象类型
    public static <T> T json2Object(String jsondata,Class<T> tClass){

        ObjectMapper mapper = new ObjectMapper();
        //Map<String,Object> map = new HashMap<>();
        T o=null;
        try {
            o = mapper.readValue(jsondata, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
    /**
     * Json  获取List泛型数据
     * @param clazz
     * @param json
     * @return
     */
    public static List jsoncastListType(Class clazz,String json){
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = new ArrayList<>();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        try {
            list =  mapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * RabbitMQ unSerializable
     * @param message   MQ
     * @param <T>       T
     * @return
     */
    public static <T> T jackon2ToObject(Message message){
        return (T)new Jackson2JsonMessageConverter().fromMessage(message);
    }

}
