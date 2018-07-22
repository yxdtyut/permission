package com.yxdtyut.param;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author : yangxudong
 * @Description : 用户vo类
 * @Date : 下午3:00 2018/7/19
 */
@Data
public class SysUserVo {
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "用户名长度需要在20字以内")
    private String username;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 5, max = 13, message = "手机号长度不对")
    private String telephone;

    @NotBlank(message = "邮箱不能为空")
    @Length(min = 6, max = 50, message = "邮箱长度需要在6-50个字符内")
    @Email(message = "邮箱格式不对")
    private String mail;

    @NotNull(message = "所在部门不能为空")
    private Integer deptId;

    @NotNull(message = "用户状态不能为空")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(max = 200, message = "备注长度需在200字以内")
    private String remark;
}
