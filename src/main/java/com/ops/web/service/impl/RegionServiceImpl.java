package com.ops.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.Region;
import com.ops.jpa.repository.RegionRepo;
import com.ops.web.service.RegionService;

@Service("regionService")
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepo regionRepo;

	@Override
	public List<Region> findAllRegions() throws Exception {
		List<Region> regionList = regionRepo.findAll();
		return  regionList== null? Collections.EMPTY_LIST:regionList;
	}

}
