package com.wtychn.tmall.handler;

import com.alibaba.fastjson.JSON;
import com.wtychn.tmall.pojo.User;
import com.wtychn.tmall.util.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        User user = (User) authentication.getPrincipal();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user", user);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSON.toJSONString(Result.success()));
        out.flush();
        out.close();
    }
}
