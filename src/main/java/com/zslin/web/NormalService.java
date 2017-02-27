package com.zslin.web;

import com.zslin.basic.tools.NormalTools;
import com.zslin.web.model.Module;
import com.zslin.web.model.Send;
import com.zslin.web.model.Url;
import com.zslin.web.service.IModuleService;
import com.zslin.web.service.ISendService;
import com.zslin.web.service.IUrlService;
import com.zslin.web.tools.ConTools;
import com.zslin.web.tools.PhoneTools;
import com.zslin.web.tools.SendSmsTools;
import com.zslin.web.tools.SmsException;
import com.zslin.wx.tools.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 16:09.
 */
@Component("normalService")
public class NormalService {

    @Autowired
    private IUrlService urlService;

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private ISendService sendService;

    /**
     * A01-查询短信剩余条数
     * @param param
     * @param token
     * @return
     */
    public String surplus(String param, String token) {
        return urlService.surplus(token)+"";
    }

    /**
     * A02-发送短信
     * @param param
     * @param token
     * @return
     */
    public String sendSms(String param, String token) {
        if(param==null || "".equals(param)) {
            throw new SmsException("参数为空", "-1");
//            return "-1";
        }
        Integer mid ;
        try {
            mid = Integer.parseInt(JsonTools.getJsonParam(param, "mid")); //模板id
        } catch (NumberFormatException e) {
            throw new SmsException("非法模板ID", "-2");
//            return "-2";
        }
        Integer iid = moduleService.findId(mid, token);
        if(iid==null || iid<=0) {
            throw new SmsException("无效短信模板", "-2");
//            return "-2";
        }
        String con = JsonTools.getJsonParam(param, "con"); //替换值
//        if(con==null || "".equals(con)) {
//            throw new SmsException("无效替换值", "-3");
////            return "-3";
//        }
        String phone = JsonTools.getJsonParam(param, "phone"); //手机号码
        if(phone==null || "".equals(phone) || !PhoneTools.isMobile(phone)) {
            throw new SmsException("无效手机号码", "-4");
//            return "-4";
        }
        Url u = urlService.findByToken(token);
        if(u==null || u.getAmount()<=0) {
            throw new SmsException("账户余额不足", "-5");
//            return "-5";
        }
        String res = SendSmsTools.send(phone, iid+"", con);
        addSend(u, res, phone, mid, con);
        return res;
    }

    private void addSend(Url u, String res, String phone, Integer mid, String param) {
        Module m = moduleService.findOne(mid);
        Send s = new Send();
        s.setCreateDate(new Date());
        s.setCreateDay(NormalTools.curDate("yyyy-MM-dd"));
        s.setCreateLong(System.currentTimeMillis());
        s.setCreateTime(NormalTools.curDate("yyyy-MM-dd HH:mm:ss"));
        s.setToken(u.getToken());

        String content = ConTools.rebuildContent(m.getContent(), param);

        Integer amount = ConTools.getCount(m.getSign()+"【】"+content);
        s.setAmount(amount); //所扣条数

        String word = SendSmsTools.shield(content);
        if(word!=null && !"".equals(word)) { //有非法关键字
            throw new SmsException(word, "-6");
        }

        s.setContent(content);
        s.setCid(u.getId());
        s.setPhone(phone);
        s.setName(u.getName());
        s.setStatus(res);
        if("0".equals(res)) {
            urlService.minusAmount(u.getToken(), amount); //修改相应剩余条数
        }
        sendService.save(s);
    }
}
