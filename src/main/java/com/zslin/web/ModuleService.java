package com.zslin.web;

import com.alibaba.fastjson.JSON;
import com.zslin.basic.repository.SimpleSpecificationBuilder;
import com.zslin.basic.tools.SecurityUtil;
import com.zslin.web.dto.ModuleDto;
import com.zslin.web.model.Module;
import com.zslin.web.model.Url;
import com.zslin.web.service.IModuleService;
import com.zslin.web.service.IUrlService;
import com.zslin.web.tools.SendSmsTools;
import com.zslin.web.tools.SmsException;
import com.zslin.web.tools.SmsSucException;
import com.zslin.wx.tools.JsonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 8:55.
 */
@Component("moduleService")
public class ModuleService {

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IUrlService urlService;

    /**
     * M03-列表短信模板
     * @param param
     * @param token
     * @return
     */
    public String listModule(String param, String token) {
        SimpleSpecificationBuilder builder = new SimpleSpecificationBuilder();
        builder.add("token", "eq", token, "and")
                .add("flag", "eq", "0");
        List<Module> list = moduleService.findAll(builder.generate());
        List<ModuleDto> result = new ArrayList<>();
        for(Module m : list) {
            result.add(new ModuleDto(m.getId(), m.getSign(), m.getContent(), m.getStatus(), m.getReason()));
        }
        String res = JSON.toJSONString(result);
        return res;
    }

    /**
     * M02-删除短信模板
     * @param param
     * @param token
     * @return
     */
    public String deleteModule(String param, String token) {
        if(param==null || "".equals(param)) {return "-1";}
        try {
            Integer id = Integer.parseInt(JsonTools.getJsonParam(param, "id"));
            moduleService.updateFlag(id, token, "1"); //标记为删除
            return "1";
        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return "-2";
            throw new SmsException("非法模板ID", "-2");
        }
    }

    /**
     * M01-添加短信模板
     * @param param 不能为空，包含sign、content
     * @return
     */
    public String addModule(String param, String token)  {
        if(param==null) {
            throw new SmsException("参数不能为空", "-1");
//            return "-1"; //参数不能为空
        }
        String sign = JsonTools.getJsonParam(param, "sign"); //
        String content = JsonTools.getJsonParam(param, "content");
        if(sign==null || "".equals(sign) || content==null || "".equals(content)) {
//            return "-2"; //签名和内容不能为空
            throw new SmsException("签名和内容不能为空", "-2");
        }

        String pwd = "";
        try {
            pwd = SecurityUtil.md5(token, sign+content);
            Module m = moduleService.findByPwd(pwd);
            if(m!=null) {
//                return "-3"; //模板已经存在
                throw new SmsException("模板已经存在", "-3");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String word = SendSmsTools.shield(sign+" "+content); //获取非法关键字

        Url u = urlService.findByToken(token);
        Module module = new Module();
        module.setContent(content);
        module.setSign(sign);
        module.setStatus("0");
        module.setCid(u.getId());
        module.setName(u.getName());
        module.setToken(token);
        module.setPwd(pwd);
        module.setReason(word);
        moduleService.save(module);
        if(word!=null && !"".equals(word)) {
            throw new SmsException(word, "-4");
        } else {
//            return "1";
            ModuleDto md = new ModuleDto(module.getId(), module.getSign(), module.getContent(), module.getStatus(), module.getReason());
            throw new SmsSucException(JSON.toJSONString(md), "1");
        }
    }
}
