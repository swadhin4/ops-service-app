package com.ops.app.constants;
// Query related to mobile apps
public class QueryConstants {

	public final static String INCIDENT_LIST_QUERY="select ct.id, ct.ticket_number as ticketNumber,"
			+ " ct.ticket_title as ticketTitle,	ct.status_id as statusId,"
			+ " st.status as statusName, ct.created_on as createdOn, ct.sla_duedate as slaDueDate"
			+ " from pm_cust_ticket ct inner join pm_user_access uc on ct.site_id = uc.site_id "
			+ " inner join pm_status st on ct.status_id = st.status_id"
			+ " where uc.user_id =:userId order by ct.id desc ";
	
	
	public final static String SITE_LIST_QUERY="select s.site_id, s.site_name, s.site_owner,"
			+ " s.primary_contact_number, s.email, s.site_number1,"
			+ " s.site_address1, s.site_address2, s.site_address3, s.site_address4, s.post_code"
			+ " from pm_site s inner join pm_user_access uc on s.site_id = uc.site_id and uc.user_id=:userId "
			+ " order by s.created_date desc";
	
	public final static String SITE_ACCESS_QUERY="select s.site_id, s.site_name "
			+ " from pm_site s inner join pm_user_access uc on s.site_id = uc.site_id and uc.user_id=:userId "
			+ " order by s.created_date desc";
	
	public final static String USER_SITE_ASSET_QUERY="select a.asset_id, a.asset_name,s.site_name,c.category_id,"
			+ " c.category_name, c.asset_type from pm_asset a, pm_site s, pm_asset_category c"
			+ " where a.site_id=s.site_id and c.category_id=a.category_id and a.del_flag=0 and s.site_id in ( ";
	
}