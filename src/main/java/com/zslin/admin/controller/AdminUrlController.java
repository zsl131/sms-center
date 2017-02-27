package com.zslin.admin.controller;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.annotations.Token;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSpecificationBuilder;
import com.zslin.basic.tools.MyBeanUtils;
import com.zslin.basic.tools.RandomTools;
import com.zslin.basic.tools.TokenTools;
import com.zslin.basic.utils.ParamFilterUtil;
import com.zslin.web.model.Code;
import com.zslin.web.model.UC;
import com.zslin.web.model.Url;
import com.zslin.web.service.ICodeService;
import com.zslin.web.service.IUCService;
import com.zslin.web.service.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:03.
 */
@Controller
@RequestMapping(value = "admin/url")
@AdminAuth(name = "连接管理", orderNum = 1, porderNum = 1, psn = "接口管理", pentity = 0, icon = "fa fa-chain")
public class AdminUrlController {

    @Autowired
    private IUrlService urlService;

    @Autowired
    private ICodeService codeService;

    @Autowired
    private IUCService ucService;

    @GetMapping(value = "list")
    @AdminAuth(name = "连接管理", orderNum = 1, type = "1", icon = "fa fa-chain")
    public String list(Model model, Integer page, HttpServletRequest request) {
        Page<Url> datas = urlService.findAll(ParamFilterUtil.getInstance().buildSearch(model, request),
                SimplePageBuilder.generate(page));
        model.addAttribute("datas", datas);
        return "admin/url/list";
    }

    @Token(flag= Token.READY)
    @AdminAuth(name = "添加连接", orderNum = 2, icon="fa fa-plus")
    @RequestMapping(value="add", method= RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        Url u = new Url();
        model.addAttribute("url", u);
        return "admin/url/add";
    }

    @Token(flag= Token.CHECK)
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String add(Model model, Url url, HttpServletRequest request) {
        if(TokenTools.isNoRepeat(request)) { //不是重复提交
            url.setToken(RandomTools.randomString(16));
            urlService.save(url);
        }
        return "redirect:/admin/url/list";
    }

    @Token(flag= Token.READY)
    @AdminAuth(name="修改连接", orderNum=3, icon = "fa fa-pencil")
    @RequestMapping(value="update/{id}", method=RequestMethod.GET)
    public String update(Model model, @PathVariable Integer id, HttpServletRequest request) {
        Url u = urlService.findOne(id);
        model.addAttribute("url", u);
        return "admin/url/update";
    }

    @Token(flag= Token.CHECK)
    @RequestMapping(value="update/{id}", method=RequestMethod.POST)
    public String update(Model model, @PathVariable Integer id, Url url, HttpServletRequest request) {
//		Boolean isRepeat = (Boolean) request.getAttribute("isRepeat");
        if(TokenTools.isNoRepeat(request)) {
            Url u = urlService.findOne(id);
            MyBeanUtils.copyProperties(url, u, new String[]{"id", "amount"});
            urlService.save(u);
        }
        return "redirect:/admin/url/list";
    }

    @AdminAuth(name="删除连接", orderNum=4, icon = "fa fa-remove")
    @RequestMapping(value="delete/{id}", method=RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable Integer id) {
        try {
            urlService.delete(id);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    /** 授权 */
    @GetMapping(value = "auth")
    @AdminAuth(name="连接授权", orderNum=5, icon = "fa fa-key")
    public String auth(Model model, Integer id, HttpServletRequest request) {
        model.addAttribute("url", urlService.findOne(id));
        SimpleSpecificationBuilder builder = new SimpleSpecificationBuilder("uid", "eq", id);
        List<UC> list = ucService.findAll(builder.generate());
        model.addAttribute("datas", list); //已经拥有的权限

        List<Code> codeList = codeService.findAll();
        model.addAttribute("codeList", codeList);
        return "admin/url/auth";
    }

    @RequestMapping(value="auth", method=RequestMethod.POST)
    public @ResponseBody String auth(Integer uid, Integer cid) {
        try {
            UC uc = ucService.findByUidAndCid(uid, cid);
            if(uc==null) {
                uc = new UC();
                Url u = urlService.findOne(uid);
                Code c = codeService.findOne(cid);
                uc.setToken(u.getToken());
                uc.setCid(cid);
                uc.setCode(c.getC());
                uc.setMethodName(c.getMethodName());
                uc.setServiceName(c.getServiceName());
                uc.setUid(uid);
                ucService.save(uc);
            } else {
                ucService.delete(uc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }
}
