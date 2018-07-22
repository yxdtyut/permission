package com.yxdtyut.service;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.param.SysAclVo;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午6:20 2018/7/21
 */

public interface SysAclService {
    int saveAcl(SysAclVo sysAclVo);

    void updateAcl(SysAclVo sysAclVo);

    PageResult pageAclByAclModuleId(int aclModuleId, PageQuery pageQuery);
}
