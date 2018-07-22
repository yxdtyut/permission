package com.yxdtyut.dto;

import com.yxdtyut.model.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author : yangxudong
 * @Description :   部门根据等级的dto
 * @Date : 下午5:59 2018/7/18
 */
@Data
public class DeptLevelDTO extends SysDept {
    /** 下级部门集合.*/
    private List<DeptLevelDTO> deptList = new ArrayList<>();

    public static DeptLevelDTO adapt(SysDept sysDept) {
        DeptLevelDTO deptLevelDTO = new DeptLevelDTO();
        BeanUtils.copyProperties(sysDept,deptLevelDTO);
        return deptLevelDTO;
    }
}
