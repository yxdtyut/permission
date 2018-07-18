package com.yxdtyut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午5:38 2018/7/17
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello permission";
    }
}
