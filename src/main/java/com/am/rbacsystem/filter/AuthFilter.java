package com.am.rbacsystem.filter;

import com.am.rbacsystem.constant.SysConstant;
import com.am.rbacsystem.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("经过AuthFilter过滤器。");

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        String uri = req.getRequestURI();
        if(uri.endsWith("/login.html") ||
                uri.endsWith("/doLogin")||
                uri.endsWith(".css")||
                uri.endsWith(".js")){
            filterChain.doFilter(req,servletResponse);
            return;
        }


        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        User user = (User)session.getAttribute(SysConstant.CURRENT_USER);
        if(user != null){
            filterChain.doFilter(servletRequest,servletResponse);

        }else{
            HttpServletResponse resp = (HttpServletResponse)servletResponse;
            resp.sendRedirect("/login.html");
        }
    }


}
