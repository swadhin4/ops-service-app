/*
 * Copyright (C) 2013 , Inc. All rights reserved
 */
package com.ops.app.util;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The Class ServiceUtil.
 *
 */
public class ServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);

	/** The field mapping. */
	private static Map<String, List<Field>> fieldMapping = new LinkedHashMap<String, List<Field>>();

	/** The field mapping. */
	private static Map<String, String> MIME_IMAGE_MAPPING = new LinkedHashMap<String, String>();

	private static Map<String, Map<String, Field>> nestedFieldMapping = new LinkedHashMap<String, Map<String, Field>>();

	private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

	static {
		MIME_IMAGE_MAPPING.put("jpe", "image/jpeg");
		MIME_IMAGE_MAPPING.put("jpeg", "image/jpeg");
		MIME_IMAGE_MAPPING.put("jpg", "image/jpeg");
		MIME_IMAGE_MAPPING.put("gif", "image/gif");
		MIME_IMAGE_MAPPING.put("png", "image/png");
		MIME_IMAGE_MAPPING.put("csv", "application/octet-stream");
	}

	/**
	 * Instantiates a new service util.
	 */
	private ServiceUtil() {

	}

	/**
	 * Find field.
	 *
	 * @param className
	 *          the class name
	 * @param fieldName
	 *          the field name
	 * @return the field
	 */
	public static Field findField(final String className, final String fieldName) {
		List<Field> fields = fieldMapping.get(className);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		if (StringUtils.contains(fieldName, ".")) {
			Map<String, Field> map = nestedFieldMapping.get(className);
			Field actualField = map.get(fieldName);
			if (map.containsKey(fieldName)) {
				return map.get(fieldName);
			}
			LOGGER.info("Finding " + fieldName + " Field in " + className);
			String array[] = StringUtils.split(fieldName, ".");
			int i = 0;
			Class<?> classType = null;
			for (String fieldN : array) {
				if (i == 0) {
					for (Field field1 : fields) {
						if (field1.getName().equals(fieldN)) {
							actualField = field1;
							classType = actualField.getType();
							break;
						}
					}
				} else {
					Field[] allFields = getAllFields(classType);
					for (Field field : allFields) {
						if (field.getName().equals(fieldN)) {
							actualField = field;
							classType = actualField.getType();
							break;
						}
					}
				}
				i++;
			}
			int len = array.length;
			String fn = array[len - 1];
			if (i == array.length && fn.equals(actualField.getName())) {
				map.put(fieldName, actualField);
				LOGGER.info("Adding " + fieldName + " Field in " + className);
				return actualField;
			} else {
				LOGGER.info("Cannot find " + fieldName + " Field in " + className);
				map.put(fieldName, null);
			}
		}
		return null;
	}

	/**
	 * Gets the all fields.
	 *
	 * @param klass
	 *          the klass
	 * @return the all fields
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getAllFields(final Class klass) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(klass.getDeclaredFields()));
		if (klass.getSuperclass() != null) {
			fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
		}
		return fields.toArray(new Field[] {});
	}

	/**
	 * Check and return value.
	 *
	 * @param value
	 *          the value
	 * @return the string
	 */
	public static String checkAndReturnValue(final String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		return value;
	}

	/**
	 * Check and return value.
	 *
	 * @param value
	 *          the value
	 * @return the string
	 */
	public static String checkAndReturnValue(final Long value) {
		if (value != null) {
			return value.toString();
		}
		return "";
	}

	/**
	 * Check and return value.
	 *
	 * @param date
	 *          the date
	 * @return the string
	 */
	public static String checkAndReturnValue(final Date date) {
		if (date == null) {
			return "";
		}
		return getDateInFormat(date, "MM/dd/yyyy HH:mm:ss");
	}

	/**
	 * Gets the date in format.
	 *
	 * @param date
	 *          the date
	 * @param format
	 *          the format
	 * @return the date in format
	 */
	public static String getDateInFormat(final Date date, final String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);

	}

	/**
	 * Gets the date in format.
	 *
	 * @param date
	 *          the date
	 * @param format
	 *          the format
	 * @return the date in format
	 */
	public static Date getDateInFormat(final String date, final String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {}

		return null;

	}

	/**
	 * Check and return value.
	 *
	 * @param enabled
	 *          the enabled
	 * @return the string
	 */
	public static String checkAndReturnValue(final Boolean enabled) {

		if (enabled == null) {
			return "false";
		}

		return enabled.toString();
	}

	/**
	 * Gets the page.
	 *
	 * @param page
	 *          the page
	 * @param rows
	 *          the rows
	 * @param sortOrder
	 *          the sort order
	 * @param orderByField
	 *          the order by field
	 * @return the page
	 */
	public static Pageable getPage(final int page, final int rows, final String sortOrder, final String... orderByField) {
		Sort.Direction sortDirection = Sort.Direction.ASC;
		if ("desc".equalsIgnoreCase(sortOrder)) {
			sortDirection = Sort.Direction.DESC;
		}
		Pageable pagebale = new PageRequest(page - 1, rows, sortDirection, orderByField);
		return pagebale;
	}

	/**
	 * Gets the page.
	 *
	 * @param page
	 *          the page
	 * @param rows
	 *          the rows
	 * @return the page
	 */
	public static Pageable getPage(final int page, final int rows) {
		Pageable pagebale = new PageRequest(page - 1, rows);
		return pagebale;
	}


	/**
	 * Gets the string in format.
	 *
	 * @param formatString
	 *          the format string
	 * @param arguments
	 *          the arguments
	 * @return the string in format
	 */
	public static String getStringInFormat(final String formatString, final Object arguments[]) {
		/*if (ArrayUtils.isEmpty(arguments)) { 
			return formatString;
		}*/
		return MessageFormat.format(formatString, arguments);
	}

	/**
	 * Gets the parent folder.
	 *
	 * @param directory
	 *          the directory
	 * @param date
	 *          the date
	 * @return the parent folder
	 */
	public static String getParentFolder(final String directory, final Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// date.get
		// String folderPath = directory + File.pathSeparator + cal.get
		// File file = new File(pathname)
		return null;
	}

	/**
	 * Gets the image mime type.
	 *
	 * @param fileName
	 *          the file name
	 * @return the image mime type
	 */
	public static String getImageMimeType(final String fileName) {
		String mimeType = MIME_IMAGE_MAPPING.get(FilenameUtils.getExtension(fileName).toLowerCase());
		if (StringUtils.isNotBlank(mimeType)) {
			return mimeType;
		} else {
			return DEFAULT_MIME_TYPE;
		}
	}

	public static void addJqGridFieldMapping(final List<Class<?>> classes) {
		if (CollectionUtils.isNotEmpty(classes)) {
			for (Class<?> clazz : classes) {
				Field[] field = getAllFields(clazz);
				List<Field> list = Arrays.asList(field);
				fieldMapping.put(clazz.getName(), list);
				Map<String, Field> nestedMapping = new HashMap<String, Field>();
				nestedFieldMapping.put(clazz.getName(), nestedMapping);
			}
		}
	}

	public static String getCurrentLoggedinUserName() {
		LOGGER.info("Inside getCurrentLoggedinUserName :");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		String username="";
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		LOGGER.info("User :"+ username);
		return username;
	}


	public static String getRestTemplateURI(final String url, final Map<String, Object> queryParams) {
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url);
		for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
			uri.queryParam(entry.getKey(), entry.getValue());
		}
		return uri.build().toUriString();
	}

	public static String generateSalt(final String username) {
		String salt = "";
		String startPart = username;
		String midPart = RandomUtils.randomStringFromRange(20, username + RandomStringUtils.randomNumeric(10));
		long currentTime = System.nanoTime();
		String time = String.valueOf(currentTime);
		int mid = time.length() / 2;
		String timeFirstPart = StringUtils.substring(time, 0, mid);
		String timeEndPart = StringUtils.substring(time, mid);
		salt = timeFirstPart + startPart + midPart + timeEndPart;
		return salt;
	}

}
