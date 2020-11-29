package com.ujiuye.mis.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器,判断是否登录成功
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer active = (Integer) request.getSession().getAttribute("active");

        if (active == null){
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
