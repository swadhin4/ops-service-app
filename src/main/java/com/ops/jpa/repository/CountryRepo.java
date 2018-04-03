package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.Country;



public interface CountryRepo extends JpaRepository<Country, Long>{

	@Query("from Country c where c.regionId=:regionId")
	public List<Country> findByRegionId(@Param(value="regionId") Long regionId);
}
