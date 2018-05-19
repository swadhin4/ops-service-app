package com.ops.app.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.UserVO;
import com.ops.jpa.entities.UserSiteAccess;
import com.ops.web.service.UserService;
import com.ops.web.service.UserSiteAccessService;

@Controller
@RequestMapping("/secure")
public class SecureController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecureController.class);
	@Autowired
	private UserService userService;
	

	@Autowired
	private UserSiteAccessService userSiteAccessService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String sayHello() {
        return "Secure Hello!";
    }
	
    @RequestMapping(value = "/v1/user", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<RestResponse>  getUserDetails(@RequestParam("email") String email ) {
    	LOGGER.info("Inside SecureController -- Get User Details By Email :");
    	RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		if(StringUtils.isNotBlank(email)){
			try {
				UserVO user = userService.findUserByUsername(email);
				if(user.getUserId()!=null && user.getSystemPassword().equalsIgnoreCase("NO")){
					response.setStatusCode(200);
					response.setObject(user);
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
				}else{
					response.setStatusCode(401);
					response.setMessage("Your current password is a system generated password. Please change your password by logging into web version of OPS365.");
					responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NOT_IMPLEMENTED);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatusCode(500);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.EXPECTATION_FAILED);
			}
		}
		
		LOGGER.info("Exit SecureController -- Get User Details By Email :");
		return responseEntity;
    }
    
    @RequestMapping(value = "/user/site/access", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse>  getUserSiteAccess(@RequestParam("email") String email) {
    	LOGGER.info("Inside UserController - getUserSiteAccess" );
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		if(StringUtils.isNotBlank(email)){
			try {
				UserVO user = userService.findUserByUsername(email);
				if(user.getUserId()!=null){
					List<UserSiteAccess> userSiteAccessList = userSiteAccessService.getUserSiteAccess(user.getUserId());
					if(!userSiteAccessList.isEmpty()){
						Collections.sort(userSiteAccessList,UserSiteAccess.COMPARE_BY_SITENAME);
					}
					response.setStatusCode(200);
					response.setObject(userSiteAccessList);
					responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.OK);
				}
			} catch (Exception e) {
				response.setStatusCode(500);
				response.setMessage("Exception while getting user site access list");
				responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
				e.printStackTrace();
			}

		} else{
			response.setStatusCode(404);
			response.setMessage("No user available ");
			responseEntity = new ResponseEntity<RestResponse>(response, HttpStatus.NOT_FOUND);
		}
		LOGGER.info("Exit UserController - getUserSiteAccess" );
		return responseEntity;
	}
}
