package com.yxdtyut.dao;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int findCountByEmail(@Param("mail") String mail, @Param("id") Integer id);

    int findCountByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    SysUser findSysUserByKeyWord(@Param("keyword") String keyword);

    int findCountByDeptId(int deptId);

    List<SysUser> findSysUserListByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);
}