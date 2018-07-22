package com.yxdtyut.beans;

import com.yxdtyut.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:52 2018/7/20
 */

public class RequestHolder {
    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
    public static SysUser getCurrentUser() {
        return userHolder.get();
    }
    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }
    public static void addUser(SysUser user) {
        userHolder.set(user);
    }
    public static void addRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }
    public static final void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
