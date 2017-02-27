package com.zslin.web.model;

import com.zslin.web.vo.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 14:33.
 * 充值金额
 */
@Entity
@Table(name = "t_charge")
public class Charge extends BaseEntity {

    /** 接入方名称 */
    private String name;

    /** 接入方token */
    private String token;

    /** 接入方id */
    private Integer cid;

    /** 充值金额，单位分 */
    private Integer money;

    /** 充值短信条数 */
    private Integer amount;

    /** 充值后剩余条数 */
    private Integer result;

    /** 描述 */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
