package com.yxdtyut.controller;

import com.yxdtyut.model.SysUser;
import com.yxdtyut.service.SysUserService;
import com.yxdtyut.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午5:39 2018/7/19
 */
@Controller
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/login.page")
    public void login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) String ret,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        SysUser sysUser = sysUserService.findSysUserByKeyWord(username);
        String errorMsg = null;
        if (sysUser == null) {
            errorMsg = "用户不存在";
        } else if (!MD5Util.encrypt(password).equals(sysUser.getPassword())) {
            errorMsg = "用户名或密码错误";
        } else if(sysUser.getStatus() != 1) {
            errorMsg = "用户已被冻结或删除，请联系管理员";
        }else {
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page");
            }
        }
        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret",ret);
        }
        request.getRequestDispatcher("/signin.jsp").forward(request,response);
    }

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request,
                      HttpServletResponse response) throws Exception{
        request.getSession().invalidate();
        response.sendRedirect("/signin.jsp");
    }
}
