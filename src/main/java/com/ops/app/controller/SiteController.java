/*
  * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.CreateSiteVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.SiteContactVO;
import com.ops.app.vo.SiteDeliveryVO;
import com.ops.app.vo.SiteInfoVO;
import com.ops.app.vo.SiteLicenceVO;
import com.ops.app.vo.SiteOperationVO;
import com.ops.app.vo.SiteSubmeterVO;
import com.ops.app.vo.UserVO;
import com.ops.web.service.SiteService;
import com.ops.web.service.UserService;

/**
 * The Class UserController.
 *
 */
@RequestMapping(value = "/site")
@Controller
public class SiteController {

	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private SiteService siteService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/v1/update", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> createNewSite(@RequestBody final CreateSiteVO createSiteVO) {
		logger.info("Inside SiteController .. createNewSite");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			logger.info("CreateSiteVO : " + createSiteVO);
			if (createSiteVO.getSiteId() != null) {
				UserVO user = userService.findUserByUsername(createSiteVO.getCreatedBy());
				if (user.getUserId() != null) {
					LoginUser authorizedUser = new LoginUser();
					authorizedUser.setEmail(user.getEmailId());
					authorizedUser.setFirstName(user.getFirstName());
					authorizedUser.setLastName(user.getLastName());
					authorizedUser.setUserId(user.getUserId());
					CreateSiteVO savedSiteVO = siteService.updateSite(createSiteVO, authorizedUser);
					response.setStatusCode(200);
					response.setObject(savedSiteVO);
					response.setMessage("Site updated successfully");
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
				} else {
					response.setStatusCode(401);
					response.setMessage("User is not authorized");
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.UNAUTHORIZED);
				}
			}

		} catch (Exception e) {
			logger.info("Exception occured while saving or updating site", e);
			response.setMessage("Exception occured while saving or updating site");
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}
		logger.info("Exit SiteController .. createNewSite");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/contact/update", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> updateSiteContact(@RequestBody final CreateSiteVO createSiteVO) {
		logger.info("Inside SiteController .. updateSiteContact");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			logger.info("CreateSiteVO : " + createSiteVO);
			if (createSiteVO.getSiteId() != null) {
				UserVO user = userService.findUserByUsername(createSiteVO.getCreatedBy());
				if (user.getUserId() != null) {
					LoginUser authorizedUser = new LoginUser();
					authorizedUser.setEmail(user.getEmailId());
					authorizedUser.setFirstName(user.getFirstName());
					authorizedUser.setLastName(user.getLastName());
					authorizedUser.setUserId(user.getUserId());
					CreateSiteVO savedSiteVO = siteService.updateSiteContact(createSiteVO, authorizedUser);
					response.setStatusCode(200);
					response.setObject(savedSiteVO);
					response.setMessage("Site Contact updated successfully");
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
				} else {
					response.setStatusCode(401);
					response.setMessage("User is not authorized");
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.UNAUTHORIZED);
				}
			}

		} catch (Exception e) {
			logger.info("Exception occured while saving or updating site", e);
			response.setMessage("Exception occured while saving or updating site");
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}
		logger.info("Exit SiteController .. updateSiteContact");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/selected/{siteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSelectedSite(@PathVariable(value = "siteId") Long siteId) {
		logger.info("Inside SiteController .. getSelectedSite");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);

		CreateSiteVO savedSiteVO = null;
		try {
			savedSiteVO = siteService.getSiteDetails(siteId);
			if (savedSiteVO.getSiteId() != null) {
				response.setStatusCode(200);
				response.setObject(savedSiteVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			} else {
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Exception while getting site details for " + siteId, e);
			response.setMessage("Exception while getting site details for " + siteId);
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSelectedSite");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSiteList(@RequestParam("email") String email) {
		logger.info("Inside SiteController .. getSiteList");
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
				List<SiteInfoVO> siteList = siteService.getSites(authorizedUser);
				if (!siteList.isEmpty()) {
					response.setStatusCode(200);
					response.setObject(siteList);
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
				} else {
					response.setStatusCode(404);
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
				}

			}
		} catch (Exception e) {
			logger.info("Exception while getting site list", e);
			response.setMessage("Exception while getting site list");
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSiteList");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/selected/contacts/{siteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSelectedSiteContact(@PathVariable(value = "siteId") Long siteId) {
		logger.info("Inside SiteController .. getSelectedSiteContact");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		SiteContactVO siteContactVO = null;
		try {
			siteContactVO = siteService.getSiteContacts(siteId);
			if (siteContactVO.getSiteId() != null) {
				response.setStatusCode(200);
				response.setObject(siteContactVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			} else {
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Exception while getting site details for " + siteId, e);
			response.setMessage("Exception while getting site details for " + siteId);
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSelectedSiteContact");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/selected/license/{siteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSelectedSiteLicense(@PathVariable(value = "siteId") Long siteId) {
		logger.info("Inside SiteController .. getSelectedSiteLicense");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		List<SiteLicenceVO> siteLicenseVO = null;
		try {
			siteLicenseVO = siteService.getSiteLicences(siteId);
			if (!siteLicenseVO.isEmpty()) {
				response.setStatusCode(200);
				response.setObject(siteLicenseVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			} else {
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Exception while getting site license details for " + siteId, e);
			response.setMessage("Exception while getting site license details for " + siteId);
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSelectedSiteLicense");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/license/update/{action}/{siteId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> addOrUpdateLicense(@PathVariable(value = "action") String action,
			@PathVariable(value = "siteId") Long siteId,
			@RequestBody SiteLicenceVO siteLicenseVO) {
		logger.info("Inside SiteController .. addOrUpdateLicense");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			logger.info("SiteLicenseVO : " + siteLicenseVO);
			SiteLicenceVO savedLicenseVO = siteService.updateSiteLicense(siteId, siteLicenseVO);
			if (savedLicenseVO.getLicenseId() != null) {
				response.setStatusCode(200);
				response.setObject(savedLicenseVO);
				response.setMessage("Site License updated successfully");
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.info("Exception occured while saving or updating site", e);
			response.setMessage("Exception occured while saving or updating site");
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}
		logger.info("Exit SiteController .. addOrUpdateLicense");
		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/license/delete/{action}/{licenseId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> deleteLicenceData(@PathVariable(value = "action") String action,
			@PathVariable(value = "licenseId") Long licenseId) {
		logger.info("Inside SiteController .. deleteLicenceData");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			 int isDeleted = siteService.deleteLicense(licenseId);
			if (isDeleted==1) {
				response.setStatusCode(200);
				response.setMessage("Site License deleted successfully");
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.info("Exception occured while saving or updating site", e);
			response.setMessage("Exception occured while saving or updating site");
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}
		logger.info("Exit SiteController .. deleteLicenceData");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/selected/operation/{siteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSelectedSiteOps(@PathVariable(value = "siteId") Long siteId) {
		logger.info("Inside SiteController .. getSelectedSiteOps");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		List<SiteOperationVO> siteOperationVO = null;
		List<SiteDeliveryVO> siteDeliveryVO = null;
		try {
			siteOperationVO = siteService.getSiteSalesOperations(siteId);
			siteDeliveryVO = siteService.getSiteDeliveryVO(siteId);
			if (!siteOperationVO.isEmpty()) {
				response.setStatusCode(200);
				response.setSiteSalesOperations(siteOperationVO);
			}
			if (!siteDeliveryVO.isEmpty()) {
				response.setStatusCode(200);
				response.setSiteDeliveryOperations(siteDeliveryVO);
			}

			if (response.getSiteSalesOperations() != null || response.getSiteDeliveryOperations() != null) {
				response.setStatusCode(200);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			} else {
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Exception while getting site  Operation details for " + siteId, e);
			response.setMessage("Exception while getting site  Operation details for " + siteId);
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSelectedSiteOps");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/selected/deliveryoperation/{siteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSelectedSiteDeliveryOps(@PathVariable(value = "siteId") Long siteId) {
		logger.info("Inside SiteController .. getSelectedSiteDeliveryOps");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		List<SiteDeliveryVO> siteOperationVO = null;
		try {
			siteOperationVO = siteService.getSiteDeliveryVO(siteId);
			if (!siteOperationVO.isEmpty()) {
				response.setStatusCode(200);
				response.setObject(siteOperationVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			} else {
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Exception while getting site delivery Operation details for " + siteId, e);
			response.setMessage("Exception while getting site delivery Operation details for " + siteId);
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSelectedSiteDeliveryOps");
		return responseEntity;
	}

	@RequestMapping(value = "/v1/selected/submeter/{siteId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> getSelectedSiteSubmeter(@PathVariable(value = "siteId") Long siteId) {
		logger.info("Inside SiteController .. getSelectedSiteSubmeter");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		List<SiteSubmeterVO> siteSubmeterVO = null;
		try {
			siteSubmeterVO = siteService.getSiteSubmeterVO(siteId);
			if (!siteSubmeterVO.isEmpty()) {
				response.setStatusCode(200);
				response.setObject(siteSubmeterVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			} else {
				response.setStatusCode(404);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.info("Exception while getting site delivery Operation details for " + siteId, e);
			response.setMessage("Exception while getting site delivery Operation details for " + siteId);
			response.setStatusCode(500);
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);

		}

		logger.info("Exit SiteController .. getSelectedSiteSubmeter");
		return responseEntity;
	}

	/*
	 * @RequestMapping(value = "/selected/file/{keyname}", method =
	 * RequestMethod.POST, produces = "application/json") public
	 * ResponseEntity<RestResponse>
	 * getSelectedSiteFile(@RequestParam(value="keyname") String keyname, final
	 * HttpSession session) { logger.info(
	 * "Inside SiteController .. getSelectedSiteFile"); RestResponse response =
	 * new RestResponse(); ResponseEntity<RestResponse> responseEntity = new
	 * ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT); LoginUser loginUser
	 * = getCurrentLoggedinUser(session); if(loginUser!=null){ try { String
	 * image= siteService.getSiteFiles("site/testupload_1511726509651.png");
	 * if(StringUtils.isNotBlank(image)){ response.setStatusCode(200);
	 * response.setObject(image); responseEntity = new
	 * ResponseEntity<RestResponse>(response,HttpStatus.OK); }else{
	 * response.setStatusCode(404); responseEntity = new
	 * ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND); }
	 * 
	 * } catch (Exception e) { logger.info("Exception while getting site image",
	 * e); response.setStatusCode(500); responseEntity = new
	 * ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND); } }
	 * 
	 * logger.info("Exit SiteController .. getSelectedSiteFile"); return
	 * responseEntity; }
	 */

}
