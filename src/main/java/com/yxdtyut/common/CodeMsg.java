package com.yxdtyut.common;

import lombok.Getter;

/**
 * @Author : yangxudong
 * @Description :   错误码
 * @Date : 下午3:04 2018/5/29
 */
@Getter
public class CodeMsg {
    /**通用模块.*/
    public static final CodeMsg SUCCESS = new CodeMsg(200, "成功");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500, "服务异常");

    /**部门模块.5001xx*/
    public static final CodeMsg DEPT_NAME_REPETITION = new CodeMsg(500100, "同一层级下部门名称重复");
    public static final CodeMsg DEPT_NOT_EXIST = new CodeMsg(500101, "部门不存在");

    /**用户模块.5002xx*/
    public static final CodeMsg USER_EMAIL_EXIST = new CodeMsg(500200, "email已经存在");
    public static final CodeMsg USER_TELEPHONE_EXIST = new CodeMsg(500201, "手机号已经存在");
    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(500203, "用户不存在");

    /**权限模块.5003xx*/
    public static final CodeMsg ACL_MODULE_NAME_REPETITION = new CodeMsg(500301, "同一层级下权限模块名称重复");
    public static final CodeMsg ACL_MODULE_NOT_EXIST = new CodeMsg(500302, "该权限模块不存在");

    /**权限.5004xx*/
    public static final CodeMsg ACL_NAME_REPETITION = new CodeMsg(500401, "同一权限模块下权限名称重复");
    public static final CodeMsg ACL_NOT_EXIST = new CodeMsg(500402, "权限不存在");




    private Integer code;
    private String msg;

    private CodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg(String msg) {
        this.code = 500;
        this.msg = msg;
    }

    private CodeMsg() {
    }
}
