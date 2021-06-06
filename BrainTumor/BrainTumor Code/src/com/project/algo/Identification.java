package com.project.algo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.project.db.DBConnect;

public class Identification{
	
    public static String result;
    
    public static String stage;
    
	 public static String test1(float inputtumorarea) 
     {
		 try { 
				
			 if(inputtumorarea>36)
		    {
		    	
		        result="Brain Tumor Is In Critical Stage";
		        stage="Critical";
		    }
		    else if(inputtumorarea<36 && inputtumorarea>1)
		    {
		    	
		    	Connection connection=DBConnect.getConnection();
			    PreparedStatement psmt;
				ResultSet rs;
				//String stage="";
				try {
					
					int tarea=(int)inputtumorarea;
					String sql="Select Stage from dataset where Area='"+tarea+"'";
					System.out.println("Sql===="+sql);
					
					psmt = connection.prepareStatement(sql);
					
					/*psmt.setString(1,bean.getUname());
					psmt.setString(2,bean.getUpassword());*/
					rs=psmt.executeQuery();
					
					while(rs.next())
					{
						String result1=rs.getString(1);
						result= "Brain Tumor Is In \n"+result1+ " Stage";
						stage=result1;
					}
					
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			
					
		    }
		    else
		    {
		    	result="Image Is Healthy Image";
		    	stage=result;
		    }
				} 
			catch ( Exception e )
			{e.printStackTrace();
			}
			
		 return result;
     }
     
	
	public String getStage(Float area)
	{
		
		Connection connection=DBConnect.getConnection();
	    PreparedStatement psmt;
		ResultSet rs;
		String stage="";
		try {
			String sql="Select Stage from dataset where Area=+area";
			
			psmt = connection.prepareStatement(sql);
			
			/*psmt.setString(1,bean.getUname());
			psmt.setString(2,bean.getUpassword());*/
			rs=psmt.executeQuery();
			
			while(rs.next())
			{
				stage=rs.getString(3);
			}
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	
		return stage;
	}
	
	
}
