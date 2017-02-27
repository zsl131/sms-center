package com.zslin.web.controller;

import com.zslin.basic.tools.NormalTools;
import com.zslin.web.dto.ResDto;
import com.zslin.web.model.CallRecord;
import com.zslin.web.model.Code;
import com.zslin.web.model.UC;
import com.zslin.web.service.ICallRecordService;
import com.zslin.web.service.ICodeService;
import com.zslin.web.service.IUCService;
import com.zslin.web.service.IUrlService;
import com.zslin.web.tools.SmsException;
import com.zslin.web.tools.SmsSucException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 8:48.
 */
@RestController
@RequestMapping(value = "root")
public class RootController {

    @Autowired
    private BeanFactory factory;

    @Autowired
    private IUrlService urlService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IUCService ucService;

    @Autowired
    private ICallRecordService callRecordService;

    @GetMapping(value = "handler")
    public ResDto handler(HttpServletRequest request) {
        String token = request.getParameter("token"); //token
        String code = request.getParameter("code"); //接口代码
        String params = request.getParameter("p");
        params = params==null?"":params;
        if(token==null || code==null || "".equalsIgnoreCase(token) || "".equals(code)) {
            return new ResDto(ResDto.ERR, "1001", "无效参数");
        }
        String status = urlService.queryStatus(token);
        if(status==null || !"1".equalsIgnoreCase(status)) {
            return new ResDto(ResDto.ERR, "1002", "无效token，或停用");
        }
        Code c = codeService.findByC(code);
        if(c==null) {
            return new ResDto(ResDto.ERR, "1003", "无效接口，或删除");
        }

        UC uc = ucService.findByTokenAndCode(token, code);
        if(uc==null) {
            return new ResDto(ResDto.ERR, "1004", "无权限访问");
        }

        try {
            addRecord(token, code, params); //添加接口调用记录
            Object obj = factory.getBean(uc.getServiceName());
            Method method = obj.getClass().getMethod(uc.getMethodName(), params.getClass(), token.getClass());
            String result = (String) method.invoke(obj, params, token);
            return new ResDto(ResDto.SUC, result);
        }catch (SmsException e) {
            return new ResDto(ResDto.ERR, "-4", e.getMessage());
        } catch (NoSuchMethodException e) {
            return new ResDto(ResDto.ERR, "", "没有找到接口方法");
        } catch (InvocationTargetException e) {
            String msg = e.getTargetException().getMessage();
            SmsException smsExc = null;
            try {
                smsExc = (SmsException)e.getTargetException();
            } catch (Exception e1) {
            }
            if(smsExc!=null) {
                return new ResDto(ResDto.ERR, smsExc.getErr(), smsExc.getMessage());
            } else {
                //可以抛出成功的异常，用于回传多个数据
                SmsSucException sse = null;
                try {
                    sse = (SmsSucException) e.getTargetException();
                } catch (Exception e1) {
                }
                if(sse!=null) {
                    return new ResDto(ResDto.SUC, sse.getErr(), sse.getMessage());
                } else {
                    return new ResDto(ResDto.ERR, "", msg);
                }
            }

        } catch (IllegalAccessException e) {
            return new ResDto(ResDto.ERR, "", e.getMessage());
        } catch (Exception e) {
            return new ResDto(ResDto.ERR, "", e.getMessage());
        }
    }

    private void addRecord(String token, String code, String params) {
        try {
            params = URLDecoder.decode(params, "utf-8");
        } catch (Exception e) {
        }
        CallRecord cr = new CallRecord();
        cr.setToken(token);
        cr.setCode(code);
        cr.setParam(params);
        cr.setCreateDate(new Date());
        cr.setCreateDay(NormalTools.curDate("yyyy-MM-dd"));
        cr.setCreateLong(System.currentTimeMillis());
        cr.setCreateTime(NormalTools.curDate("yyyy-MM-dd HH:mm:ss"));
        callRecordService.save(cr);
    }
}
