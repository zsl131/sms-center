package com.zslin.web.model;

import com.zslin.web.vo.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 16:02.
 */
@Entity
@Table(name = "t_call_record")
public class CallRecord extends BaseEntity {

    private String token;

    private String code;

    @Lob
    private String param;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
