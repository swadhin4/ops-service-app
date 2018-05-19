package com.ops.web.service;

import java.util.List;

import com.ops.app.vo.LoginUser;
import com.ops.jpa.entities.IncidentReport;

public interface IncidentReportService {

	public List<IncidentReport> findAllIncident();
	
	public List<IncidentReport> findReportsByUser(LoginUser loggedUser);
}
