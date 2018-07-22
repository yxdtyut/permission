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
 * @Date : 下午3:32 2018/7/20
 */
@Data
public class SysAclModuleVo {
    private Integer id;

    @NotBlank(message = "模块名称不能为空")
    @Length(min = 2, max = 20, message = "模块名称需在2-20个字符内")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "模块状态不能为空")
    @Min(value = 0, message = "模块状态数值有误")
    @Max(value = 1, message = "模块状态数值有误")
    private Integer status;

    @NotNull(message = "模块顺序不能为空")
    private Integer seq;

    @Length(max = 200, message = "备注需要200字以内")
    private String remark;
}
