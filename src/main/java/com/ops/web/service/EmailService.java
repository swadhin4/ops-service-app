package com.ops.web.service;

import com.ops.app.util.RestResponse;
import com.ops.app.vo.AppUserVO;
import com.ops.app.vo.LoginUser;
import com.ops.app.vo.ServiceProviderVO;
import com.ops.app.vo.TicketVO;
import com.ops.jpa.entities.SPEscalationLevels;

public interface EmailService {


	//public void sendEmail(EmailTemplate emailTemplate) throws MailException;
	public RestResponse sendEmail(String to, String message) throws Exception;
	public RestResponse sendSuccessSaveEmail(String emailId, AppUserVO appUserVO) throws Exception;
	public RestResponse successSaveSPEmail(ServiceProviderVO serviceProviderVO, LoginUser loginUser) throws Exception;
	public void successTicketCreationSPEmail(TicketVO ticketVO, String creationStatus, String company) throws Exception;
	public RestResponse sendForgotPasswordEmail(String email, String passwordResetLink) throws Exception;
	RestResponse successEscalationLevel(TicketVO ticketVO, SPEscalationLevels spEscalationLevel, String ccLevelEmail, String level) throws Exception;
}
