package com.zslin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 钟述林 393156105@qq.com on 2017/2/5 11:21.
 */
@Controller
@RequestMapping(value = "test")
public class TestController {

    @GetMapping(value = "index")
    public String index() {
        return "test/index";
    }
}
