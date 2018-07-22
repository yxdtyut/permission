package com.yxdtyut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午5:53 2018/7/19
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }
}
