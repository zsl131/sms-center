package com.zslin.web.model;

import com.zslin.web.vo.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 14:50.
 * 发送记录
 */
@Entity
@Table(name = "t_send")
public class Send extends BaseEntity {

    /** 接入方名称 */
    private String name;

    /** 接入方token */
    private String token;

    /** 接入方id */
    private Integer cid;

    /** 接收方电话 */
    private String phone;

    /** 短信内容 */
    private String content;

    /** 状态，0-成功 */
    private String status;

    /** 计数，扣短信数量 */
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
