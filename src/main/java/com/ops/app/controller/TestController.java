/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.UploadFile;
import com.ops.jpa.entities.User;
import com.ops.web.service.TicketService;
import com.ops.web.service.UserService;

/**
 * The Class TestController.
 *
 */
@Controller
@RequestMapping("/test")
public class TestController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(value = "/upload/{ticketid}/{ticketnumber}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> uploadNewAttachments(@PathVariable(value="ticketid") Long ticketId,
			@PathVariable(value="ticketnumber") String ticketNumber,
			@RequestBody List<UploadFile> incidentImageList, @RequestParam("email") String email) {
		LOGGER.info("Inside IncidentController .. uploadNewAttachments");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		User user = userService.findByEmail(email);
		if (user != null) {
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(user.getUserId());
			loginUser.setUsername(user.getEmailId());
			loginUser.setCompany(user.getCompany());
			try {
				boolean isUploaded = ticketService.uploadIncidentAttachments(ticketId,ticketNumber, loginUser, incidentImageList);
				if(isUploaded){
					response.setStatusCode(200);
					response.setMessage("Image uploaded successfully");
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
					LOGGER.info("Image uploaded successfully");
				}
				
			 }catch (Exception e) {
				 LOGGER.info("Exception while uploaded image.", e);
				 response.setStatusCode(500);
				 responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			 }
		}

		LOGGER.info("Exit IncidentController .. uploadNewAttachments");
		return responseEntity;
	}

}
