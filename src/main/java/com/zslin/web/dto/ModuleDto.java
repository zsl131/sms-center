package com.zslin.web.dto;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 15:50.
 */
public class ModuleDto {

    private Integer id;

    private String sign;

    private String content;

    private String status;

    private String reason;

    public ModuleDto(Integer id, String sign, String content, String status, String reason) {
        this.id = id;
        this.sign = sign;
        this.content = content;
        this.status = status;
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
