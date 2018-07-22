package com.yxdtyut.exception;

import com.yxdtyut.common.CodeMsg;
import lombok.Getter;

/**
 * @Author : yangxudong
 * @Description :   参数异常
 * @Date : 下午2:32 2018/7/18
 */
@Getter
public class ParamException extends RuntimeException {

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
