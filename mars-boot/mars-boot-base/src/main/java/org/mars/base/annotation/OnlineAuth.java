package org.mars.base.annotation;

import java.lang.annotation.*;

/**
 * @Author: Mars
 * @Description: online请求拦截专用注解
 * @Date: 2022年4月23日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface OnlineAuth {

    /**
     * @Description 请求关键字，在xxx/code之前的字符串
     * @return
     */
    String value();
}
