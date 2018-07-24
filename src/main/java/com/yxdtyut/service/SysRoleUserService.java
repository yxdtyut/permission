package com.yxdtyut.service;

import com.yxdtyut.model.SysRole;
import com.yxdtyut.model.SysUser;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:09 2018/7/23
 */

public interface SysRoleUserService {
    List<SysUser> getSysUserListByRoleId(int roleId);

    void changeRoleUser(int roleId, List<Integer> userList);

    List<SysRole> findSysRoleListByUserId(int userId);

    List<SysUser> findUserListByRoleList(List<SysRole> sysRoles);
}
