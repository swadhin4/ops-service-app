/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.ServiceProviderVO;
import com.ops.app.vo.UserVO;
import com.ops.web.service.ServiceProviderService;
import com.ops.web.service.UserService;

/**
 * The Class ServiceProviderController.
 *
 */
@RequestMapping(value = "/serviceprovider")
@Controller
public class ServiceProviderController  {

	private static final Logger logger = LoggerFactory.getLogger(ServiceProviderController.class);


	@Autowired
	private ServiceProviderService serviceProviderService;
	
	@Autowired
	private UserService userService;


	@RequestMapping(value = "/v1/list", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> listAllServiceProvider(@RequestParam("email") String email) {
		logger.info("Inside ServiceProviderController .. listAllServiceProvider");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
		UserVO user = userService.findUserByUsername(email);
		if (user.getUserId() != null) {
			LoginUser authorizedUser = new LoginUser();
			authorizedUser.setEmail(user.getEmailId());
			authorizedUser.setFirstName(user.getFirstName());
			authorizedUser.setLastName(user.getLastName());
			authorizedUser.setUserId(user.getUserId());
			authorizedUser.setCompany(user.getCompany());
			List<ServiceProviderVO> serviceProviderVOs = serviceProviderService.findAllServiceProvider(authorizedUser);
				if (serviceProviderVOs.isEmpty()) {
					response.setStatusCode(404);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
				}else{
					Collections.sort(serviceProviderVOs, ServiceProviderVO.COMPARE_BY_SPNAME);
					response.setStatusCode(200);
					response.setObject(serviceProviderVOs);
					responseEntity = new  ResponseEntity<RestResponse>(response, HttpStatus.OK);
				}
		}
			} catch (Exception e) {
				logger.info("Exception in getting service provider list", e);
				response.setMessage("Exception while getting service provider list");
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			}
		logger.info("Inside ServiceProviderController .. listAllServiceProvider");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/detail/{spId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> serviceProviderView(@PathVariable(value="spId") Long spId) {
		logger.info("Inside ServiceProviderController .. serviceProviderView");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
			try {
				ServiceProviderVO serviceProviderVO= serviceProviderService.findServiceProvider(spId);
				if (serviceProviderVO.getServiceProviderId()==null) {
					response.setStatusCode(404);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
				}else{
					response.setStatusCode(200);
					response.setObject(serviceProviderVO);
					responseEntity = new  ResponseEntity<RestResponse>(response, HttpStatus.OK);
				}
			} catch (Exception e) {
				logger.info("Exception in getting service provider view", e);
				response.setMessage("Exception while getting service provider view");
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
			}
		logger.info("Inside ServiceProviderController .. serviceProviderView");
		return responseEntity;
	}
	
	
}
