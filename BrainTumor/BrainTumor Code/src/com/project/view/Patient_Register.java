package com.project.view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.commons.io.FileUtils;

import com.project.bean.PatientBean;
import com.project.dao.PatientDao;
import com.project.service.EmailDemo;
import com.project.validation.EmailAndMobile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
public class Patient_Register
{
	private JFrame frame;
	private JLabel headerLabel,patientLabel,addressLabel,ageLabel, emailLabel,mobnoLabel,RegisterLabel;
	private JLabel passwordLabel;
	private JTextField patientText,addressText,ageText, emailText,mobnoText;
	
	private JPasswordField passwordText;
	private JButton registerButton,clearButton, loginButton, backButton;
	
    private InputStream is;
    private JPanel panel1,panel2;
    Boolean resultStatus=Boolean.FALSE;
	

	public Patient_Register()
	 {
		frame = new JFrame("REGISTRATION FORM");
		frame.setSize(1150,750);
		//frame.setLocation(250,200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel1=new JPanel();
		panel1.setBounds(250,50,750,80);
		
		panel2 = new JPanel();
		panel2.setBounds(420,180,300,500);
		
		
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		frame.setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon("images\\sky2.jpg")));
		
		panel2.setLayout(null);
		
		headerLabel=new JLabel("BRAIN TUMOR SEGMENTATION AND PREDICTION");
		headerLabel.setBounds(110, 0, 160, 25);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
		headerLabel.setForeground(Color.BLACK);
		panel1.add(headerLabel);
		
		RegisterLabel=new JLabel("REGISTRATION");
		RegisterLabel.setBounds(90, 0, 200, 20);
		RegisterLabel.setFont(new Font("Arial", Font.BOLD, 22));
		panel2.add(RegisterLabel);

		patientLabel = new JLabel("Name");
		patientLabel.setBounds(10, 30, 80, 25);
		patientLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(patientLabel);

		patientText = new JTextField(20);
		patientText.setBounds(100, 30, 160, 25);
		panel2.add(patientText);

		addressLabel = new JLabel("Address");
		addressLabel.setBounds(10, 70, 80, 25);
		addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(addressLabel);

		addressText = new JTextField(20);
		addressText.setBounds(100, 70, 160, 25);
		panel2.add(addressText);
		
		/******************************/
		ageLabel= new JLabel("Age");
		ageLabel.setBounds(10, 110, 80, 25);
		ageLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(ageLabel);
		
		ageText = new JTextField(20);
		ageText.setBounds(100, 110, 160, 25);
		panel2.add(ageText);
		
		emailLabel = new JLabel("Email");
		emailLabel.setBounds(10, 150, 80, 25);
		emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(emailLabel);

		emailText = new JTextField(20);
		emailText.setBounds(100, 150, 160, 25);
		panel2.add(emailText);
		
		mobnoLabel = new JLabel("Mobno");
		mobnoLabel.setBounds(10, 190, 80, 25);
		mobnoLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(mobnoLabel);

		mobnoText = new JTextField(20);
		mobnoText.setBounds(100, 190, 160, 25);
		panel2.add(mobnoText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 230, 80, 25);
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 230, 160, 25);
		panel2.add(passwordText);
	
		registerButton = new JButton("Register");
		registerButton.setBounds(10, 270, 100, 30);
		registerButton.setForeground(Color.BLACK);
		panel2.add(registerButton);
		
		
		clearButton = new JButton("Clear");
		clearButton.setBounds(180, 270, 80, 30);
		clearButton.setForeground(Color.BLACK);
		panel2.add(clearButton);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(10, 310, 100, 30);
		loginButton.setForeground(Color.BLACK);
		panel2.add(loginButton);
		
		backButton= new JButton("Back");
		backButton.setBounds(180, 310, 80, 30);
		panel2.add(backButton);
		panel2.setOpaque(false);
		
		
		registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	registerButtonActionPerformed(evt);
            }
        });
		
		
		clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clearButtonActionPerformed(evt);
            }
        });
		
		loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	loginButtonActionPerformed(evt);
            }
        });
		
		backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	backButtonActionPerformed(evt);
            }
        });
		
		passwordText.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	passwordTextActionPerformed(evt);
	            }
	        });
		frame.add(panel1);
		frame.add(panel2);
		frame.setVisible(true);
	 }
		
	    
		private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) 
		{
	        String uname=patientText.getText();
	        boolean validName =EmailAndMobile.isValidName(uname);
	        
	        String address=addressText.getText();
	        
	        String age=ageText.getText();
	        boolean validAge =EmailAndMobile.isValidAge(age);
	       
	        String email=emailText.getText();
	        boolean validEmail =EmailAndMobile.isValidEmailAddress(email);
	        
	        String mobNo=mobnoText.getText();
	        boolean validMobilNo =EmailAndMobile.isValidMobilNumber(mobNo);
	        
	        String password=passwordText.getText();
	        
	       
	        
	        if(patientText.getText().length()==0)
	        {
	            JOptionPane.showMessageDialog(frame,"Please Enter Patient Name");
	        }
	        else if(validName==false)
	        {
	           
	            JOptionPane.showMessageDialog(frame,"Please Enter Valid Name"); 
	              
	        }
	       
	        else if(addressText.getText().length()==0)
	        {
	            
	            JOptionPane.showMessageDialog(frame,"Please Enter Address"); 
	        
	        }
	        
	        else if(ageText.getText().length()==0)
	        {
	        	
	            JOptionPane.showMessageDialog(frame,"Please Enter User Age");
	            
	        }
	        else if(validAge==false)
	        {
	            
	            JOptionPane.showMessageDialog(frame,"Please Enter Valid Age"); 
	             
	        }
	       
	        else if(emailText.getText().length()==0)
	        {
	        	
	            JOptionPane.showMessageDialog(frame,"Please Enter Email");
	            
	        }
	        else if(validEmail==false)
	        {
	           
	            JOptionPane.showMessageDialog(frame,"Please Enter Valid Email Id"); 
	                
	        }
	        else if(mobnoText.getText().length()==0)
	        {
	            JOptionPane.showMessageDialog(frame,"Please Enter Mobile");
	            
	        }
	        else if(validMobilNo==false)
	        {
	                    
	            
	            JOptionPane.showMessageDialog(frame,"Please Enter Valid Mobile Number"); 
	                          
	            
	        }
	        else if(passwordText.getText().length()==0)
	        {
	           
	            JOptionPane.showMessageDialog(frame,"Please Enter Password");
	            
	        }
	                    
	        else
	        {
	        
	         PatientBean bean=new PatientBean();
	         bean.setPatient_name(uname);
	         bean.setAddress(address);
	         bean.setAge(age);
	         bean.setEmail(email);
	         bean.setMobNo(mobNo);
	         bean.setPassword(password);
	         
	         PatientDao ud=new PatientDao();
	         
	 		 
	        if(ud.patientRegistration(bean))
	        {
	        	 EmailDemo ed=new EmailDemo();
		 		 ed.sendEmail(email);
	             JOptionPane.showMessageDialog(frame,"Patient Register Sucessfully !!!");
	         
	         try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	          PatientLogin login=new PatientLogin();
	          //login.setVisible(true);
	          frame.dispose();
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(frame,"Patient Registration Fail !!!");
	        }
	        }
		}
		
		private void passwordTextActionPerformed(java.awt.event.ActionEvent evt) 
		 {
			//GEN-FIRST:event_jPasswordField1ActionPerformed
		        // TODO add your handling code here:
		 }
		
		
	       
		private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) 
		{
	        
			patientText.setText("");
	        passwordText.setText("");
	        addressText.setText("");
	        emailText.setText("");
	        mobnoText.setText("");
	        ageText.setText("");
	      
	     }
		
		 private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) 
		   {
			 try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		        PatientLogin login=new PatientLogin();
		        //login.setVisible(true);
		        frame.dispose();
		        
		   }
		
		 private void backButtonActionPerformed(java.awt.event.ActionEvent evt) 
		   {
			 try 
			    {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			 Home h=new Home();
			 //login.setVisible(true);
			 frame.dispose();
		  }
		
}
