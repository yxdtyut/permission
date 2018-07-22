package com.yxdtyut.service;

import com.yxdtyut.dto.AclModuleLevelDTO;
import com.yxdtyut.dto.DeptLevelDTO;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午5:59 2018/7/18
 */

public interface SysTreeService {
    /** 获取部门树.*/
    List<DeptLevelDTO> deptTree();

    List<AclModuleLevelDTO> aclModuleTree();
}
