package com.yxdtyut.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yxdtyut.common.Result;
import com.yxdtyut.dto.AclModuleLevelDTO;
import com.yxdtyut.model.SysRole;
import com.yxdtyut.model.SysRoleAcl;
import com.yxdtyut.model.SysUser;
import com.yxdtyut.param.SysRoleVo;
import com.yxdtyut.service.*;
import com.yxdtyut.utils.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:52 2018/7/22
 */
@RequestMapping("/sys/role")
@Controller
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    @RequestMapping("save.json")
    @ResponseBody
    public Result saveRole(SysRoleVo sysRoleVo) {
        sysRoleService.saveRole(sysRoleVo);
        return Result.success(sysRoleVo);
    }

    @RequestMapping("update.json")
    @ResponseBody
    public Result updateRole(SysRoleVo sysRoleVo) {
        sysRoleService.updateRole(sysRoleVo);
        return Result.success(sysRoleVo);
    }

    @RequestMapping("list.json")
    @ResponseBody
    public Result roleList() {
        List<SysRole> sysRoles = sysRoleService.roleList();
        return Result.success(sysRoles);
    }

    @RequestMapping("/roleTree.json")
    @ResponseBody
    public Result tree(@RequestParam int roleId) {
        List<AclModuleLevelDTO> aclModuleLevelDTOS = sysTreeService.roleTree(roleId);
        return Result.success(aclModuleLevelDTOS);
    }

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public Result changeAcls(@RequestParam int roleId, @RequestParam(required = false,defaultValue = "") String aclIds) {
        List<Integer> aclList = StringUtil.parseStringToIntList(aclIds);
        sysRoleAclService.changeRoleAcls(roleId,aclList);
        return Result.success(roleId);
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public Result changeUsers(@RequestParam int roleId, @RequestParam(required = false,defaultValue = "") String userIds) {
        List<Integer> userList = StringUtil.parseStringToIntList(userIds);
        sysRoleUserService.changeRoleUser(roleId,userList);
        return Result.success(roleId);
    }

    @RequestMapping("/users.json")
    @ResponseBody
    public Result users(@RequestParam int roleId) {
        List<SysUser> selectedUser = sysRoleUserService.getSysUserListByRoleId(roleId);
        List<SysUser> allUser = sysUserService.findAll();
        List<SysUser> unSelectedUser = Lists.newArrayList();
        List<Integer> selectedUserIdSet = selectedUser.stream().map(x -> x.getId()).collect(Collectors.toList());
        allUser.stream().forEach((x) -> {
            if (x.getStatus() == 1 && !selectedUserIdSet.contains(x.getId())) {
                unSelectedUser.add(x);
            }
        });
        Map<String,Object> sysUserMap = Maps.newLinkedHashMap();
        sysUserMap.put("selected", selectedUser);
        sysUserMap.put("unselected", unSelectedUser);
        return Result.success(sysUserMap);
    }
}
