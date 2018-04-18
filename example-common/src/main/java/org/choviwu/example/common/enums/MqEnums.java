package org.choviwu.example.common.enums;

/**
 * Created by ChoviWu on 2018/04/11
 * Description:
 */
public enum  MqEnums {

    LOGIN("login."),

    //Exchange
    TOPIC("topicExchange")
    ;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    MqEnums(String value) {
        this.value = value;
    }
}
