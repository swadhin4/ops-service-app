package com.ops.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.LoginUser;
import com.ops.jpa.entities.Role;
import com.ops.jpa.entities.UserRole;
import com.ops.jpa.repository.RoleDAO;
import com.ops.web.service.ApplicationService;
import com.ops.web.service.UserService;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);
	private static final String[] USER_ROLE_ARRAY = new String[] { "ROLE_ADMIN" };

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDAO roleDAO;


	@Override
	@Transactional(readOnly = true)
	public RestResponse checkUserRole(final LoginUser user) {
		LOGGER.info("Inside ApplicationServiceImpl - checkUserRole()");
		String userRole="";
		String redirectUrl="";
		RestResponse response=new RestResponse();
		if (user == null) {
			return null;
		} else {
			List<UserRole> userRoles=user.getUserRoles();
			LOGGER.info("Checking for user role as ROLE_ADMIN.");
			LOGGER.info("User is having roles: ");

			UserRole loggedInUserRole =  user.getUserRoles().get(0);
			LOGGER.info(loggedInUserRole.getRole().getRoleName());

			switch(loggedInUserRole.getRole().getRoleId().intValue()){

			case 1 : response.setRedirectUrl("home"); break;
			case 2: response.setRedirectUrl("home"); break;
			case 3:response.setRedirectUrl("home"); break;
			case 4: response.setRedirectUrl("admin.home");break;
			case 5: response.setRedirectUrl("home");break;
			default:response.setRedirectUrl("redirect:/");

			}

			/*if(loggedInUserRole.getRole().getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
				userRole=loggedInUserRole.getRole().getRoleName();
				response.setRedirectUrl("admin.home");
				redirectUrl="admin.home";
			}
			else if(loggedInUserRole.getRole().getRoleName().equalsIgnoreCase("ROLE_SITE_STAFF")) {
				userRole=loggedInUserRole.getRole().getRoleName();
				response.setRedirectUrl("home");
				redirectUrl="home";
			}
			else{
				response.setRedirectUrl("redirect:/");
			}*/
		}
		LOGGER.info("Exit ApplicationServiceImpl - checkUserRole()");
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isSuperUser(final LoginUser user) {
		LOGGER.info("Inside ApplicationServiceImpl - isSuperUser()");
		boolean superUser=false;
		if (user == null) {
			return false;
		} else {
			List<UserRole> userRoles=user.getUserRoles();
			LOGGER.info("Checking for user role as ROLE_ADMIN.");
			LOGGER.info("User is having roles: ");
			for(UserRole role:userRoles){
				LOGGER.info(role.getRole().getRoleName());
				if(ArrayUtils.contains(USER_ROLE_ARRAY, role.getRole().getRoleName())){
					superUser=true;
					break;
				}
			}
			LOGGER.info("Exit ApplicationServiceImpl - isSuperUser()");
			return superUser;
		}
	}


	@Override
	public List<Role> findAllRoles(final LoginUser user) {
		List<Role> roleList = roleDAO.findAll();
		UserRole userRole = user.getUserRoles().get(0);
		List<Role> tempRoleList = new ArrayList<Role>();
		for(Role role:roleList){
			if(userRole.getRole().getRoleId().intValue() != role.getRoleId().intValue()){
				tempRoleList.add(role);
			}
		}
		return tempRoleList==null?Collections.EMPTY_LIST:tempRoleList;
	}

}
