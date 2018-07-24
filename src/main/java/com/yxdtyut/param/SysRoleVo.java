package com.yxdtyut.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:36 2018/7/22
 */
@Data
public class SysRoleVo {
    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2,max = 20, message = "角色名称需在2-20个字符内")
    private String name;

    private Integer type = 1;

    @NotNull(message = "角色状态不能为空")
    @Min(value = 0,message = "角色状态信息有误")
    @Max(value = 1,message = "角色状态信息有误")
    private Integer status;

    @Length(max = 200, message = "备注参数字数需在200个字符内")
    private String remark;
}
