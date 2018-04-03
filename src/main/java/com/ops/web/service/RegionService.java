package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.Region;


public interface RegionService {

	public List<Region> findAllRegions() throws Exception;

}
