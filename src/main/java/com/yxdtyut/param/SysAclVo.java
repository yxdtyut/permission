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
 * @Date : 下午6:12 2018/7/21
 */
@Data
public class SysAclVo {
    private Integer id;

    @NotBlank(message = "权限名称不能为空")
    @Length(min = 2,max = 20, message = "权限名称必须在2-20个字符内")
    private String name;

    @NotNull(message = "权限模块id不能为空")
    private Integer aclModuleId;

    @NotNull(message = "权限url不能为空")
    @Length(min = 6,max = 200,message = "权限url需在6-200字符内")
    private String url;

    @NotNull(message = "权限类型不能为空")
    @Min(value = 1, message = "权限类型格式不对")
    @Max(value = 3, message = "权限类型格式不对")
    private Integer type;

    @NotNull(message = "权限状态不能为空")
    @Min(value = 0, message = "权限状态格式不对")
    @Max(value = 1, message = "权限状态格式不对")
    private Integer status;

    @NotNull(message = "权限顺序不能为空")
    private Integer seq;

    @Length(max = 200, message = "备注需在200个字符内")
    private String remark;
}
