package com.yxdtyut.service;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.model.SysUser;
import com.yxdtyut.param.SysUserVo;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:20 2018/7/19
 */

public interface SysUserService {
    int save(SysUserVo sysUserVo);

    int update(SysUserVo sysUserVo);

    SysUser findSysUserByKeyWord(String username);

    PageResult getPageResultByDeptId(int deptId, PageQuery page);

    List<SysUser> findAll();
}
