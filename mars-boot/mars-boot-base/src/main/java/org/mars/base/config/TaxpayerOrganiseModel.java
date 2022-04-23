package org.mars.base.config;

import lombok.Data;

/**
 * 机构税号
 * 
 * @author 薛超
 * @since 2021年3月8日
 * @version 1.0.0
 */
@Data
public class TaxpayerOrganiseModel {
    /**
     * 组织机构代码
     */
    private String organiseId;
    /**
     * 发票类型代码 0:普通发票；1：专用发票
     */
    private String invoicetypeCode;
    /**
     * 纳税人识别号
     */
    private String taxpayerId;
}
