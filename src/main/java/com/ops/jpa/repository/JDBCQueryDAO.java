package com.ops.jpa.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.ops.jpa.entities.IncidentReport;
import com.ops.jpa.entities.User;

@Repository
public class JDBCQueryDAO{
	private SimpleJdbcCall jdbcCall;
	
	private JdbcTemplate jdbcSqlCall;
	
	private SimpleJdbcCall jdbcSPCall;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcCall =  new SimpleJdbcCall(dataSource).withProcedureName("usp_update_sla_duedate");
	}
	
	@Autowired
	public void setSQLDataSource(DataSource dataSource) {
		this.jdbcSqlCall =  new SimpleJdbcCall(dataSource).getJdbcTemplate();
	}
	
	
	@Autowired
	public void setSPDataSource(DataSource dataSource) {
		this.jdbcSPCall =  new SimpleJdbcCall(dataSource).withProcedureName("usp_generate_sp_unique_id");
	}
	
	public String getSlaDate(String ticketNumber, Integer slaDuration, String slaUnit) {
		 SqlParameterSource in = new MapSqlParameterSource()
				 .addValue("p_ticket_number", ticketNumber)
				 .addValue("p_sla_duration", slaDuration)
				 .addValue("p_sla_unit", slaUnit);
		 
	      Map<String, Object> out = jdbcCall.execute(in);
	      String slaDueDate = (String)out.get("out_sla_due_date");
	      return slaDueDate;
	      
	}

	public List<IncidentReport> getIncidentReports(){
		return jdbcSqlCall.query("Select * from vw_incident_report", new RowMapper<IncidentReport>(){
			@Override
			public IncidentReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				return null;
			}
			
		});
	}
	
	public List<User> getUserByEmail(String email){
		return jdbcSqlCall.query("Select user_id, email_id from pm_users where email_id='"+email+"'", new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				 User user=new User();
				 user.setUserId(rs.getLong(1));
				 user.setEmailId(rs.getString(2));
			     return user;  
			}
			
		});
	}
	
	public Integer getUniqueSPUserId() {
		  SqlParameterSource in = new MapSqlParameterSource();
	      Map<String, Object> out = jdbcSPCall.execute(in);
	      Integer uniqueSPUserId = (Integer)out.get("rand_user_id");
	      return uniqueSPUserId;
	}
}
