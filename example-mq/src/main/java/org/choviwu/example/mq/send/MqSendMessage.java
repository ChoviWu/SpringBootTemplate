package org.choviwu.example.mq.send;

import org.choviwu.example.common.enums.MqEnums;
import org.choviwu.example.common.model.BusMessageLog;
import org.choviwu.example.common.util.AssertUtil;
import org.choviwu.example.common.util.IpUtils;
import org.choviwu.example.common.util.JsonUtils;
import org.choviwu.example.mapper.BusMessageLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Created by ChoviWu on 2018/04/11
 * Description:发消息 核心
 */
@Component
public class MqSendMessage {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate rabbitTemplate;

    private final BusMessageLogMapper messageLogMapper;

    @Autowired
    public MqSendMessage(RabbitTemplate rabbitTemplate,
                         BusMessageLogMapper messageLogMapper){
        this.rabbitTemplate = rabbitTemplate;
        this.messageLogMapper = messageLogMapper;
    }
    //发消息 TOPIC  消息体必须实现序列化
    public void sendMessage(MqEnums exchangeEnums, String routKey, Serializable object){
        String exchangeKey = exchangeEnums.getValue();
        try {
            //消息体防重复 唯一
            CorrelationData data = new CorrelationData(crc32Code(this.serialize(object)));
            if(messageLogMapper.getLogByCrc32Code(data.getId())!=null) return;
            this.insertLog(exchangeKey,data.getId(),routKey,object);
            rabbitTemplate.convertAndSend(exchangeKey, routKey,object,data);
        }catch (Exception e){ logger.info(JsonUtils.toJson(e));}
    }

    /**
     * 插入消息
     * @param exchangeKey   类型
     * @param key           key  exchange才有
     * @param message       消息体
     */
    private void insertLog(String exchangeKey,String  crc32Code,String key,Object message){

        BusMessageLog messageLog = new BusMessageLog();
        messageLog.setBody(JsonUtils.toJson(message));
        messageLog.setAddip(IpUtils.get());
        messageLog.setAddtime(new Date());
        messageLog.setRoutKey(key);
        messageLog.setStatus(0);//未消费
        messageLog.setCrc32code(crc32Code);
        if(StringUtils.isEmpty(exchangeKey)){
            messageLog.setType(2);
        }else{
            messageLog.setType(1);
        }
        AssertUtil.isTrue(messageLogMapper.insert(messageLog)>0,"insert_error");
    }


    /**
     * 序列化
     * @param object
     * @return
     */
    public byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.info("serialize error :" + e.getMessage());
        }
        return null;
    }
    //对消息体crc32处理防止消息重复
    public static String crc32Code(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return Long.valueOf(crc32.getValue()).toString();
    }

}
