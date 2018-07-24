package com.yxdtyut.dao;

import com.yxdtyut.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> selectAclIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);

    void deleteByRoleId(@Param("roleId") int roleId);

    void batchInsert(@Param("sysRoleAclList") List<SysRoleAcl> sysRoleAcls);

    List<Integer> findRoleIdListByAclId(@Param("aclId") int aclId);
}