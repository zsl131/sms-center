package com.zslin.web.model;

import javax.persistence.*;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 16:58.
 * 短信模板
 */
@Entity
@Table(name = "t_module")
public class Module {

    /** 接入方名称 */
    private String name;

    /** 接入方token */
    private String token;

    /** 接入方id */
    private Integer cid;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** 聚合接口中模板id */
    private Integer iid;

    /** 签名 */
    private String sign;

    /** 内容，变量使用：#name# */
    private String content;

    /** 状态 */
    private String status;

    /** 删除标记 */
    private String flag = "0";

    /** 用于过滤模板重复提交 */
    private String pwd;

    /** 如果不通过，则原因 */
    private String reason="";

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
