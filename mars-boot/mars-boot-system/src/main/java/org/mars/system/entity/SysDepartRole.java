package org.mars.system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mars.base.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 部门角色
 * @Author: jeecg-boot
 * @Date: 2020-02-12
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDepartRole {

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 部门id
     */
    @Excel(name = "部门id", width = 15)
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    private java.lang.String departId;
    /**
     * 部门角色名称
     */
    @Excel(name = "部门角色名称", width = 15)
    private java.lang.String roleName;
    /**
     * 部门角色编码
     */
    @Excel(name = "部门角色编码", width = 15)
    private java.lang.String roleCode;
    /**
     * 描述
     */
    @Excel(name = "描述", width = 15)
    private java.lang.String description;
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15)
    private java.lang.String createBy;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @Excel(name = "更新人", width = 15)
    private java.lang.String updateBy;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;


}
