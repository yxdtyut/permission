package com.yxdtyut.controller;

import com.yxdtyut.common.Result;
import com.yxdtyut.param.SysAclModuleVo;
import com.yxdtyut.service.SysAclModuleService;
import com.yxdtyut.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author : yangxudong
 * @Description :   权限模块controller
 * @Date : 下午3:32 2018/7/20
 */
@Controller
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService aclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveAclModule(SysAclModuleVo sysAclModuleVo) {
        int count = aclModuleService.save(sysAclModuleVo);
        return Result.success(sysAclModuleVo);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateAclModule(SysAclModuleVo sysAclModuleVo) {
        aclModuleService.update(sysAclModuleVo);
        return Result.success(sysAclModuleVo);
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public Result tree() {
        return Result.success(sysTreeService.aclModuleTree());
    }

}
