package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.Area;


public interface AreaService {


	public List<Area> findAllAreas(Long districtId) throws Exception;



}
