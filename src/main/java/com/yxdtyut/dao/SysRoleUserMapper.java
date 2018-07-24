package com.yxdtyut.dao;

import com.yxdtyut.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<Integer> selectRoleIdsByUserId(@Param("userId") int userId);

    List<Integer> findUserIdsByRoleId(int roleId);

    void deleteByRoleId(@Param("roleId") int roleId);

    void batchInsert(@Param("sysRoleUserList") List<SysRoleUser> sysRoleUsers);

    List<Integer> findUserIdsByRoleIdList(@Param("roleIdList") List<Integer> roleIds);
}