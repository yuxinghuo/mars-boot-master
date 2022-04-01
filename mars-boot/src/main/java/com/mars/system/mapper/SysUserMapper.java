package com.mars.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mars.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

}
