package com.circle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by keweiyang on 2017/6/24.
 */
@Controller
public class PageController {

    @RequestMapping(value = "/")
    public String showIndex() {
        System.out.println("12312312312");
        return "/index2.html";
    }

    @RequestMapping("toLogin")
    public String toLogin() {
        return "/login.html";
    }

    @RequestMapping("toIndex")
    public String toIndex() {

        return "/index2.html";
    }
}
