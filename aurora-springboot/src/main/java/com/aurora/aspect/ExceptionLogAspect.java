package com.aurora.aspect;

import com.alibaba.fastjson.JSON;
import com.aurora.entity.ExceptionLog;
import com.aurora.event.ExceptionLogEvent;
import com.aurora.utils.ErrorUtils;
import com.aurora.utils.IpUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class ExceptionLogAspect {

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("execution(* com.aurora.controller..*.*(..))")
    public void exceptionLogPointcut() {
    }

    @AfterThrowing(value = "exceptionLogPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        ExceptionLog exceptionLog = new ExceptionLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        // 请求url
        exceptionLog.setOptUrl(Objects.requireNonNull(request).getRequestURI());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 请求方法
        exceptionLog.setOptMethod(methodName);
        // 请求方式
        exceptionLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        // 请求参数
        exceptionLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
        // 请求方法描述
        exceptionLog.setOptDesc(Objects.requireNonNull(apiOperation).value());
        // 异常信息
        exceptionLog.setExceptionInfo(ErrorUtils.getTrace(e));
        // ip地址
        String ipAddress = IpUtils.getIpAddress(request);
        exceptionLog.setIpAddress(ipAddress);
        // ip来源
        exceptionLog.setIpSource(IpUtils.getIpSource(ipAddress));
        // 事件发布
        applicationContext.publishEvent(new ExceptionLogEvent(exceptionLog));
    }

}
