package com.project.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailDemo {

   public void sendEmail(String to)
   {
	    String subject="Welcome";
		String addtext="Registration Success";


		  //Get the session object  
		  Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication("projectjava8@gmail.com","jproject333");//change accordingly  
		   }  
		  });  
		   
		  //compose message  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress());//change accordingly  
		   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		   message.setSubject(subject);  
		   message.setText(addtext);  
		     
		   
		   //send message  
		   Transport.send(message);  
		  
		   System.out.println("message sent successfully");  
		   //response.sendRedirect("emailsuccess.jsp");
		  }
		  catch (MessagingException e) 
		  {
			  throw new RuntimeException(e);
		}  
   }	   
	   
   public void sendUserPass(String to, String uname, String upass)
	   {
		   Properties props = new Properties();  
			  props.put("mail.smtp.host", "smtp.gmail.com");  
			  props.put("mail.smtp.socketFactory.port", "465");  
			  props.put("mail.smtp.socketFactory.class",  
			            "javax.net.ssl.SSLSocketFactory");  
			  props.put("mail.smtp.auth", "true");  
			  props.put("mail.smtp.port", "465");  
			   
			  Session session = Session.getDefaultInstance(props,  
			   new javax.mail.Authenticator() {  
			   protected PasswordAuthentication getPasswordAuthentication() {  
			   return new PasswordAuthentication("projectjava8@gmail.com","jproject333");//change accordingly  
			   }  
			  });  
			   
			  //compose message  
			  try {  
			   MimeMessage message = new MimeMessage(session);  
			   message.setFrom(new InternetAddress());//change accordingly  
			   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
			   message.setSubject("Password");
			   message.setText("Hello  "+to+"\nYour Username is:="+uname+"\nYour Password is:= "+upass);
			     
			   
			   //send message  
			   Transport.send(message);  
			  
			   System.out.println("message sent successfully");  
			   //response.sendRedirect("emailsuccess.jsp");
			  }
			  catch (MessagingException e) 
			  {
				  e.printStackTrace();  
		   
	   }
   }
}
