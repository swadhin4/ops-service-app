package com.ops.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.jpa.entities.Area;
import com.ops.jpa.repository.AreaRepo;
import com.ops.web.service.AreaService;



@Service("areaService")
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaRepo areaRepo;

	@Override
	public List<Area> findAllAreas(Long districtId) throws Exception {
		return areaRepo.findAreaBy(districtId);
	}


}
