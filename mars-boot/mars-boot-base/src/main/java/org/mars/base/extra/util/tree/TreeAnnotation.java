package org.mars.base.extra.util.tree;

import java.lang.annotation.*;

/**
 * 树形数据解析
 * 
 * @author 薛超
 * @since 2020年2月19日
 * @version V1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TreeAnnotation {
	/**
	 * 类型
	 * 
	 * @return {@link TreeEnum}
	 */
	TreeEnum value();

}
