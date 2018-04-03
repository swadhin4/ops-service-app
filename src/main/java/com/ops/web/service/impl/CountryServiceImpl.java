package com.ops.web.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.app.vo.CountryVO;
import com.ops.jpa.entities.Country;
import com.ops.jpa.repository.CountryRepo;
import com.ops.web.service.CountryService;



@Service("countryService")
public class CountryServiceImpl implements CountryService{


	@Autowired
	private CountryRepo countryRepo;

	@Override
	public Country findCountry(final Long countryId) {
		return countryRepo.findOne(countryId);
	}

	@Override
	public List<Country> findAllCountries() {
		List<Country> countryList =  countryRepo.findAll();
		return countryList == null?Collections.EMPTY_LIST:countryList;
	}

	@Override
	public CountryVO findCountryBy(final Long countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Country> findCountryByRegion(Long regionId) {
		List<Country> countryList =  countryRepo.findByRegionId(regionId);
		return countryList == null?Collections.EMPTY_LIST:countryList;
	}


}
