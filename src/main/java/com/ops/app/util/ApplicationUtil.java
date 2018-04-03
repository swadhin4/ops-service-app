package com.ops.app.util;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationUtil.class);
	
	@Value("${file.upload.location}")
	private static String fileUploadLcation;
	
	@Value("${file.download.location}")
	private static String fileDownloadLocation;
	
	public static String getServerUploadLocation(){
		LOGGER.info("Inside ApplicationUtil .. getServerUploadLocation");
		LOGGER.info("Getting server resource storage location...");
		Path path = FileSystems.getDefault().getPath(ApplicationUtil.fileUploadLcation); 
	 	String fileUploadLocation = path.toString();
	 	LOGGER.info("Resource Storage location : "+ fileUploadLocation);
	 	LOGGER.info("Exit ApplicationUtil .. getServerUploadLocation");
		return fileUploadLocation;
	}
	
	public static String getServerDownloadLocation(){
		LOGGER.info("Inside ApplicationUtil .. getServerDownloadLocation");
		LOGGER.info("Getting server resource storage location...");
		Path path = FileSystems.getDefault().getPath(ApplicationUtil.fileDownloadLocation); 
	 	String fileDownloadLocation = path.toString();
	 	LOGGER.info("Resource Storage location : "+ fileDownloadLocation);
	 	LOGGER.info("Exit ApplicationUtil .. getServerDownloadLocation");
		return fileDownloadLocation;
	}
	
	
}
