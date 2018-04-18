package org.choviwu.example.mq.callback;

import org.choviwu.example.common.common.Constants;
import org.choviwu.example.common.model.BusMessageLog;
import org.choviwu.example.common.util.AssertUtil;
import org.choviwu.example.mapper.BusMessageLogMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by ChoviWu on 2018/04/11
 * Description: 确认发送消息
 */
@Component("confirmCallBack")
@Transactional
public  class ConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    private final BusMessageLogMapper messageLogMapper;

    @Autowired
    public ConfirmCallBack(BusMessageLogMapper messageLogMapper){
        this.messageLogMapper = messageLogMapper;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean flag, String s) {
        BusMessageLog log = messageLogMapper.getLogByCrc32Code(correlationData.getId());
        AssertUtil.isNullOrEmpty(log,"select_error");
        if(flag){
            //TODO
            if(log.getStatus().intValue()== Constants.TWO){
                //消费异常的 routKey未匹配上 或者down了
                //重试
                
            }else if (log.getStatus().intValue()== Constants.ZERO){
                //未消费的 让它消费
                log.setStatus(1);
                log.setAuditime(new Date());
                AssertUtil.isTrue(messageLogMapper.updateByPrimaryKeySelective(log)>0,"update_error");
            }
        }else{
            //服务器异常  消息
            /**
             * 1、 exchange 没有到  false
             * 2、
             */
            //TODO  Deal Logic
        }
    }


}
