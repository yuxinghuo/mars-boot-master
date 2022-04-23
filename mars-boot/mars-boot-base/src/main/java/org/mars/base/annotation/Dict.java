package org.mars.base.annotation;

import java.lang.annotation.*;

/**
 * @Author: Mars
 * @Description: 字典配置注解
 * @Date: 2022年4月23日
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {
    /**
     * @return String
     * @Description: 数据code
     */
    String dicCode();

    /**
     * @return String
     * @Description: 数据Text
     */
    String dicText() default "";

    /**
     * @return String
     * @Description: 数据字典表
     */
    String dictTable() default "";
}
