package com.yxdtyut.param;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @Author : yangxudong
 * @Description :部门的vo类
 * @Date : 下午4:04 2018/7/18
 */
@Data
public class SysDeptVo {
    private Integer id;
    @NotBlank(message = "部门名称不能为空")
    @Length(min = 2, max = 15, message = "部门名称字数必须在2-15字")
    private String name;
    private Integer parentId = 0;
    @NotNull(message = "部门顺序不能为空")
    private Integer seq;
    @Length(max = 150, message = "备注字数不能超过150字")
    private String remark;
}
