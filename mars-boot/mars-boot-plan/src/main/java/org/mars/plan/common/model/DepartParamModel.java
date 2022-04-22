package org.mars.plan.common.model;

import lombok.Data;

/**
 * 部门参数 model
 *
 * @author 薛超
 * @version V1.0
 * @since 2020年3月27日
 */
@Data
public class DepartParamModel {

    private String id;
    /**
     * *********下列字段将自动补充**********
     */
    /**
     * 上级部门id
     */
    private String parentId;
    /**
     * 部门名称
     */
    private String departName;
    /**
     * 部门编码
     */
    private String orgCode;
    /**
     * 部门层级
     */
    private String orgType;
    /**
     * 开票网点编码
     */
    private String kpwdbm;
    /**
     * 截取有效位后的开票网点编码
     */
    private String subKpwdbm;

}
