package com.yxdtyut.controller;

import com.google.common.collect.Maps;
import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.common.Result;
import com.yxdtyut.dto.AclModuleLevelDTO;
import com.yxdtyut.model.SysRole;
import com.yxdtyut.param.SysAclModuleVo;
import com.yxdtyut.param.SysDeptVo;
import com.yxdtyut.param.SysUserVo;
import com.yxdtyut.service.SysRoleUserService;
import com.yxdtyut.service.SysTreeService;
import com.yxdtyut.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

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
        PageResult pageResult = sysUserService.getPageResultByDeptId(deptId, page);
        return Result.success(pageResult);
    }

    @RequestMapping("/acls.json")
    @ResponseBody
    public Result acls(@RequestParam int userId) {
        List<AclModuleLevelDTO> sysAclModuleVoList = sysTreeService.userTree(userId);
        List<SysRole> sysRoles = sysRoleUserService.findSysRoleListByUserId(userId);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("acls", sysAclModuleVoList);
        resultMap.put("roles", sysRoles);
        return Result.success(resultMap);
    }

    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }
}
