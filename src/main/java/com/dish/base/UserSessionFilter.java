package com.dish.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-3-30
 * Time: 下午7:57
 * 用户会话过滤器
 */
public class UserSessionFilter extends OncePerRequestFilter {

    private final static String NEED_LOGIN_URL = "/home";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Object userName = session.getAttribute("userName");
        if (userName != null) {
            UserSessionManager.setLoginUserName(String.valueOf(userName));
        }
        String contextPath = request.getContextPath();
        if (StringUtils.startsWith(request.getRequestURI(), contextPath + "/resources/")) {
            filterChain.doFilter(request, response);
        } else {
            if (StringUtils.startsWithIgnoreCase(request.getRequestURI(), contextPath + NEED_LOGIN_URL)) {
                if (userName == null) {
                    response.sendRedirect(contextPath + "/");
                    return;
                }
            }
            try {
                filterChain.doFilter(request, response);
            } finally {
                UserSessionManager.clear();
            }
        }

    }

}
