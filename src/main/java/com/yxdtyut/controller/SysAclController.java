package com.yxdtyut.controller;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.common.Result;
import com.yxdtyut.param.SysAclModuleVo;
import com.yxdtyut.param.SysAclVo;
import com.yxdtyut.param.SysDeptVo;
import com.yxdtyut.service.SysAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author : yangxudong
 * @Description :   权限controller
 * @Date : 下午3:32 2018/7/20
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveAcl(SysAclVo sysAclVo) {
        sysAclService.saveAcl(sysAclVo);
        return Result.success(sysAclVo);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateAcl(SysAclVo sysAclVo) {
        sysAclService.updateAcl(sysAclVo);
        return Result.success(sysAclVo);
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public Result page(@RequestParam int aclModuleId, PageQuery pageQuery) {
        PageResult pageResult = sysAclService.pageAclByAclModuleId(aclModuleId,pageQuery);
        return Result.success(pageResult);
    }

}
