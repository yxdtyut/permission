package com.yxdtyut.dao;

import com.yxdtyut.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int getCountByName(@Param("name") String name,@Param("id") Integer id);

    List<SysRole> roleList();

    List<SysRole> selectSysRoleListByRoleIds(@Param("roleIdList") List<Integer> roleIds);
}