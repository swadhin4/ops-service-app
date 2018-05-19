package com.ops.app.util;

import com.google.gson.Gson;

public class RestResponse  {

	private int statusCode;

	private String message;

	private Object object;

	private String redirectUrl;

	private String loggedInUserMail;
	
	private String mode;
	
	private int calculatedVal;
	
	private Object siteSalesOperations;
	
	private Object siteDeliveryOperations;
	
	private String fileType;




	public RestResponse() {
		super();
		// TODO Auto-generated constructor stub
	}



	public RestResponse(final int statusCode, final String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}



	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(final int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(final Object object) {
		this.object = object;
	}


	public String getLoggedInUserMail() {
		return loggedInUserMail;
	}


	public void setLoggedInUserMail(final String loggedInUserMail) {
		this.loggedInUserMail = loggedInUserMail;
	}



	public String getRedirectUrl() {
		return redirectUrl;
	}



	public void setRedirectUrl(final String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}



	public static String getJSON(final Object object) {
		Gson gson=new Gson();
		return gson.toJson(object);
	}



	public String getMode() {
		return mode;
	}



	public void setMode(String mode) {
		this.mode = mode;
	}



	public int getCalculatedVal() {
		return calculatedVal;
	}



	public void setCalculatedVal(int calculatedVal) {
		this.calculatedVal = calculatedVal;
	}



	public Object getSiteSalesOperations() {
		return siteSalesOperations;
	}



	public void setSiteSalesOperations(Object siteSalesOperations) {
		this.siteSalesOperations = siteSalesOperations;
	}



	public Object getSiteDeliveryOperations() {
		return siteDeliveryOperations;
	}



	public void setSiteDeliveryOperations(Object siteDeliveryOperations) {
		this.siteDeliveryOperations = siteDeliveryOperations;
	}



	public String getFileType() {
		return fileType;
	}



	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


}
