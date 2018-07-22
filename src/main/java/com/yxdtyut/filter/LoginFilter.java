package com.yxdtyut.filter;

import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.model.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:56 2018/7/20
 */

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
        if (sysUser == null) {
            response.sendRedirect("/signin.jsp");
            return;
        }
        RequestHolder.addUser(sysUser);
        RequestHolder.addRequest(request);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
