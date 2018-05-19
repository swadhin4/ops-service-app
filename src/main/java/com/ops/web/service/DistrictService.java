package com.ops.web.service;

import java.util.List;

import com.ops.app.vo.DistrictVO;
import com.ops.jpa.entities.District;


public interface DistrictService {

	public List<District> findAllDistricts() throws Exception;

	public List<DistrictVO> findDistrictByCountry(Long countryId) throws Exception;

}
