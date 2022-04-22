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
 * @Description: 多数据源管理
 * @Author: jeecg-boot
 * @Date: 2019-12-25
 * @Version: V1.0
 */
@Data
@TableName("sys_data_source")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDataSource {

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 数据源编码
     */
    @Excel(name = "数据源编码", width = 15)
    private java.lang.String code;
    /**
     * 数据源名称
     */
    @Excel(name = "数据源名称", width = 15)
    private java.lang.String name;
    /**
     * 描述
     */
    @Excel(name = "备注", width = 15)
    private java.lang.String remark;
    /**
     * 数据库类型
     */
    @Dict(dicCode = "database_type")
    @Excel(name = "数据库类型", width = 15, dict = "database_type")
    private java.lang.String dbType;
    /**
     * 驱动类
     */
    @Excel(name = "驱动类", width = 15)
    private java.lang.String dbDriver;
    /**
     * 数据源地址
     */
    @Excel(name = "数据源地址", width = 15)
    private java.lang.String dbUrl;
    /**
     * 数据库名称
     */
    @Excel(name = "数据库名称", width = 15)
    private java.lang.String dbName;
    /**
     * 用户名
     */
    @Excel(name = "用户名", width = 15)
    private java.lang.String dbUsername;
    /**
     * 密码
     */
    @Excel(name = "密码", width = 15)
    private java.lang.String dbPassword;
    /**
     * 创建人
     */
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @Excel(name = "所属部门", width = 15)
    private java.lang.String sysOrgCode;
}
