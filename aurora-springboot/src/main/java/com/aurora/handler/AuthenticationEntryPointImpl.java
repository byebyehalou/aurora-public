package com.aurora.handler;

import com.alibaba.fastjson.JSON;
import com.aurora.constant.CommonConst;
import com.aurora.vo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(CommonConst.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.fail(40001, "用户未登录")));
    }
}
