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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.DistrictVO;
import com.ops.jpa.entities.Area;
import com.ops.jpa.entities.Cluster;
import com.ops.web.service.AreaService;
import com.ops.web.service.ClusterService;
import com.ops.web.service.DistrictService;

/**
 * The Class UserController.
 *
 */
@RequestMapping(value = "/district")
@Controller
public class DistrictController {

	private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);


	@Autowired
	private DistrictService districtService;

	
	@Autowired
	private AreaService areaService;

	@Autowired
	private ClusterService clusterService;


	@RequestMapping(value = "/api/country/{countryId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> listAllDistricts(@PathVariable(value="countryId") final Long countryId) {
		logger.info("Inside DistrictController .. listAllDistricts");
		List<DistrictVO> districtList = null;
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
			try {
				districtList = districtService.findDistrictByCountry(countryId);
				if(!districtList.isEmpty()){				
					response.setStatusCode(200);
					response.setObject(districtList);
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
				}else{
					response.setStatusCode(404);
					response.setMessage("No district found");
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				response.setStatusCode(505);
				response.setMessage("Exception in getting response");
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
				logger.info("Exception in getting response", e);

			}

		logger.info("Exit DistrictController .. listAllDistricts");
		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/area/{districtId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> listAllAreas(@PathVariable(value="districtId") final Long districtId) {
		logger.info("Inside DistrictController .. listAllAreas");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			List<Area> areas = areaService.findAllAreas(districtId);
			if(!areas.isEmpty()){				
				response.setStatusCode(200);
				response.setObject(areas);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			}else{
				response.setStatusCode(404);
				response.setMessage("No Areas found");
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit DistrictController .. listAllAreas");
		return responseEntity;
	}
	
	@RequestMapping(value = "/v1/cluster/{districtId}/{areaId}", method = RequestMethod.GET,produces="application/json")
	public ResponseEntity<RestResponse> listAllCluster(@PathVariable(value="districtId") final Long districtId,
			@PathVariable(value="areaId") final Long areaId) {
		logger.info("Inside DistrictController .. listAllCluster");
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			List<Cluster> clusters = clusterService.getAllClustersBy(districtId,areaId);
			if(!clusters.isEmpty()){				
				response.setStatusCode(200);
				response.setObject(clusters);
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
			}else{
				response.setStatusCode(404);
				response.setMessage("No Clusters found");
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit DistrictController .. listAllCluster");
		return responseEntity;
	}
}
