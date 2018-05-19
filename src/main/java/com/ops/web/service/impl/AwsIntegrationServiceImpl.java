package com.ops.web.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ops.app.util.RestResponse;
import com.ops.web.service.AwsIntegrationService;

@Service("awsIntegrationService")
public class AwsIntegrationServiceImpl implements AwsIntegrationService{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AwsIntegrationServiceImpl.class);
	
	private static final String SUFFIX = "/";
	
	 @Autowired
	 private Environment environment;
	
	@Override
	public void uploadObject(PutObjectRequest fileObject, AmazonS3 s3Client) {
		s3Client.putObject(fileObject);
	}

	@Override
	public void createFolder(String bucketName, String folderName, AmazonS3 s3Client) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent, metadata);

		// send request to S3 to create folder
		s3Client.putObject(putObjectRequest);
	}

	@Override
	public void deleteFolder(String bucketName, String folderName, AmazonS3 s3Client) {
		List<S3ObjectSummary> fileList = s3Client.listObjects(bucketName, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			s3Client.deleteObject(bucketName, file.getKey());
		}
		s3Client.deleteObject(bucketName, folderName);
	}

	@Override
	public File downloadFile(String bucketName, String keyName) {
		/*AWSCredentials credentials = new BasicAWSCredentials("AKIAJZTA6BYNTESWQWBQ","YWzhoGSfC1ADDT+xHzvAsvf/wyMlSl71TexLLg8t");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		s3client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2));*/
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJZTA6BYNTESWQWBQ", "YWzhoGSfC1ADDT+xHzvAsvf/wyMlSl71TexLLg8t");
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
								.withRegion(Regions.US_WEST_2)
		                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		                        .build();
		
		
		byte[] readBuf = new byte[1024];
		String imageBytes ="";
		File file=null;
		try{
			S3Object o=s3client.getObject(bucketName, keyName);
			S3ObjectInputStream s3is = o.getObjectContent();
			String contentType = o.getObjectMetadata().getContentType();
			String fileDownloadLocation = environment.getProperty("file.download.location");
			file=new File(fileDownloadLocation+"//"+keyName);
				FileOutputStream fos =  new FileOutputStream(file);
				int read_len = 0;
				while((read_len = s3is.read(readBuf))>0){
					fos.write(readBuf,0,read_len);
				}
				
				s3is.close();
				fos.close();
			//System.out.println(imageBytes);
			
		}catch(AmazonServiceException e){
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}

	@Override
	public RestResponse deleteFile(String bucketName, String keyName) throws Exception {
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJZTA6BYNTESWQWBQ","YWzhoGSfC1ADDT+xHzvAsvf/wyMlSl71TexLLg8t");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		RestResponse response = new RestResponse();
		s3client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2));
		 try {
			 s3client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
			 response.setStatusCode(200);
	        } catch (AmazonServiceException ase) {
	          //  System.out.println("Caught an AmazonServiceException.");
	          //  System.out.println("Error Message:    " + ase.getMessage());
	         //   System.out.println("HTTP Status Code: " + ase.getStatusCode());
	          //  System.out.println("AWS Error Code:   " + ase.getErrorCode());
	          //  System.out.println("Error Type:       " + ase.getErrorType());
	          //  System.out.println("Request ID:       " + ase.getRequestId());
	            response.setStatusCode(500);
	        } catch (AmazonClientException ace) {
	         //   System.out.println("Caught an AmazonClientException.");
	         //   System.out.println("Error Message: " + ace.getMessage());
	            response.setStatusCode(500);
	        }
		 return response;
	}

	@Override
	public RestResponse deleteMultipleFile(List<KeyVersion> keys) throws Exception {
		RestResponse response = new RestResponse();
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJZTA6BYNTESWQWBQ","YWzhoGSfC1ADDT+xHzvAsvf/wyMlSl71TexLLg8t");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		s3client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_WEST_2));
		DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest("malay-first-s3-bucket-pms-test");
		multiObjectDeleteRequest.setKeys(keys);
		try {
		    DeleteObjectsResult delObjRes = s3client.deleteObjects(multiObjectDeleteRequest);
		    System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
		    response.setStatusCode(200);			
		} catch (MultiObjectDeleteException e) {
			   response.setStatusCode(500);
			   e.printStackTrace();
		}
		return response;
	}

}
