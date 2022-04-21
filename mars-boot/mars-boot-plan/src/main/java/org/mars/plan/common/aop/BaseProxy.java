package org.mars.plan.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.StrUtil;

public interface BaseProxy {

    /**
     * 环绕拦截
     * 
     * @param pdj {@link ProceedingJoinPoint}
     * @return Object
     * @throws Throwable 框架中可能出现的所有异常
     */
    Object around(ProceedingJoinPoint pdj) throws Throwable;

    /**
     * 获取方法
     * 
     * @param pdj {@link ProceedingJoinPoint}
     * @return {@link Method}
     */
    default Method getMethod(ProceedingJoinPoint pdj) {
        Signature signature = pdj.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method;
    }

    /**
     * 获取request
     * 
     * @return {@link HttpServletRequest}
     */
    default HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr != null) {
                return attr.getRequest();
            }

        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取response
     * 
     * @return {@link HttpServletResponse}
     */
    default HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        return response;
    }

    /**
     * 获取请求地址
     * 
     * @param request       httpRequest
     * @param realm_current 例如：http://www.dcdyxxx.com 不要+/ 当前域名
     * @return String
     */
    default String getRequestEncodeUrl(HttpServletRequest request, String realm_current) {
        try {
            String param = "";
            String url = realm_current + request.getRequestURI();
            if (request.getQueryString() != null) {
                param = "?" + request.getQueryString();
            }
            return "?redirectUrl=" + java.net.URLEncoder.encode(url + param, "UTF-8");
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 判断方法是不是ajax
     * 
     * @param request httpRequest
     * @return boolean
     */
    default boolean requestIsAjax(HttpServletRequest request) {
        boolean isAjax = false;
        if (!StrUtil.isBlank(request.getHeader("x-requested-with"))
                && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
            isAjax = true;
        }
        return isAjax;
    }

    /**
     * 是否包含
     * 
     * @param object 对象
     * @return boolean
     */
    default boolean isInclude(Object object) {
        return (object instanceof HttpServletRequest//
                || object instanceof HttpServletResponse//
                || object instanceof HttpSession//
        );
    }

}
