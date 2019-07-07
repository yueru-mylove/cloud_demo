package com.voiceai.cloud.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年06月04日 09:23:00
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
