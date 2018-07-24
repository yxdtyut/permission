package com.yxdtyut.controller;

import com.google.common.collect.Maps;
import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.common.Result;
import com.yxdtyut.dao.SysRoleAclMapper;
import com.yxdtyut.dto.AclModuleLevelDTO;
import com.yxdtyut.model.SysRole;
import com.yxdtyut.param.SysAclModuleVo;
import com.yxdtyut.param.SysAclVo;
import com.yxdtyut.param.SysDeptVo;
import com.yxdtyut.service.SysAclService;
import com.yxdtyut.service.SysRoleAclService;
import com.yxdtyut.service.SysRoleService;
import com.yxdtyut.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

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

    @RequestMapping("/acls.json")
    @ResponseBody
    public Result acls(@RequestParam int aclId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<SysRole> sysRoles = sysRoleService.findSysRoleListByAclId(aclId);
        resultMap.put("roles", sysRoles);
        resultMap.put("users", sysRoleUserService.findUserListByRoleList(sysRoles));
        return Result.success(resultMap);
    }

}
