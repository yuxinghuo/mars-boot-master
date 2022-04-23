package org.mars.base.system.util;

import javax.servlet.http.HttpServletRequest;

import org.mars.base.system.query.QueryGenerator;
import org.mars.base.util.SpringContextUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;

/**
 * 查询条件
 * 
 * @author 薛超
 * @since 2020年4月29日
 * @version V1.0
 */
public class QueryWrapperUtil extends QueryGenerator {
	/**
	 * 封装QueryWrapper.initQueryWrapper
	 * 
	 * @param <T>
	 * @param entityClass 实体类.class
	 * @return
	 */
	public static <T> QueryWrapper<T> wrapper(Class<T> entityClass) {
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		return initQueryWrapper(ReflectUtil.newInstance(entityClass), request.getParameterMap());
	}

	/**
	 * 封装 QueryWrapper.initQueryWrapper
	 * 
	 * @param <T>
	 * @param entity 实体类
	 * @return
	 */
	public static <T> QueryWrapper<T> wrapper(T entity) {
		Assert.notNull(entity, "entity 不能为空");
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		return initQueryWrapper(entity, request.getParameterMap());
	}
}
