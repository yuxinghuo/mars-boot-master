package org.mars.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mars.system.entity.SysDepart;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * <p>
 *
 * @Author: Steve
 * @Since：   2019-01-22
 */
public interface SysDepartMapper extends BaseMapper<SysDepart> {

	/**
	 * 根据用户ID查询部门集合
	 */
	public List<SysDepart> queryUserDeparts(@Param("userId") String userId);

	/**
	 * 根据用户名查询部门
	 *
	 * @param username
	 * @return
	 */
	public List<SysDepart> queryDepartsByUsername(@Param("username") String username);

	@Select("select id from sys_depart where org_code=#{orgCode}")
	public String queryDepartIdByOrgCode(@Param("orgCode") String orgCode);

	@Select("select id,parent_id from sys_depart where id=#{departId}")
	public SysDepart getParentDepartId(@Param("departId") String departId);

	@Select("select d.id, d.parent_id parentId, d.province_code provinceCode, d.kpwdbm, d.depart_name departName, d.org_type orgType, d.org_code orgCode, d.org_category orgCategory from sys_depart d,sys_user_depart ud where d.id=ud.dep_id and ud.user_id=#{userId} LIMIT 1")
	public SysDepart getFirstSysDepartByUserId(@Param("userId") String userId);

	/**
	 *  根据部门Id查询,当前和下级所有部门IDS
	 * @param departId
	 * @return
	 */
	List<String> getSubDepIdsByDepId(@Param("departId") String departId);

	/**
	 * 根据部门编码获取部门下所有IDS
	 * @param orgCodes
	 * @return
	 */
	List<String> getSubDepIdsByOrgCodes(@Param("orgCodes") List<String> orgCodes);

	@Select("select * from sys_depart where org_code=#{orgCode}")
	List<SysDepart> getDeptByOrgCode(@Param("orgCode")String orgCode);

}
