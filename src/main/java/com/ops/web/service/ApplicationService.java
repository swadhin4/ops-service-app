package com.ops.web.service;

import java.util.List;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.LoginUser;
import com.ops.jpa.entities.Role;

public interface ApplicationService {

	public RestResponse checkUserRole(LoginUser user);

	public List<Role> findAllRoles(LoginUser user);

	boolean isSuperUser(LoginUser user);
}
