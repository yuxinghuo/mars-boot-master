package com.mars.system.service;

import com.mars.system.domain.dto.UserDTO;
import com.mars.system.shiro.ShiroUserOnline;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface SessionService {

    List<ShiroUserOnline> list();

    List<UserDTO> listOnlineUser();

    Collection<Session> sessionList();

    boolean forceLogout(String sessionId);
}
