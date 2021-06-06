package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project.bean.DoctorBean;
import com.project.db.DBConnect;

public class DoctorDao {

	
	PreparedStatement st;
    ResultSet rs;
    Boolean resultStatus=Boolean.FALSE;
    Connection con=DBConnect.getConnection();
    
    public  boolean checkDoctorLogin(DoctorBean bean)
    {
		
   	try {
			String sql="Select * from tbl_doctor where doctor_email=? and doctor_pass=?";
			st = con.prepareStatement(sql);
			st.setString(1,bean.getDoctor_email());
			st.setString(2,bean.getDoctor_password());
			ResultSet rs=st.executeQuery();
			resultStatus=rs.next();
		} 
		catch (SQLException e)
   	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	
		return resultStatus;
		 	
		 
	}
}
