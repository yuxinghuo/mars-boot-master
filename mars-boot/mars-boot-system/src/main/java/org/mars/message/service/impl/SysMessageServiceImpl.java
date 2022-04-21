package org.mars.message.service.impl;

import org.mars.base.system.base.service.impl.JeecgServiceImpl;
import org.mars.message.entity.SysMessage;
import org.mars.message.mapper.SysMessageMapper;
import org.mars.message.service.ISysMessageService;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Service
public class SysMessageServiceImpl extends JeecgServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

}
