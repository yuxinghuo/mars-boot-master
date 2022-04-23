package org.mars.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author qinfeng
 * @Date 2020/2/19 12:01
 * @Description:
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysCategoryModel {
    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 父级节点
     */
    private java.lang.String pid;
    /**
     * 类型名称
     */
    private java.lang.String name;
    /**
     * 类型编码
     */
    private java.lang.String code;

}
