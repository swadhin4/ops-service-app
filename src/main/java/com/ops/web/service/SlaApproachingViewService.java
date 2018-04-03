package com.ops.web.service;

import java.util.List;

import com.ops.jpa.entities.SlaApproachingView;

public interface SlaApproachingViewService {

	public List<SlaApproachingView> findTicketsApproachingSLA(int noOfDays);


	public List<SlaApproachingView> findTicketsApproachingSLA();

}
