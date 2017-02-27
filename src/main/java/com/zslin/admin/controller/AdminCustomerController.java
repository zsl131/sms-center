package com.zslin.admin.controller;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.annotations.Token;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.basic.tools.RandomTools;
import com.zslin.basic.tools.TokenTools;
import com.zslin.basic.utils.ParamFilterUtil;
import com.zslin.web.model.Customer;
import com.zslin.web.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:03.
 */
@Controller
@RequestMapping(value = "admin/customer")
@AdminAuth(name = "接入管理", orderNum = 1, porderNum = 1, psn = "应用管理", pentity = 0, icon = "fa fa-address-book")
public class AdminCustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping(value = "list")
    @AdminAuth(name = "接入管理", orderNum = 1, type = "1", icon = "fa fa-address-book")
    public String list(Model model, Integer page, HttpServletRequest request) {
        Page<Customer> datas = customerService.findAll(ParamFilterUtil.getInstance().buildSearch(model, request),
                SimplePageBuilder.generate(page));
        model.addAttribute("datas", datas);
        return "admin/customer/list";
    }

    @Token(flag= Token.READY)
    @AdminAuth(name = "添加接入商", orderNum = 2, icon="fa fa-plus")
    @RequestMapping(value="add", method= RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        Customer c = new Customer();
        model.addAttribute("customer", c);
        return "admin/customer/add";
    }

    @Token(flag= Token.CHECK)
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String add(Model model, Customer customer, HttpServletRequest request) {
        if(TokenTools.isNoRepeat(request)) { //不是重复提交
            customer.setToken(RandomTools.randomString(16));
            customerService.save(customer);
        }
        return "redirect:/admin/customer/list";
    }

    @Token(flag= Token.READY)
    @AdminAuth(name="修改接入商", orderNum=3, icon = "fa fa-pencil")
    @RequestMapping(value="update/{id}", method=RequestMethod.GET)
    public String update(Model model, @PathVariable Integer id, HttpServletRequest request) {
        Customer c = customerService.findOne(id);
        model.addAttribute("customer", c);
        return "admin/customer/update";
    }

    @Token(flag= Token.CHECK)
    @RequestMapping(value="update/{id}", method=RequestMethod.POST)
    public String update(Model model, @PathVariable Integer id, Customer customer, HttpServletRequest request) {
//		Boolean isRepeat = (Boolean) request.getAttribute("isRepeat");
        if(TokenTools.isNoRepeat(request)) {
            Customer c = customerService.findOne(id);
            MyBeanUtils.copyProperties(customer, c, new String[]{"id", "createDate", "createLong", "createDay", "createTime"});
            customerService.save(c);
        }
        return "redirect:/admin/customer/list";
    }

    @AdminAuth(name="删除接入商", orderNum=4, icon = "fa fa-remove")
    @RequestMapping(value="delete/{id}", method=RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Integer id) {
        try {
            customerService.delete(id);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }
}
