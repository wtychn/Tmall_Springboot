package com.wtychn.tmall.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")){//请求不为Post方式
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        if(request.getContentType()==null){//使用PostMan发送表单登录时会出现空指针异常
            throw new AuthenticationServiceException("登陆方式不正确");
        }
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) ||
            request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            //如果是Json数据格式请求登录
            String jsonData=getJsonParam(request);
            JSONObject jsonObject= JSON.parseObject(jsonData);
            String username = jsonObject.getString("name");//获取用户名
            String password = jsonObject.getString("password");//密码
            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }else{
            throw new AuthenticationServiceException("登陆方式不正确");//禁止使用非application/json格式的登录方式
        }
    }

    /**
     * 获取HttpServletRequest中的Json数据
     *
     * @param request
     * @return
     */
    private String getJsonParam(HttpServletRequest request) {
        String jsonParam = "";
        ServletInputStream inputStream = null;
        try {
            int contentLength = request.getContentLength();
            if (!(contentLength < 0)) {
                byte[] buffer = new byte[contentLength];
                inputStream = request.getInputStream();
                for (int i = 0; i < contentLength; ) {
                    int len = inputStream.read(buffer, i, contentLength);
                    if (len == -1) {
                        break;
                    }
                    i += len;
                }
                jsonParam = new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            logger.error("参数转换成json异常g{}", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("参数转换成json异常s{}", e);
                }
            }
        }
        return jsonParam;
    }

}
