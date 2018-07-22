package com.yxdtyut.dao;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int getCountByAclModuleIdAndName(@Param("aclModuleId") Integer aclModuleId,@Param("name") String name,@Param("id") Integer id);

    int getCountByAclModuleId(int aclModuleId);

    List<SysAcl> selectAclListByAclModuleId(@Param("aclModuleId")int aclModuleId,@Param("page") PageQuery pageQuery);
}