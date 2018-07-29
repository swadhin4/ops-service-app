package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.AppFeature;
import com.ops.jpa.entities.RolePermission;
import com.ops.jpa.entities.RoleStatus;
import com.ops.jpa.entities.UserRole;
import com.ops.jpa.repository.RolePermissionRepo;
import com.ops.web.service.RolePermissionService;

@Service("rolePersmissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

	@Autowired
	private RolePermissionRepo rolePermissionRepo;

	@Override
	@Transactional
	public List<AppFeature> getUserFeatureAccess(final UserRole loggedInUserRole) throws Exception {
		List<AppFeature> appAccessList = new ArrayList<AppFeature>();
		List<RolePermission> rolePermissions = rolePermissionRepo.findPermissionsByRole(loggedInUserRole.getRole().getRoleId());
		if(!rolePermissions.isEmpty()){
			for(RolePermission rolePermission:rolePermissions) {
				AppFeature appFeature = rolePermission.getAppFeature();
				LOGGER.info("User has access : "+ rolePermission + " to feature : "+  appFeature.getFeatureName());
				appAccessList.add(appFeature);
			}
		}
		return appAccessList;
	}

	@Override
	public List<RoleStatus> getRoleStatus(final UserRole usr) {
		try {
			System.out.println("Role ID --- > "+usr.getRole().getRoleId());
			List<RoleStatus> roleStatus = rolePermissionRepo.getRoleStatus(usr.getRole().getRoleId());
			return roleStatus;
		} catch (Exception e) {
			LOGGER.error("Error Occured while fetching Role Status mapping");
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public List<RoleStatus> getRoleStatusByRoleId(final Long roleId) {
		try {
			System.out.println("Role ID --- > "+roleId);
			List<RoleStatus> roleStatus = rolePermissionRepo.getRoleStatus(roleId);
			return roleStatus;
		} catch (Exception e) {
			LOGGER.error("Error Occured while fetching Role Status mapping");
			e.printStackTrace();
			return null;
		}

	}

}
