package com.yxdtyut.service;

import com.yxdtyut.model.SysRole;
import com.yxdtyut.param.SysRoleVo;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:39 2018/7/22
 */

public interface SysRoleService {
    int saveRole(SysRoleVo sysRoleVo);

    void updateRole(SysRoleVo sysRoleVo);

    List<SysRole> roleList();

    List<SysRole> findSysRoleListByAclId(int aclId);
}
