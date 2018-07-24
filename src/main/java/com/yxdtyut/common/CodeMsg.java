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
    public static final CodeMsg NO_PERMISSION_ERROR = new CodeMsg(501, "没有访问权限，如需要访问，请联系管理员");

    /**部门模块.5001xx*/
    public static final CodeMsg DEPT_NAME_REPETITION = new CodeMsg(500100, "同一层级下部门名称重复");
    public static final CodeMsg DEPT_NOT_EXIST = new CodeMsg(500101, "部门不存在");
    public static final CodeMsg WAIT_DELETE_DEPT_NOT_EXIST = new CodeMsg(500102, "待删除部门不存在");
    public static final CodeMsg WAIT_DELETE_DEPT_HAS_CHILD_DEPT = new CodeMsg(500103, "待删除部门存在子部门无法删除");
    public static final CodeMsg WAIT_DELETE_DEPT_HAS_USER = new CodeMsg(500104, "待删除部门存在用户无法删除");

    /**用户模块.5002xx*/
    public static final CodeMsg USER_EMAIL_EXIST = new CodeMsg(500200, "email已经存在");
    public static final CodeMsg USER_TELEPHONE_EXIST = new CodeMsg(500201, "手机号已经存在");
    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(500203, "用户不存在");

    /**权限模块.5003xx*/
    public static final CodeMsg ACL_MODULE_NAME_REPETITION = new CodeMsg(500301, "同一层级下权限模块名称重复");
    public static final CodeMsg ACL_MODULE_NOT_EXIST = new CodeMsg(500302, "该权限模块不存在");
    public static final CodeMsg WAIT_DELETE_ACL_MODULE_NOT_EXIST = new CodeMsg(500303, "待删除权限模块不存在");
    public static final CodeMsg WAIT_DELETE_ACL_MODULE_HAS_CHILD_ACLMODULE = new CodeMsg(500303, "待删除权限模块存在子模块，无法删除");
    public static final CodeMsg WAIT_DELETE_ACL_MODULE_HAS_ACL = new CodeMsg(500303, "待删除权限模块存在权限点，无法删除");

    /**权限.5004xx*/
    public static final CodeMsg ACL_NAME_REPETITION = new CodeMsg(500401, "同一权限模块下权限名称重复");
    public static final CodeMsg ACL_NOT_EXIST = new CodeMsg(500402, "权限不存在");

    /**角色模块.5005xx*/
    public static final CodeMsg ROLE__REPETITION = new CodeMsg(500501, "角色名称重复");
    public static final CodeMsg ROLE_NOT_EXIST = new CodeMsg(500502, "角色不存在");




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
