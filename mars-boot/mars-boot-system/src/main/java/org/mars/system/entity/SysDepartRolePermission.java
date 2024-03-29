package org.mars.system.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 部门角色权限
 * @Author: jeecg-boot
 * @Date: 2020-02-12
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_role_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDepartRolePermission {

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 部门id
     */
    @Excel(name = "部门id", width = 15)
    private java.lang.String departId;
    /**
     * 角色id
     */
    @Excel(name = "角色id", width = 15)
    private java.lang.String roleId;
    /**
     * 权限id
     */
    @Excel(name = "权限id", width = 15)
    private java.lang.String permissionId;
    /**
     * dataRuleIds
     */
    @Excel(name = "dataRuleIds", width = 15)
    private java.lang.String dataRuleIds;

    public SysDepartRolePermission() {
    }

    public SysDepartRolePermission(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
