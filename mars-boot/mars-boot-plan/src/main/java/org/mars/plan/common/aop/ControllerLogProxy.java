package org.mars.plan.common.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mars.base.api.vo.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * controller 日志拦截
 * 
 * @author 薛超
 * @since 2020年11月3日
 * @version 1.0.0
 */
@Component
@Aspect
@Slf4j
public class ControllerLogProxy implements BaseProxy {

    private final List<Class<? extends Annotation>> mappingList = CollUtil.toList(DeleteMapping.class, //
            PutMapping.class, //
            RequestMapping.class, //
            GetMapping.class, //
            PostMapping.class);

    /**
     * 环绕拦截 org.jeecg.modules.system.controller<br>
     * org.jeecg.modules.aisino.newtaxcontrol.controller<br>
     */
    @Override
    @Around(value = "execution(* org.mars..*.controller.*Controller.*(..))")
    public Object around(ProceedingJoinPoint pdj) throws Throwable {
        Object obj = null;
        HttpServletRequest request = getRequest();
        if (request == null) {
            // 非http请求
            return pdj.proceed();
        }
        //
        Method method = getMethod(pdj);
        Class<? extends Annotation> mappingClass = CollUtil.findOne(mappingList,
                m -> AnnotationUtil.hasAnnotation(method, m));
        if (mappingClass == null) {
            return pdj.proceed();
        }
        //
        long before = System.nanoTime();
        try {
            Object[] objects = pdj.getArgs();
            // 参数
            List<Object> list = Stream.of(objects).filter(o -> !isInclude(o)).collect(Collectors.toList());
            // json格式化
            String params = JSONUtil.toJsonStr(list);
            log.info("【请求路径】[{}]:[{}]", request.getMethod(), request.getRequestURI());// 请求路径
            log.info("【请求参数】{}", params);
            // 执行方法
            obj = pdj.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            // 返回值
            if (obj instanceof Result) {
                log.info("【返回值】{}", JSONUtil.toJsonStr(obj));
            }
            long after = System.nanoTime();
            // 计算方法执行时间
            log.info("【执行时间】[{}s]", (after - before) / 1000000000.0);
        }
        return obj;
    }

}