package com.ops.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.Status;
import com.ops.jpa.repository.StatusRepo;
import com.ops.web.service.StatusService;

@Service("statusService")
public class StatusServiceImpl implements StatusService{

	private final static Logger LOGGER = LoggerFactory.getLogger(StatusServiceImpl.class);

	@Autowired
	private StatusRepo statusRepo;

	@Override
	public List<Status> getStatusByCategory(final String category) throws Exception {
		List<Status> statusList = statusRepo.findByStatusCategory(category);		
		return statusList == null?Collections.EMPTY_LIST:statusList;
	}



}
