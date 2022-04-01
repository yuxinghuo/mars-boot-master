package com.mars.system.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.mars.system.domain.dto.RoleDTO;
import com.mars.system.entity.SysRole;

import java.util.List;

/**
 * @功能：
 * @author： 超君子
 * @时间：2019-05-28
 * @version 1.0.0
 */
public interface SysRoleService extends IService<SysRole> {

	RoleDTO get(Integer roleId);

	List<RoleDTO> list(String roles);

	boolean save(RoleDTO role);

	boolean update(RoleDTO role);

	boolean remove(Integer roleId);

	boolean batchRemove(Integer[] roleIds);

}
