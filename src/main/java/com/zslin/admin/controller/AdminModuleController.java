package com.zslin.admin.controller;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.annotations.Token;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.basic.tools.TokenTools;
import com.zslin.basic.utils.ParamFilterUtil;
import com.zslin.web.model.Charge;
import com.zslin.web.model.Customer;
import com.zslin.web.model.Module;
import com.zslin.web.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/6 15:14.
 */
@Controller
@RequestMapping(value = "admin/module")
@AdminAuth(name = "短信模板管理", orderNum = 4, porderNum = 1, psn = "应用管理", pentity = 0, icon = "fa fa-book")
public class AdminModuleController {

    @Autowired
    private IModuleService moduleService;

    @GetMapping(value = "list")
    @AdminAuth(name = "短信模板管理", orderNum = 1, type = "1", icon = "fa fa-book")
    public String list(Model model, Integer page, HttpServletRequest request) {
        Page<Module> datas = moduleService.findAll(ParamFilterUtil.getInstance().buildSearch(model, request),
                SimplePageBuilder.generate(page, SimpleSortBuilder.generateSort("id_d")));
        model.addAttribute("datas", datas);
        return "admin/module/list";
    }

    @Token(flag= Token.READY)
    @AdminAuth(name="修改短信模板", orderNum=3, icon = "fa fa-pencil")
    @RequestMapping(value="update/{id}", method= RequestMethod.GET)
    public String update(Model model, @PathVariable Integer id, HttpServletRequest request) {
        Module m = moduleService.findOne(id);
        model.addAttribute("module", m);
        return "admin/module/update";
    }

    @Token(flag= Token.CHECK)
    @RequestMapping(value="update/{id}", method=RequestMethod.POST)
    public String update(Model model, @PathVariable Integer id, Module module, HttpServletRequest request) {
        if(TokenTools.isNoRepeat(request)) {
            Module m = moduleService.findOne(id);
            MyBeanUtils.copyProperties(module, m, new String[]{"id"});
            moduleService.save(m);
        }
        return "redirect:/admin/module/list";
    }

    @AdminAuth(name="删除短信模板", orderNum=4, icon = "fa fa-remove")
    @RequestMapping(value="delete/{id}", method=RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable Integer id) {
        try {
            moduleService.delete(id);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }
}
