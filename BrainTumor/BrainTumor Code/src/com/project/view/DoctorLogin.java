package com.project.view;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.project.bean.DoctorBean;
import com.project.dao.DoctorDao;
import com.project.validation.EmailAndMobile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class DoctorLogin 
{
	private JFrame frame;
	private JPanel panel1, panel2;
	private JLabel emailLabel,passwordLabel,loginLabel,headerLabel;
	private JTextField emailText;
	private JPasswordField passwordText;
	private JButton loginButton,clearButton, backButton;
	Boolean resultStatus=Boolean.FALSE;
	public static String doc_email;
	
	public DoctorLogin() 
	 {
		frame=new JFrame("DOCTOR LOGIN FORM");
	    frame.setSize(1150,750);
	    //frame.setLocation(250,200);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setBounds(200,160,600,440);
		
		panel1=new JPanel();
		panel1.setBounds(250,50,750,120);
		
		panel2 = new JPanel();
		panel2.setBounds(420,180,300,400);
		
		
		panel2.setOpaque(false);
		frame.setLayout(null);
		frame.setContentPane(new JLabel(new ImageIcon("images/sky2.jpg")));
		
		
		panel2.setLayout(null);
		
		headerLabel=new JLabel("BRAIN TUMOR SEGMENTATION AND PREDICTION");
		headerLabel.setBounds(150, 0, 160, 25);
		//headerLabel.setSize(headerLabel.getPreferredSize());
		headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
		headerLabel.setForeground(Color.BLACK);
		panel1.add(headerLabel);
		
		loginLabel=new JLabel("DOCTOR LOGIN HERE...");
		loginLabel.setBounds(60, 10, 300, 25);
		loginLabel.setFont(new Font("Arial", Font.BOLD, 22));
		panel2.add(loginLabel);

		emailLabel = new JLabel("Email");
		emailLabel.setBounds(10, 60, 80, 25);
		emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(emailLabel);

		emailText = new JTextField(20);
		emailText.setBounds(100, 60, 160, 25);
		panel2.add(emailText);

		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 100, 80, 25);
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel2.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 100, 160, 25);
		panel2.add(passwordText);

		loginButton = new JButton("Login");
		loginButton.setBounds(50, 150, 80, 30);
		panel2.add(loginButton);
		
		loginButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)	
		  {
			  String email=emailText.getText();
		      boolean validEmail =EmailAndMobile.isValidEmailAddress(email);
		      
		      String pass= passwordText.getText();
			   
			    if(emailText.getText().length()==0)
		         {
		            JOptionPane.showMessageDialog(frame,"Please Enter Doctor Email");
		         }
			    else if(validEmail==false)
		         {       
		            JOptionPane.showMessageDialog(frame,"Please Enter Valid Email Id");  
		         }
		        else if(passwordText.getText().length()==0)
		         {
		            
		            JOptionPane.showMessageDialog(frame,"Please Enter Password");
		             
		         }
		        else
		         {   
		           DoctorBean bean=new DoctorBean();
			       bean.setDoctor_email(email);;
			       bean.setDoctor_password(pass);
			       
			       DoctorDao dd=new DoctorDao();
			   
			   
			      if(resultStatus=dd.checkDoctorLogin(bean)) 
			       {
			    	  doc_email=email;
			    	  try {
							UIManager
									.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
			    	  JOptionPane.showMessageDialog(frame, "Doctor Login Successfully!!!");
			    	  ImageProcessing ip=new ImageProcessing();
			    	  ip.setVisible(true);
				      frame.dispose();
			       } 
			     else
			       {

				     JOptionPane.showMessageDialog(null,"Wrong Password / Username");
				     emailText.setText("");
				     passwordText.setText("");
				     emailText.requestFocus();
			       }
		        }
		    }
		  
		   }
		   );
		
		clearButton = new JButton("Clear");
		clearButton.setBounds(180, 150, 80, 30);
		panel2.add(clearButton);
		
		backButton= new JButton("Back");
		backButton.setBounds(120, 200, 80, 30);
		panel2.add(backButton);
		panel2.setOpaque(false);
		
		clearButton.addActionListener(new java.awt.event.ActionListener()
		   {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clearButtonActionPerformed(evt);
            }
        });
		
		backButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)	
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
		);	
		
	    panel1.setOpaque(false);
		panel2.setOpaque(false);
		
		frame.add(panel1);
		frame.add(panel2);
		frame.setVisible(true);
	 }
	
	
	private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) 
	  {
        
		emailText.setText("");
        passwordText.setText("");
      
      }
		
	
}
