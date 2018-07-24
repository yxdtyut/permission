package com.yxdtyut.dao;

import com.yxdtyut.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int getCountByParentIdAndName(@Param("parentId") Integer parentId,@Param("name") String name,@Param("id") Integer id);

    List<SysAclModule> findSysAclModuleListByLevel(@Param("level") String level);

    void batchUpdateDept(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    List<SysAclModule> findSysAclModuleList();

    int findAclModuleCountByParentId(@Param("aclModuleId") int aclModuleId);
}