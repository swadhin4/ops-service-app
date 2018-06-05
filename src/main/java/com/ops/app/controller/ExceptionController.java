package com.ops.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ops.app.util.RestResponse;

@RestController
public class ExceptionController implements ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
	private static final String PATH = "/error";
    
    @RequestMapping(value = PATH)
    public String error() {
    	/*LOGGER.info("Pre-authenticated entry point called. Rejecting access," + HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    	RestResponse responseData=new RestResponse();
    	responseData.setStatusCode(HttpStatus.PROXY_AUTHENTICATION_REQUIRED.ordinal());
        responseData.setMessage("Authentication Failed. This is a protected resource. It requires more authentication");
        ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(responseData,HttpStatus.PROXY_AUTHENTICATION_REQUIRED);*/
		return "Error";
    }

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
