package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.District;



public interface DistrictRepo extends JpaRepository<District, Long>{

	@Query("from District d where d.country=:countryId")
	public List<District> findByCountryId(@Param(value="countryId") Long countryId);
}
