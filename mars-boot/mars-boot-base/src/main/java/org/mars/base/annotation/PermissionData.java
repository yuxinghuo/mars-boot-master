package org.mars.base.annotation;

import java.lang.annotation.*;

/**
 * @Author: Mars
 * @Description: 数据权限注解
 * @Date: 2022年4月23日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface PermissionData {
    /**
     * @Description 暂时没用
     */
    String value() default "";


    /**
     * @return String
     * @Description 配置菜单的组件路径, 用于数据权限
     */
    String pageComponent() default "";
}