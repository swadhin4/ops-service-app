package com.ops.web.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.LoginUser;

public interface AuthorizationService {

	public RestResponse authorizeUserAccess(LoginUser user) throws Exception;

	public void  check(UserDetails user) ;
}
