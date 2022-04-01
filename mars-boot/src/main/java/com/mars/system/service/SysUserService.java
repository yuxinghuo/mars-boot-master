package com.mars.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mars.system.domain.dto.UserDTO;
import com.mars.system.domain.vo.UserVO;
import com.mars.system.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @功能：
 * @时间：2020-04-17
 * @version 1.0.0
 */
@Service
public interface SysUserService extends IService<SysUser> {

	UserDTO get(Integer userId);

	int save(UserDTO user);

	int update(UserDTO user);

	int remove(Integer userId);

	int removeBatch(Integer[] userIds);

	int resetPwd(UserVO userVO, UserDTO user);
}
