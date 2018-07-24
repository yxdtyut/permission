package com.yxdtyut.dao;

import com.yxdtyut.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> findSysDeptList();

    List<SysDept> findSysDeptListByLevel(@Param("level") String level);

    void batchUpdateDept(@Param("deptList") List<SysDept> sysDeptList);

    int getCountByParentIdAndName(@Param("parentId") Integer parentId,@Param("name") String name,@Param("id") Integer id);

    int findSysDeptCountByParentId(@Param("deptId") int deptId);
}