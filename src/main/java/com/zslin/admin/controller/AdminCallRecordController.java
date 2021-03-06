package com.zslin.admin.controller;

import com.zslin.basic.annotations.AdminAuth;
import com.zslin.basic.repository.SimplePageBuilder;
import com.zslin.basic.repository.SimpleSortBuilder;
import com.zslin.basic.utils.ParamFilterUtil;
import com.zslin.web.model.CallRecord;
import com.zslin.web.model.Send;
import com.zslin.web.service.ICallRecordService;
import com.zslin.web.service.ISendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 15:36.
 */
@Controller
@RequestMapping(value = "admin/callRecord")
@AdminAuth(name = "接口调用记录", orderNum = 3, porderNum = 1, psn = "应用管理", pentity = 0, icon = "fa fa-database")
public class AdminCallRecordController {

    @Autowired
    private ICallRecordService callRecordService;

    @GetMapping(value = "list")
    @AdminAuth(name = "接口调用记录", orderNum = 1, type = "1", icon = "fa fa-database")
    public String list(Model model, Integer page, HttpServletRequest request) {
        Page<CallRecord> datas = callRecordService.findAll(ParamFilterUtil.getInstance().buildSearch(model, request),
                SimplePageBuilder.generate(page, SimpleSortBuilder.generateSort("createDate_d")));
        model.addAttribute("datas", datas);
        return "admin/callRecord/list";
    }

}
