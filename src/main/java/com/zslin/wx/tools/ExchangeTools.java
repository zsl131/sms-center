package com.zslin.wx.tools;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 钟述林 393156105@qq.com on 2017/1/24 23:38.
 * 与微信的数据交换
 */
@Component
public class ExchangeTools {

    @Autowired
    private AccessTokenTools accessTokenTools;

    public JSONObject getUserInfo(String openid) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params.put("access_token", accessTokenTools.getAccessToken());
            params.put("openid", openid);
            params.put("lang", "zh_CN");

            String result = InternetTools.doGet("https://api.weixin.qq.com/cgi-bin/user/info", params);

            JSONObject jsonObj = new JSONObject(result);
            return jsonObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
