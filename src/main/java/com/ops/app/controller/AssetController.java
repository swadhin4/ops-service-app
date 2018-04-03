/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

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
import com.ops.app.vo.AssetVO;
import com.ops.jpa.entities.AssetCategory;
import com.ops.jpa.entities.AssetLocation;
import com.ops.web.service.ApplicationService;
import com.ops.web.service.AssetService;
import com.ops.web.service.EmailService;
import com.ops.web.service.UserService;

/**
 * The Class UserController.
 *
 */
@RequestMapping(value = "/asset")
@Controller
public class AssetController {

	private static final Logger logger = LoggerFactory.getLogger(AssetController.class);

	/** The user service. */
	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private EmailService emailService;


	@Autowired
	private AssetService assetService;

	

	@RequestMapping(value = "/list", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<AssetVO>> listAllAssets(@RequestParam("email") String email) {
		List<AssetVO> assets = null;
		try {
				assets = assetService.findAllAsset(null);
				if (assets.isEmpty()) {
					return new ResponseEntity(HttpStatus.NO_CONTENT);
					// You many decide to return HttpStatus.NOT_FOUND
				}
		} catch (Exception e) {
			logger.info("Exception in getting asset list response", e);
		}

		return new ResponseEntity<List<AssetVO>>(assets, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/site/list/{siteId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<AssetVO>> listAllAssetsBySite( @PathVariable (value="siteId") Long siteId) {
		logger.info("Inside AssetController..listAllAssetsBySite");
		List<AssetVO> assets = null;
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
				assets = assetService.findAssetsBySite(siteId);
				if (assets.isEmpty()) {
					logger.info("No assets retrieved for site" +siteId); 
					return new ResponseEntity(HttpStatus.NOT_FOUND);
					// You many decide to return HttpStatus.NOT_FOUND
				}
		} catch (Exception e) {
			logger.info("Exception in getting asset list response", e);
		}
		logger.info("Exit  AssetController..listAllAssetsBySite");
		return new ResponseEntity<List<AssetVO>>(assets, HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RestResponse> createNewAsset(final Locale locale, final ModelMap model,
			@RequestBody final AssetVO assetVO) {
		logger.info("Inside AssetController .. createNewAsset");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
			try {
				logger.info("AssetVO : "+ assetVO);
				response = assetService.saveOrUpdateAsset(assetVO, null);
				if(response.getStatusCode()==200){
					if(response.getMode().equals("SAVING")){
						response.setStatusCode(200);
						response.setMessage("Asset created successfully");
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
					}
					else if(response.getMode().equals("UPDATING")){
						response.setStatusCode(200);
						response.setMessage("Asset updated successfully");
						responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
						}
				}else{
					response.setStatusCode(204);
					response.setMessage("Asset code already exists for selected site");
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				logger.info("Exception in getting response", e);
				response.setMessage("Exception while creating an asset");
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_FOUND);

			}

		logger.info("Exit AssetController .. createNewAsset");
		return responseEntity;
	}

	@RequestMapping(value = "/delete/{assetId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> deleteAsset(@PathVariable(value="assetId") Long assetId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NO_CONTENT);
		try {
			AssetVO assetVO = assetService.deleteAsset(assetId);
			if (assetVO.getAssetId()!=null) {
				response.setStatusCode(200);
				response.setObject(assetVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<AssetCategory>> listAllAssetCategories() {
		List<AssetCategory> assetCategories = null;
		try {
			assetCategories = assetService.getAllAssetCategories();
			if (assetCategories.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<AssetCategory>>(assetCategories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/info/{assetId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> getAssetInfo(@PathVariable(value="assetId") Long assetId) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NO_CONTENT);
		try {
			AssetVO assetVO = assetService.findAssetById(assetId);
			if (assetVO.getAssetId()!=null) {
				response.setStatusCode(200);
				response.setObject(assetVO);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@RequestMapping(value = "/locations", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<AssetLocation>> listAllAssetLocations() {
		List<AssetLocation> assetLocations= null;
		try {
			assetLocations = assetService.getAllAssetLocations();
			if (assetLocations.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<AssetLocation>>(assetLocations, HttpStatus.OK);
	}
	
}
