package com.yxdtyut.exception;

import com.yxdtyut.common.CodeMsg;
import lombok.Getter;

/**
 * @Author : yangxudong
 * @Description :   权限异常
 * @Date : 上午10:44 2018/7/18
 */
@Getter
public class PermissionException extends RuntimeException {
    private CodeMsg codeMsg;

    public PermissionException() {
        super();
    }

    public PermissionException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }

    protected PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
