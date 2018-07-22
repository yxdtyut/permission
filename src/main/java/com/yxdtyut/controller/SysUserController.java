package com.yxdtyut.controller;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.common.Result;
import com.yxdtyut.param.SysDeptVo;
import com.yxdtyut.param.SysUserVo;
import com.yxdtyut.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author : yangxudong
 * @Description :   用户controller
 * @Date : 下午4:11 2018/7/19
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveDept(SysUserVo sysUserVo) {
        sysUserService.save(sysUserVo);
        return Result.success(sysUserVo);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateDept(SysUserVo sysUserVo) {
        sysUserService.update(sysUserVo);
        return Result.success(sysUserVo);
    }

    @RequestMapping("page.json")
    @ResponseBody
    public Result page(@RequestParam int deptId, PageQuery page) {
        PageResult pageResult = sysUserService.getPageResultByDeptId(deptId,page);
        return Result.success(pageResult);
    }
}
