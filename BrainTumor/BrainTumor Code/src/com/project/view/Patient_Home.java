package com.project.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Patient_Home {

JFrame frame;
	
	private JPanel panel1,panel2;
	private JLabel label1,label2;
	private JButton report_Button, back_Button;
	
	
   public Patient_Home()
   {
 	    frame = new JFrame("PateintHomePage");
	    frame.setSize(1150,750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		panel1=new JPanel();
		panel1.setBounds(200,70,750,80);
		
		panel2=new JPanel();
		//panel2.setBounds(0,130,150,500);
		panel2.setBounds(200, 180, 1275, 300);
		panel2.setLayout(null);
		
		label1=new JLabel("BRAIN TUMOR SEGMENTATION AND PREDICTION");
		label1.setBounds(350, 10, 160, 25);
		label1.setFont(new Font("Arial", Font.BOLD, 22));
		label1.setForeground(Color.black);
		panel1.add(label1);
		panel1.setOpaque(false);
		
		label2=new JLabel("PATIENT HOME PAGE");
		label2.setBounds(280, 0, 300, 25);
		label2.setFont(new Font("Arial", Font.BOLD, 20));
		label2.setForeground(Color.black);
		panel2.add(label2);
		panel2.setOpaque(false);
		
		report_Button = new JButton("View Report");
		report_Button.setBounds(230, 70, 130, 50);
		panel2.add(report_Button);
		panel2.setOpaque(false);
		
		back_Button= new JButton("Back");
		back_Button.setBounds(400, 70, 130, 50);
		panel2.add(back_Button);
		panel2.setOpaque(false);
		
		back_Button.addActionListener(new ActionListener()
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
		
	        frame.setContentPane(new JLabel(new ImageIcon("images/sky2.jpg")));
	 	    frame.add(panel1);
	 	    frame.add(panel2);
	 	    frame.setLocationRelativeTo(null);
	 		frame.setVisible(true);
     }
   public static void main(String args[])
   {
	   try 
	    {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	   
      new Patient_Home();
   }
}
