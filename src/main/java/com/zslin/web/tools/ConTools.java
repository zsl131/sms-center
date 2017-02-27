package com.zslin.web.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/7 0:01.
 */
public class ConTools {

    /** 生成真实的短信内容 */
    public static String rebuildContent(String con, String params) {
        try {
            params = URLDecoder.decode(params, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        String[] pArray = params.split("&");
        for(String p : pArray) {
            String [] cArray = p.split("=");
            con = con.replaceAll(cArray[0], cArray[1]);
        }
        return con;
    }

    /**
     * 获取短信条数
     * @param con 短信内容
     * @return
     */
    public static Integer getCount(String con) {
        int len = con.length();
        int c = len/70;
        return c + (con.length()%70==0?0:1);
    }
}
