package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.Company;


public interface CompanyService {

	public Company findCompany(Long companyId);

	public List<Company> findCompanyByType();

	public List<Company> findAssetServiceProvider();


}
