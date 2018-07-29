package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.AppFeature;
import com.ops.jpa.entities.RoleStatus;
import com.ops.jpa.entities.UserRole;

public interface RolePermissionService {

	public List<AppFeature> getUserFeatureAccess(UserRole loggedInUserRole) throws Exception;

	public List<RoleStatus> getRoleStatus(UserRole usr);

	List<RoleStatus> getRoleStatusByRoleId(Long roleId);

}
