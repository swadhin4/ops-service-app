package com.ops.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.SPLoginVO;
import com.ops.app.vo.UserVO;
import com.ops.jpa.entities.RoleStatus;
import com.ops.web.service.RolePermissionService;
import com.ops.web.service.UserService;

@Controller
@RequestMapping(value = "/role")
public class RoleController  {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RolePermissionService rolePersmissionService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/manage")
	public String getRoles(){
		return "role.entry";
	}

	
	@RequestMapping(value = "/getstatusroleids", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getRoleStatusMapping(@RequestParam("email") String email){
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
		//Authentication springAuthentication = SecurityContextHolder.getContext().getAuthentication();
		//AuthorizedUserDetails authUser2 = (AuthorizedUserDetails)springAuthentication.getPrincipal();
		List<RoleStatus> getRoleStatus = new ArrayList<RoleStatus>();
		UserVO user = userService.findUserByUsername(email);
		if(user.getUserId()!=null){
			LoginUser authorizedUser = new LoginUser();
			authorizedUser.setEmail(user.getEmailId());
			authorizedUser.setFirstName(user.getFirstName());
			authorizedUser.setLastName(user.getLastName());
			authorizedUser.setUserId(user.getUserId());
		if(authorizedUser!=null){
			logger.info("Getting role status for Loggedin User : "+ authorizedUser.getUsername());
			getRoleStatus = rolePersmissionService.getRoleStatus(authorizedUser.getUserRoles().get(0));
		}/*else if(!StringUtils.isEmpty(spLoginVO.getSpUsername()) && spLoginVO.isValidated()){
			logger.info("Getting role status for service provider :"+ spLoginVO.getSpUsername());
			getRoleStatus = rolePersmissionService.getRoleStatusByRoleId(spLoginVO.getRoleId());
		}*/
		List<Long> StatusIDs= new ArrayList<Long>();
	//	getRoleStatus.stream().peek(e -> StatusIDs.add(e.getRoleId()));
		if(!getRoleStatus.isEmpty()){
			
			for(RoleStatus roleStatus : getRoleStatus){
				StatusIDs.add(roleStatus.getStatusId());
			}
			logger.info("Status IDs: "+ StatusIDs);
		}
		
		response.setObject(StatusIDs);
		response.setStatusCode(200); 
		responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
		}}catch(Exception e) {
			e.printStackTrace();
		response.setStatusCode(500);
		response.setMessage("Error Occured While Fetching Status Roles");
		responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

}
