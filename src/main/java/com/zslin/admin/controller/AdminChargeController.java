package com.zslin.admin.controller;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.annotations.Token;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.NormalTools;
import com.zslin.basic.tools.TokenTools;
import com.zslin.basic.utils.ParamFilterUtil;
import com.zslin.web.model.Charge;
import com.zslin.web.model.Url;
import com.zslin.web.service.IChargeService;
import com.zslin.web.service.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:29.
 */
@Controller
@RequestMapping(value = "admin/charge")
@AdminAuth(name = "充值管理", orderNum = 2, porderNum = 1, psn = "应用管理", pentity = 0, icon = "fa fa-cny")
public class AdminChargeController {

    @Autowired
    private IChargeService chargeService;

    @Autowired
    private IUrlService urlService;

    @GetMapping(value = "list")
    @AdminAuth(name = "充值管理", orderNum = 1, type = "1", icon = "fa fa-cny")
    public String list(Model model, Integer page, HttpServletRequest request) {
        Page<Charge> datas = chargeService.findAll(ParamFilterUtil.getInstance().buildSearch(model, request),
                SimplePageBuilder.generate(page, SimpleSortBuilder.generateSort("createDate_d")));
        model.addAttribute("datas", datas);
        return "admin/charge/list";
    }

    @Token(flag= Token.READY)
    @AdminAuth(name = "充值", orderNum = 2, icon="fa fa-plus")
    @RequestMapping(value="add", method= RequestMethod.GET)
    public String add(Model model, Integer cid, HttpServletRequest request) {
        Charge c = new Charge();
        model.addAttribute("charge", c);
        model.addAttribute("url", urlService.findOne(cid));
        return "admin/charge/add";
    }

    @Token(flag= Token.CHECK)
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String add(Model model, Charge charge, HttpServletRequest request) {
        if(TokenTools.isNoRepeat(request)) { //不是重复提交
            Url u = urlService.findOne(charge.getCid());
            charge.setToken(u.getToken());
            charge.setName(u.getName());
            charge.setResult(charge.getAmount()+u.getAmount());
            charge.setCreateDate(new Date());
            charge.setCreateDay(NormalTools.curDate("yyyy-MM-dd"));
            charge.setCreateLong(System.currentTimeMillis());
            charge.setCreateTime(NormalTools.curDate("yyyy-MM-dd HH:mm:ss"));
            u.setAmount(charge.getResult());
            urlService.save(u);
            chargeService.save(charge);
        }
        return "redirect:/admin/charge/list";
    }
}
