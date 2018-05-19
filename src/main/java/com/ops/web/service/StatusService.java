package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.Status;

public interface StatusService {
	public List<Status> getStatusByCategory(String category) throws Exception;

}
