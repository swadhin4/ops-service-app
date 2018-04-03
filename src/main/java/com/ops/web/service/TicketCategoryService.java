package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.TicketCategory;


public interface TicketCategoryService {


	public List<TicketCategory> getAllTicketCategories() throws Exception;


}
