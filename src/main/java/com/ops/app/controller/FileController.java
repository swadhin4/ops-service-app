/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
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
import com.ops.app.vo.CreateSiteVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.UploadFile;
import com.ops.app.vo.UserVO;
import com.ops.jpa.entities.User;
import com.ops.web.service.FileIntegrationService;
import com.ops.web.service.SiteService;
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
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private SiteService siteService;
	
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
				responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		LOGGER.info("Exit FileController .. deleteFileAttached");
		return responseEntity;
		}

	
	
	@RequestMapping(value = "/ticket/upload/{ticketid}/{ticketnumber}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> uploadNewAttachments(@PathVariable(value="ticketid") Long ticketId,
			@PathVariable(value="ticketnumber") String ticketNumber,
			@RequestBody List<UploadFile> incidentImageList, @RequestParam("email") String email) {
		LOGGER.info("Inside FileController .. uploadNewAttachments");
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

		LOGGER.info("Exit FileController .. uploadNewAttachments");
		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/selected/file/download/{type}/{fileId}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<RestResponse> getSelectedIncidentImage(@PathVariable(value="type") String type,
			@PathVariable(value="fileId") Long fileId,
			@RequestParam("email") String email) {
		LOGGER.info("Inside FileController .. getSelectedIncidentImage");
		RestResponse response=new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
			try {
				if(fileId!=null){
				User user = userService.findByEmail(email);
				if(user!=null){
					LoginUser loginUser = new LoginUser();
					loginUser.setUserId(user.getUserId());
					loginUser.setUsername(user.getEmailId());
					loginUser.setCompany(user.getCompany());
					String keyName = "";
					if(!StringUtils.isEmpty(type)){
					
						if(type.equalsIgnoreCase("SITE")){
							keyName = siteService.getSiteAttachment(fileId);
						}else if(type.equalsIgnoreCase("INCIDENT")){
							keyName = ticketService.getTicketAttachmentKey(fileId);
						}
					}
					if(!StringUtils.isEmpty(keyName)){
						RestResponse responseData = fileIntegrationService.getFileLocation(loginUser.getCompany(),keyName);
						if(responseData!=null && responseData.getStatusCode()==200){
							LOGGER.info("Converting To base64 string from file :"+ responseData.getMessage());
							File file = new File(responseData.getMessage());
							String base64String = encodeFileToBase64Binary(file);
							if(!StringUtils.isEmpty(base64String)){
								response.setStatusCode(200);
								response.setMessage(base64String);
								response.setFileType(responseData.getFileType());
								responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
							}else{
								response.setStatusCode(404);
								response.setMessage("No Image file");
								responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
							}
						}else{
							LOGGER.info("Unable to download file");
						 }
					}else{
						response.setStatusCode(404);
						response.setMessage("No Image file");
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
					}
				  }
				}
			} catch (Exception e) {
				LOGGER.info("Exception while getting file image", e);
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);

			}

			LOGGER.info("Exit FileController .. getSelectedSiteFile");
		return responseEntity;
	}
	
	@RequestMapping(value = "/site/upload/attachment", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> uploadNewAttachments(@RequestBody List<UploadFile> siteImage, @RequestParam("email") String email) {
		LOGGER.info("Inside FileController .. uploadNewAttachments");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		User user = userService.findByEmail(email);
		if (user != null) {
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(user.getUserId());
			loginUser.setUsername(user.getEmailId());
			loginUser.setCompany(user.getCompany());
			try {
				CreateSiteVO siteVO = siteService.uploadSiteImage(loginUser.getCompany(), siteImage.get(0));
				if(!StringUtils.isBlank(siteVO.getFileLocation())){
					response.setStatusCode(200);
					boolean isUpdated = siteService.updateSiteAttachmentFile(siteVO.getFileLocation(),siteImage.get(0).getSiteId());
					if(isUpdated){
						
						response.setMessage("Image uploaded successfully");
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
						LOGGER.info("Image uploaded successfully");
					}
					else{
						
						response.setMessage("Unable to update image location in DB.");
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
						LOGGER.info("Image uploaded successfully");
					}
				}
				
			 }catch (Exception e) {
				 LOGGER.info("Exception while uploaded image.", e);
				 response.setStatusCode(500);
				 responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			 }
		}

		LOGGER.info("Exit FileController .. uploadNewAttachments");
		return responseEntity;
	}

	
	 private static String encodeFileToBase64Binary(File downloadedFile){
         String encodedfile = "";
         try {
        	// Reading a Image file from file system
             FileInputStream imageInFile = new FileInputStream(downloadedFile);
             byte imageData[] = new byte[(int) downloadedFile.length()];
             imageInFile.read(imageData);
             encodedfile = Base64.encodeBase64String(imageData);

             imageInFile.close();
         } catch (FileNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
         return encodedfile;
     }
}
