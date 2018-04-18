package org.choviwu.example.rest.controller;

import io.swagger.annotations.*;
import org.choviwu.example.common.common.Paginator;
import org.choviwu.example.common.enums.MqEnums;
import org.choviwu.example.common.model.BasUser;
import org.choviwu.example.mq.send.MqSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * Created by ChoviWu on 2018/04/10
 * Description:Rest Ful
 */
@Api(description = "主页模块",tags = {"主页"})
@RestController
public class IndexController {

    private final MqSendMessage sendMessage;

    private Logger logger = Logger.getLogger("IndexController");
    @Autowired
    public IndexController(MqSendMessage sendMessage){
        this.sendMessage = sendMessage;
    }

    @ApiOperation(value = "查询列表",nickname = "ChoviWu",notes = "查询列表",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams(
            @ApiImplicitParam( name = "id",value = "测试ID",defaultValue = "1",dataType = "Integer")
    )
    @GetMapping(value = "/example/{id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object example(Paginator p,
                          @ApiParam(name = "pageSize",value = "10",defaultValue = "10",required = true)
                          @PathVariable("id") Integer name){
        System.out.println(name);
        return name;
    }

    @ApiOperation(value = "首页",nickname = "ChoviWu",notes = "首页",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/index")
    public Object index(){
        logger.info("Logging...");
        BasUser user = new BasUser();
        user.setId(1);
        sendMessage.sendMessage(MqEnums.TOPIC, MqEnums.LOGIN.getValue()+"1234",user);
        return user;
    }

}
