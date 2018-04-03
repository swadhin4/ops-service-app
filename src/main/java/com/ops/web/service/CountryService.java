package com.ops.web.service;

import java.util.List;

import com.ops.app.vo.CountryVO;
import com.ops.jpa.entities.Country;


public interface CountryService {

	public Country findCountry(Long countryId);

	public CountryVO findCountryBy(Long countryId);

	public List<Country> findAllCountries();

	public List<Country> findCountryByRegion(Long regionId);

}
