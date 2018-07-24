package com.yxdtyut.service;

import com.yxdtyut.model.SysAcl;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午5:54 2018/7/22
 */

public interface SysCoreService {
    List<SysAcl> getUserAclList(int userId);

    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getRoleAclList(int roleId);

    boolean hasUrlAcl(String servletPath);
}
