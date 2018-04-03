package com.ops.web.service;

import com.ops.app.vo.CustomerVO;


public interface CustomerService {

	public CustomerVO saveCustomer(CustomerVO customerVO) throws Exception;
}
