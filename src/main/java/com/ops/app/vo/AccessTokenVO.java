package com.ops.app.vo;

import com.fasterxml.jackson.annotation.JsonSetter;

public class AccessTokenVO {

	
	private String accessToken;
	private String tokenType;
	private String refreshToken;
	private String expiresIn;
	private String scope;
	private String error;
	private String errorDescription;
	
	public String getAccessToken() {
		return accessToken;
	}
	@JsonSetter("access_token")
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	@JsonSetter("token_type")
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	@JsonSetter("refresh_token")
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	@JsonSetter("expires_in")
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getScope() {
		return scope;
	}
	@JsonSetter("scope")
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getError() {
		return error;
	}
	
	@JsonSetter("error")
	public void setError(String error) {
		this.error = error;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	@JsonSetter("error_description")
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
	
}
