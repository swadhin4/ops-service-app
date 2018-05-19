package com.ops.app.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ops.app.util.RestResponse;
import com.ops.app.vo.AccessTokenVO;

@Controller
@RequestMapping("/basic/token")
public class AppAuthenticationController {

	public static final String OAUTH_TOKEN_URL="http://localhost:9191/ops/api/oauth/token?grant_type=password";
	
	 @Value("${server.hosturi}")
	private String hostURI;
	
	 @Value("${authentication.oauth.token.url}")
	private String oauthURI;
	
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<RestResponse> getAuthenticationToken(@RequestParam(value="username") String username, 
    		@RequestParam(value="password") String password) {
    	RestResponse responseObj = new RestResponse();
    	ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
    	try {
            HttpHeaders headers = createHttpHeaders("ops365","opssecret");
           // responseObj.setObject(headers.getFirst("Authorization"));
           
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(hostURI+oauthURI+"&username="+username+"&password="+password);
            request.addHeader("Authorization", headers.getFirst("Authorization"));
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");
            HttpResponse responseData = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(responseData.getEntity().getContent()));
        	StringBuffer result = new StringBuffer();
        	String line = "";
        	while ((line = rd.readLine()) != null) {
        		result.append(line);
        	}
        	ObjectMapper mapper = new ObjectMapper();
        	AccessTokenVO accessTokenVO =  mapper.readValue(result.toString(), AccessTokenVO.class);
        	if(accessTokenVO.getAccessToken()!=null){
        		 responseObj.setLoggedInUserMail(username);
                 responseObj.setStatusCode(200);
                 responseObj.setObject(accessTokenVO);
                 responseEntity = new ResponseEntity<RestResponse>(responseObj, HttpStatus.OK);
        	}else{
        		 responseObj.setStatusCode(401);
        		 responseEntity = new ResponseEntity<RestResponse>(responseObj, HttpStatus.UNAUTHORIZED);
        	}
           
           }
        catch (Exception eek) {
           eek.printStackTrace();
            responseObj.setStatusCode(500);
            responseEntity = new ResponseEntity<RestResponse>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		return responseEntity;
    }

    private HttpHeaders createHttpHeaders(String user, String password)
    {
        String notEncoded = user + ":" + password;
        String encodedAuth = Base64.encodeBase64String(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + encodedAuth);
        headers.add("Accept","application/json");
        return headers;
    }
}
