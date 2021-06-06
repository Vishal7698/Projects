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

public class Home 
{
	JFrame frame;
	
	private JPanel panel1,panel2;
	private JLabel label1,label2;
	private JButton Patient_LoginButton,Patient_RegisterButton,Doctor_LoginButton;
	
	
   public Home()
   {
 	    frame = new JFrame("HomePage");
	    frame.setSize(1150,750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
	panel1=new JPanel();
		panel1.setBounds(250,50,750,80);
		
		
		
		panel2=new JPanel();
		//panel2.setBounds(0,130,150,500);
		panel2.setBounds(200, 180, 1275, 100);
		panel2.setLayout(null);
		
		
		
		label1=new JLabel("BRAIN TUMOR SEGMENTATION AND PREDICTION");
		label1.setBounds(350, 10, 160, 25);
		label1.setFont(new Font("Arial", Font.BOLD, 22));
		label1.setForeground(Color.black);
		panel1.add(label1);
		panel1.setOpaque(false);
		
		label2=new JLabel("HOME PAGE");
		label2.setBounds(310, 0, 160, 25);
		label2.setFont(new Font("Arial", Font.BOLD, 20));
		label2.setForeground(Color.black);
		panel2.add(label2);
		panel2.setOpaque(false);
		
		Patient_LoginButton = new JButton("Patient Login");
		Patient_LoginButton.setBounds(170, 50, 130, 50);
		panel2.add(Patient_LoginButton);
		panel2.setOpaque(false);
		
			
		Patient_RegisterButton = new JButton("Patient Register");
		Patient_RegisterButton.setBounds(320, 50, 130, 50);
		panel2.add(Patient_RegisterButton);
		panel2.setOpaque(false);
		
		
		Doctor_LoginButton= new JButton("Doctor Login");
		Doctor_LoginButton.setBounds(470, 50, 130, 50);
		panel2.add(Doctor_LoginButton);
		panel2.setOpaque(false);
		
		Patient_LoginButton.addActionListener(new ActionListener()
			{
			  public void actionPerformed(ActionEvent e)	
			  {
				  try 
				    {
						UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				 PatientLogin login=new PatientLogin();
				 //login.setVisible(true);
				 frame.dispose();
			  }
			  
			}
			);
		 	
		Patient_RegisterButton.addActionListener(new ActionListener()
	       {
	         public void actionPerformed(ActionEvent e)	
	          {
	        	 try 
	        	 {
	 				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	 			 } catch (Exception e1) {
	 				e1.printStackTrace();
	 			 }
		         Patient_Register register=new Patient_Register();
		        //register.setVisible(true);
		        frame.dispose();
	          }
	  
	       }
		);
		
		Doctor_LoginButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)	
		  {
			  try 
			    {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			 DoctorLogin login=new DoctorLogin();
			 //login.setVisible(true);
			 frame.dispose();
		  }
		  
		}
		);
            
	        frame.setContentPane(new JLabel(new ImageIcon("images/sky2.jpg")));
	       /* File input = new File("/tmp/duke.png");
	        BufferedImage image = ImageIO.read(input);

	        BufferedImage resized = resize(image, 500, 500);

	        File output = new File("/tmp/duke-resized-500x500.png");
	        ImageIO.write(resized, "png", output);*/
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
    	   
	       new Home();
        }
}
