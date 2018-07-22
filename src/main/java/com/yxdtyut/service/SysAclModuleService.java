package com.yxdtyut.service;

import com.yxdtyut.param.SysAclModuleVo; /**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:49 2018/7/20
 */

public interface SysAclModuleService {
    int save(SysAclModuleVo sysAclModuleVo);

    void update(SysAclModuleVo sysAclModuleVo);
}
