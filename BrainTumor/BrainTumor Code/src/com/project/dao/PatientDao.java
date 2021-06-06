
package com.project.dao;

import com.project.db.*;
import com.project.bean.PatientBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PatientDao {
    
    PreparedStatement st;
    ResultSet rs;
    Boolean resultStatus=Boolean.FALSE;
    Connection con=DBConnect.getConnection();
    
    public boolean patientRegistration(PatientBean bean)
	   {
		try
		   {
		    String sql="Select * from tbl_patient where patient_email=?";
				st = con.prepareStatement(sql);
				st.setString(1,bean.getEmail());
				rs=st.executeQuery();
				Boolean b=rs.next();
				
				if(b==true)
				{
				System.out.println("Record already exist");
				}
				
				else
				{
				
				String SQL="insert into tbl_patient(patient_name, patient_address, patient_age, patient_email, patient_mob, patient_password) values(?,?,?,?,?,?)"; 
				
					PreparedStatement pstmt=con.prepareStatement(SQL);
					pstmt.setString(1, bean.getPatient_name());
					pstmt.setString(2, bean.getAddress());
					pstmt.setString(3, bean.getAge());
					pstmt.setString(4, bean.getEmail());
					pstmt.setString(5, bean.getMobNo());
					pstmt.setString(6, bean.getPassword());
					
					int index=pstmt.executeUpdate();
					
					if(index>0)
					{
						resultStatus=Boolean.TRUE;
					}
					else
					{
						resultStatus=Boolean.FALSE;	
					}
					
			   
	     	    }
		   }
				
				catch (SQLException e) 
				  {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }
					
		     return resultStatus;
				
		 }
	
   
     
    public  boolean loginCheck(PatientBean bean)
     {
		
    	try {
			String sql="Select * from tbl_patient where patient_email=? and patient_password=?";
			st = con.prepareStatement(sql);
			st.setString(1,bean.getEmail());
			st.setString(2,bean.getPassword());
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
    
    
    public  ArrayList<PatientBean> patientDetails(String name)
	{
		
	   ResultSet rs=null;
	   
	   ArrayList<PatientBean> details = new ArrayList<PatientBean>();
	   
       String sql = "Select * from tbl_patient where patient_name='"+name+"'";
		
		try {
			
			Statement stmt=con.createStatement();
		
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				PatientBean bean=new PatientBean();
				bean.setId(rs.getInt(1));
				bean.setPatient_name(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setAge(rs.getString(4));
				bean.setEmail(rs.getString(5));
				bean.setMobNo(rs.getString(6));
				
				details.add(bean);
			}
			
		 } 
		catch (SQLException e) 
		   {
			
			 e.printStackTrace();
		   }
		return details;
		
		
	}
    
    public  ArrayList<String> getAllPatientDetails()
   	{
   		
   	   ResultSet rs=null;
   	   
   	   ArrayList<String> details = new ArrayList<String>();
   	   
          String sql = "Select * from tbl_patient";
   		
   		try {
   			
   			Statement stmt=con.createStatement();
   		
   			rs = stmt.executeQuery(sql);
   			
   			while(rs.next())
   			{
   				details.add(rs.getString(2));
   			}
   			
   		 } 
   		catch (SQLException e) 
   		   {
   			
   			 e.printStackTrace();
   		   }
   		return details;
   		
   		
   	}
   
}
