/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.CustomerSPLinkedTicketVO;
import com.ops.app.vo.EscalationLevelVO;
import com.ops.app.vo.IncidentVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.ServiceProviderVO;
import com.ops.app.vo.TicketCommentVO;
import com.ops.app.vo.TicketEscalationVO;
import com.ops.app.vo.TicketHistoryVO;
import com.ops.app.vo.TicketMVO;
import com.ops.app.vo.TicketPrioritySLAVO;
import com.ops.app.vo.TicketVO;
import com.ops.app.vo.UserVO;
import com.ops.jpa.entities.CustomerSPLinkedTicket;
import com.ops.jpa.entities.SPEscalationLevels;
import com.ops.jpa.entities.Status;
import com.ops.jpa.entities.TicketAttachment;
import com.ops.jpa.entities.TicketCategory;
import com.ops.jpa.entities.User;
import com.ops.jpa.repository.SPEscalationLevelRepo;
import com.ops.jpa.repository.TicketAttachmentRepo;
import com.ops.web.service.EmailService;
import com.ops.web.service.FileIntegrationService;
import com.ops.web.service.ServiceProviderService;
import com.ops.web.service.StatusService;
import com.ops.web.service.TicketCategoryService;
import com.ops.web.service.TicketService;
import com.ops.web.service.UserService;

/**
 * The Class UserController.
 *
 */
@Controller
@RequestMapping("/file")
public class FileController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileIntegrationService fileIntegrationService;
	
	@Autowired
	private TicketAttachmentRepo ticketAttachmentRepo;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/attachment/delete/{feature}/{attachmentId}/{type}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<RestResponse> deleteFileAttached(@PathVariable(value="feature") String feature, 
			@PathVariable(value="attachmentId") Long attachmentId,@PathVariable(value="type") 
			String type, @RequestParam("email") String email) {
		LOGGER.info("Inside FileController .. deleteFileAttached");
		RestResponse responseData = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.NO_CONTENT);
		try {
		UserVO user = userService.findUserByUsername(email);
		if(user.getUserId()!=null){
			LoginUser loginUser = new LoginUser();
			loginUser.setEmail(user.getEmailId());
			loginUser.setFirstName(user.getFirstName());
			loginUser.setLastName(user.getLastName());
			loginUser.setUserId(user.getUserId());
				if(StringUtils.isNotEmpty(feature)){
					if(feature.equalsIgnoreCase("SITE")){
						responseData = fileIntegrationService.deleteFile(attachmentId, null,null,null,null);
						if(responseData.getStatusCode()==200){
							responseData.setStatusCode(200);
							responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.OK);
							}else{
								responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.EXPECTATION_FAILED);
							}
					}
					else if(feature.equalsIgnoreCase("LICENSE")){
						List<Long> licenseList = new ArrayList<Long>();
						licenseList.add(attachmentId);
						responseData = fileIntegrationService.deleteFile(null, licenseList,null,null,null);
						if(responseData.getStatusCode()==200){
							responseData.setStatusCode(200);
							responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.OK);
							}else{
								responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.EXPECTATION_FAILED);
							}
					}
					else if(feature.equalsIgnoreCase("ASSET")){
						if(StringUtils.isNotBlank(type)){
							responseData = fileIntegrationService.deleteFile(null, null,attachmentId,null,type);	
						}
						if(responseData.getStatusCode()==200){
							responseData.setStatusCode(200);
							responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.OK);
							}else{
								responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.EXPECTATION_FAILED);
							}
					}
					else if(feature.equalsIgnoreCase("INCIDENT")){
						List<Long> ticketAttachementIds = new ArrayList<Long>();
						ticketAttachementIds.add(attachmentId);
						responseData = fileIntegrationService.deleteFile(null, null,null,ticketAttachementIds,null);
						if(responseData.getStatusCode()==200){
						responseData.setStatusCode(200);
						responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.OK);
						}else{
							responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.EXPECTATION_FAILED);
						}
							
					}
					else{
						responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.NO_CONTENT);
					}
					
				}
		}
		}catch (Exception e) {
				LOGGER.info("Exception while getting site image", e);
				responseData.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.OK);
			}
		
		LOGGER.info("Exit FileController .. deleteFileAttached");
		return responseEntity;
		}

}
