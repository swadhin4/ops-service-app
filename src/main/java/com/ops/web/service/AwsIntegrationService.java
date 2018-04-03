package com.ops.web.service;

import java.io.File;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ops.app.util.RestResponse;

public interface AwsIntegrationService {

	public void uploadObject(PutObjectRequest fileObject, AmazonS3 s3client);
	
	public void createFolder(String bucketName, String folderName, AmazonS3 s3Client );
	
	public void deleteFolder(String bucketName, String folderName, AmazonS3 s3Client );
	
	public File downloadFile(String bucketName, String keyName);
	
	public RestResponse deleteFile(String bucketName, String keyName) throws Exception;
	
	public RestResponse deleteMultipleFile(List<KeyVersion> keys) throws Exception;
	
}
