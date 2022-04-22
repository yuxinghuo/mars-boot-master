package org.mars.base.extra.api;

import java.util.List;

import org.mars.base.extra.model.TaxpayerOrganiseModel;

import cn.hutool.core.collection.CollUtil;

/**
 * sdk 辅助api
 * 
 * @author 薛超
 * @since 2021年3月8日
 * @version 1.0.0
 */
public interface ExtraSdkAPI {

    /**
     * 根据组织机构代码-> 获取机构税号关系
     */
    default List<TaxpayerOrganiseModel> likeTaxpayerOrganiseList(String orgCode) {
        return CollUtil.toList();
    };

}
