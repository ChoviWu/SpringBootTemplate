package org.choviwu.web.index;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import org.choviwu.example.common.enums.MqEnums;
import org.choviwu.example.common.model.BasUser;
import org.choviwu.example.common.service.ResourceService;
import org.choviwu.example.common.util.JsonUtils;
import org.choviwu.example.mq.send.MqSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "首页",description = "首页模块")
@ApiModel(value = "首页模块",description = "这是一个首页")
public class IndexController {

    private final ResourceService resourceService;

    private final  MqSendMessage mqSendMessage;
    @Autowired
    IndexController(ResourceService resourceService, MqSendMessage mqSendMessage){
        this.resourceService = resourceService;
        this.mqSendMessage = mqSendMessage;
    }

    @GetMapping(value = "/index/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(
        @ApiImplicitParam(name = "ID", value = "", dataType = "整型")
    )
    public Object index(@PathVariable(required = false,value = "id") Integer id){
        List list = resourceService.selectAll();
        mqSendMessage.sendMessage(MqEnums.TOPIC,MqEnums.LOGIN.getValue()+id,new BasUser());
        return "Hello World"+id +"  "+ JsonUtils.toJson(list);
    }
}
