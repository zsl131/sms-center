package com.zslin.test;

import com.zslin.basic.tools.RandomTools;
import com.zslin.web.tools.ConTools;
import com.zslin.web.tools.PhoneTools;
import com.zslin.web.tools.SendSmsTools;
import com.zslin.wx.tools.JsonTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:00.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class NormalTest {

    @Test
    public void testRandom() {
        String str = RandomTools.randomString(16);
        System.out.println(str);
    }

    @Test
    public void testEncode() throws Exception {
        String str = "#name#你好，验证码是#code#。";
//        System.out.println(URLDecoder.decode(str, "utf-8"));
        System.out.println(URLEncoder.encode(str, "utf-8"));
        String s = "%23name%23%3D%E5%BC%A0%E4%B8%89%26%23yw%23%3D88%26%23sx%23%3D95";
        System.out.println(URLDecoder.decode(s, "utf-8"));
        System.out.println(URLEncoder.encode("{'sign':'昭通网', 'content':'#name#你好，验证码是#code#。'}", "utf-8"));

        System.out.println(URLEncoder.encode("{'phone':'15925061256', 'mid':2,'con':'#name#=张三&#yw#=88&#sx#=95'}", "utf-8"));
        System.out.println(URLEncoder.encode("{'id':1}", "utf-8"));
    }

    @Test
    public void testWord() {
        String word = "你中奖的验证码是：1234，哈哈，中奖";
        String res = SendSmsTools.shield(word);
        System.out.println(res);
    }

    @Test
    public void test() {
        String str = "{\"reason\":\"操作成功\",\"result\":{\"sid\":\"201702062302208712749619\",\"fee\":1,\"count\":1},\"error_code\":0}";
        String res = JsonTools.getJsonParam(str, "error_code");
        System.out.println(res);
    }

    @Test
    public void testPhone() {
        String str = "18087021771";
        System.out.println(PhoneTools.isMobile(str));
    }

    @Test
    public void testCount() {
        String con = "【昭通网】你好，这是您的短信验证码：123456.请不要告诉任何人1，就算打死也不要告诉这是您的短信验证码：123456.请不要告诉任何人，就【昭通网】你好，这是您的短信验证码：123456.请不要告诉任何人，就算打死也不要告诉这是您的短信验证码：123456.请不要告诉任何人，就";
        System.out.println(con.length()+"======"+ ConTools.getCount(con));
    }
}
