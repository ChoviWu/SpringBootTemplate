package org.choviwu.example.common.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "bus_message_log_t")
public class BusMessageLog {
    @Id
    private Integer id;

    @Column(name = "crc32Code")
    private String crc32code;

    @Column(name = "rout_key")
    private String routKey;

    /**
     * 0 未处理  1成功  2 失败
     */
    private Integer status;

    /**
     * 类型  1  exchangeKey   2  Topic 
     */
    private Integer type;

    /**
     * 审核时间
     */
    private Date auditime;

    private Date addtime;

    private String addip;

    private String body;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return crc32Code
     */
    public String getCrc32code() {
        return crc32code;
    }

    /**
     * @param crc32code
     */
    public void setCrc32code(String crc32code) {
        this.crc32code = crc32code;
    }

    /**
     * @return rout_key
     */
    public String getRoutKey() {
        return routKey;
    }

    /**
     * @param routKey
     */
    public void setRoutKey(String routKey) {
        this.routKey = routKey;
    }

    /**
     * 获取0 未处理  1成功  2 失败
     *
     * @return status - 0 未处理  1成功  2 失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 未处理  1成功  2 失败
     *
     * @param status 0 未处理  1成功  2 失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取类型  1  exchangeKey   2  Topic 
     *
     * @return type - 类型  1  exchangeKey   2  Topic 
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型  1  exchangeKey   2  Topic 
     *
     * @param type 类型  1  exchangeKey   2  Topic 
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取审核时间
     *
     * @return auditime - 审核时间
     */
    public Date getAuditime() {
        return auditime;
    }

    /**
     * 设置审核时间
     *
     * @param auditime 审核时间
     */
    public void setAuditime(Date auditime) {
        this.auditime = auditime;
    }

    /**
     * @return addtime
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return addip
     */
    public String getAddip() {
        return addip;
    }

    /**
     * @param addip
     */
    public void setAddip(String addip) {
        this.addip = addip;
    }

    /**
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }
}