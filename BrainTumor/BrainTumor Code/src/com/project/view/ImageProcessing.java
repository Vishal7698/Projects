package com.project.view;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import com.project.algo.Fuzzy;
import com.project.algo.GrayDemo;
import com.project.algo.Identification;
import com.project.algo.Kmeans;
import com.project.algo.MedianFilter;
import com.project.algo.PDF;
import com.project.algo.PSNR;
import com.project.algo.PixelCount;
import com.project.algo.Thresholding;
import com.project.algo.TumorDetection;
import com.project.bean.PatientBean;
import com.project.dao.PatientDao;

public class ImageProcessing extends javax.swing.JFrame 
{
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet rs=null;
  String filePath=null;
  String filePath1=null;
  public static String FileName=null;
  
  String TumorArea="";
  String stageofTumor="";
  String identification="";
  
  String fileName="";
  String patientName=null;
  
  File source;
  File dest;
  public static float tumorarea;
 //D:\Laptop  Workspace\PC IEEE Project Guru 2019\BrainTumor\src\com\project\images
  String Path="D:/Laptop  Workspace/PC IEEE Project Guru 2019/BrainTumor/";
	
  String appPath=Path+"src/com/project/images";
  
  
  public ImageProcessing() 
  {

   initComponents();
   setSize(1100,770);
   //setLocation(80,100);
   setLocationRelativeTo(null);
   setVisible(true);
   
  }

  public void showImage()
  {
	  jScrollPane2.setText("");
	  patientName=(String) patient_List.getSelectedItem();
	  System.out.println("Your seleted patient: " + patientName);
	  /*patientName=nameText.getText();*/
	  
	  try
	  {
		//display image//C:\Users\Cwc\Desktop\Project Pictures\BrainMRIImages
		  	JFileChooser chooser=new JFileChooser(new File("C:\\Users\\Cwc\\Desktop\\Project Pictures\\BrainMRIImages"));

		  	chooser.setMultiSelectionEnabled(false);
		  	chooser.setVisible(true);

		  	chooser.showOpenDialog(this);

		  	File file=chooser.getSelectedFile();
		  	FileName=file.getName();
		  	
		  	filePath=file.getPath();
		  	
		  	if(filePath!=null)
		  	{
		  		 
		  		JOptionPane.showMessageDialog(this, "Brain Image Uploaded Successfully!!!");
			  	
		  		path.setText("File path:-"+" "+filePath);
			  	showimage.setIcon(new ImageIcon(filePath));
			  	filename.setText(file.getName());
			  	
			  	file=chooser.getSelectedFile();
			  	BufferedImage patientImage = ImageIO.read(file);
			  	source=new File(appPath+"/"+patientName+"_Img.jpg");
		        ImageIO.write(patientImage, "jpg", source);
			  	
			  	dest = new File(appPath);
			  	
		        if (!dest.exists()) 
		        {
		            if (dest.mkdir()) 
		            {
		                System.out.println("Directory is created!");
		            } else {
		                System.out.println("Failed to create directory!");
		            }
		        }
			 }
		  	
		  	else
		  	{
		  		JOptionPane.showMessageDialog(this,"Please select image");
		  	}
	  }
   catch(Exception e)
   {
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
   }
}
public void showOriginalImage()
{
	try
	  {
		 
		    JOptionPane.showMessageDialog(this, "Image Stored Successfully!!!");
		  	if(source!=null)
		  	{
		  		filePath=source.getPath();
		  	}
		  	if(filePath!=null)
		  	{
		  		path.setText("File path:-"+" "+filePath);
		  		showimage.setIcon(new ImageIcon(filePath));
		  	} 
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}
}

public void showGrayImage()
{
	try
	  {    
		   //File file=new File(appPath+"/"+filename.getText());
		   BufferedImage grayImg=GrayDemo.toGray(source);
		   
		   File file=new File(appPath+"/GrayImg.jpg");
		   
		   ImageIO.write(grayImg, "jpg", file);
		   
		   JOptionPane.showMessageDialog(this, "Image Gray Successfully!!!");
		   
		  	if(file!=null)
		  	{
		  		filePath=file.getPath();
		  	}
		  	if(filePath!=null)
		  	{
		  		path.setText("File path:-"+" "+filePath);
		  		showimage.setIcon(new ImageIcon(filePath));
		  	} 
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}
}

public void showFilterImage()
{
	try
	  {     
		   File file=new File(appPath+"/GrayImg.jpg");
		   
		   BufferedImage filterImg=MedianFilter.medianFilter(file);
		   
		   file=new File(appPath+"/FilteredImg.jpg");
		   
		   ImageIO.write(filterImg, "jpg", file);
		   
		   JOptionPane.showMessageDialog(this, "Image Filtered Successfully!!!");
		   
		  	if(file!=null)
		  	{
		  		filePath=file.getPath();
		  	}
		  	if(filePath!=null)
		  	{
		  		path.setText("File path:-"+" "+filePath);
		  		showimage.setIcon(new ImageIcon(filePath));
		  		System.out.println("filePath1==="+filePath);
		  	} 
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}
}

public void showClusters()
{
	try
	  {   
		   File file=new File(appPath+"/FilteredImg.jpg");
		   BufferedImage filterImg=ImageIO.read(file);
		    
		   String[] dstpath={appPath+"/cluster1.jpg",appPath+"/cluster2.jpg",appPath+"/cluster3.jpg",appPath+"/cluster4.jpg"};
		   for(int i=0; i<dstpath.length;i++)
		   {
		    System.out.println("path= "+dstpath[i]);
		   }
		    
		   Kmeans kmeans=new Kmeans();
		   int k=4;
		   Kmeans.imagecluster(k,filterImg,dstpath);
		    
		   File file1=new File(appPath+"/cluster1.jpg");
		   File file2=new File(appPath+"/cluster2.jpg");
		   File file3=new File(appPath+"/cluster3.jpg");
		   File file4=new File(appPath+"/cluster4.jpg");
		   
		   String filepath1=null;
		   String filepath2=null;
		   String filepath3=null;
		   String filepath4=null;
		   
		   JOptionPane.showMessageDialog(this, "K-means Clustering Successfully!!!");
		   
		  	if(file1!=null && file2!=null && file3!=null && file4!=null)
		  	{
		  		filepath1=file1.getPath();
		  		filepath2=file2.getPath();
		  		filepath3=file3.getPath();
		  		filepath4=file4.getPath();
		  	}
		  	if(filepath1!=null && filepath2!=null && filepath3!=null && filepath4!=null)
		  	{
		  		path.setText("File path:-"+" "+appPath+"/KmeansResults");
		  		clustlbl1.setIcon(new ImageIcon(filepath1));
		  		clustlbl2.setIcon(new ImageIcon(filepath2));
		  		clustlbl3.setIcon(new ImageIcon(filepath3));
		  		clustlbl4.setIcon(new ImageIcon(filepath4));
		  	} 
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}
}

public void showFuzzyClusters()
{
	try
	{
		 
		   File file=new File(appPath+"/cluster4.jpg");
		   BufferedImage koutImg=ImageIO.read(file);
		    
		   String dstpath=appPath+"/fuzzyoutput.jpg";
		   
		    Fuzzy fuzzy = new Fuzzy(); 
		    //conditions/iterations
			int iter=9;
			Fuzzy.fuzzy_cluster(koutImg,dstpath, iter);
			
			file=new File(appPath+"/fuzzyoutput.jpg");
			
			JOptionPane.showMessageDialog(this, "Fuzzy Clustering Successfully!!!");
			if(file!=null)
		  	{
		  		filePath=file.getPath();
		  	}
		  	if(filePath!=null)
		  	{
		  		path.setText("File path:-"+" "+filePath);
		  		showimage.setIcon(new ImageIcon(filePath));
		  		System.out.println("filePath2==="+filePath);
		  	} 
				
			
	}
		catch(Exception e)
			{
			   JOptionPane.showMessageDialog(this, e.getMessage());
			   e.printStackTrace();
			}
	
}
public void showThresholdImage()
{
	try
	  {     
		   File file=new File(appPath+"/fuzzyoutput.jpg");
		   BufferedImage img=ImageIO.read(file);
		   
		   Thresholding th=new Thresholding();
 		  
		   BufferedImage Threshold=th.Threshold(img, 175);
		   
		   file=new File(appPath+"/ThresholdImg.jpg");
		   
		   ImageIO.write(Threshold, "jpg", file);
		   JOptionPane.showMessageDialog(this, "Thresholding Successfully!!!");
		  	if(file!=null)
		  	{
		  		filePath=file.getPath();
		  	}
		  	if(filePath!=null)
		  	{
		  		path.setText("File path:-"+" "+filePath);
		  		showimage.setIcon(new ImageIcon(filePath));
		  		System.out.println("filePath1==="+filePath);
		  	} 
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}
}
public void showTumorDetection()
{
	try
	  {     
		   String inputpath=appPath+"/ThresholdImg.jpg";
		   String outputpath=appPath+"/detectedtumor.jpg";
		   
		   TumorDetection td=new TumorDetection();
		  
		   td.detect(inputpath, outputpath);
		  	
		   File file=new File(appPath+"/detectedtumor.jpg");
		   /*JOptionPane.showMessageDialog(this, "Tumor Detection Successfully!!!");*/
		  	if(file!=null)
		  	{
		  		filePath=file.getPath();
		  	}
		  	if(filePath!=null)
		  	{
		  		path.setText("File path:-"+" "+filePath);
		  		showimage.setIcon(new ImageIcon(filePath));
		  		System.out.println("filePath1==="+filePath);
		  	} 
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}

}

public void areaOfTumor()
{
	
	 File file=new File(appPath+"/ThresholdImg.jpg"); 
	 PixelCount pixel=new PixelCount();
     int whitepixels=pixel.Pixel(file);
    
     double count1=(double)whitepixels;
    
     double doublearea=(Math.sqrt(count1))*0.264;
     tumorarea=(float)doublearea;
     
     TumorArea=String.valueOf(tumorarea);
     
     TextArea.setText("");
     TextArea.append("\n");
     TextArea.append("Area of Tumor=");
     TextArea.append("\n\n");
     TextArea.append(TumorArea+" mm");
     JOptionPane.showMessageDialog(this, "Tumor area= "+TumorArea);
     TextArea.setFont(new Font("Arial", Font.BOLD, 22));
     TextArea.append("\n");
     
}
public void stageOfTumor()
{
	
	Identification s=new Identification();
	stageofTumor=s.test1(tumorarea);
	TextArea.setText("");
    TextArea.append("\n");
    TextArea.append("Stage of Tumor=");
    TextArea.append("\n\n");
    TextArea.append(stageofTumor);
    JOptionPane.showMessageDialog(this, stageofTumor);
    TextArea.setFont(new Font("Arial", Font.BOLD, 22));
    TextArea.append("\n");
}

public void identification()
{
	
	  if(tumorarea>36)
	    {
	    	
		    identification="Brain Tumor Is Malignant ";
	        JOptionPane.showMessageDialog(this, identification);
	        TextArea.setText("");
	        TextArea.append("\n");
	        TextArea.append("Identification=");
	        TextArea.append("\n\n");
	        TextArea.append(identification);
	        TextArea.setFont(new Font("Arial", Font.BOLD, 22));
	        TextArea.append("\n");
	    }
	    else if(0.1 <tumorarea && tumorarea <36)
	    {
	    	identification="Brain Tumor Is Benign";
	    	JOptionPane.showMessageDialog(this, identification);
	    	TextArea.setText("");
	        TextArea.append("\n");
	        TextArea.append("Identification=");
	        TextArea.append("\n\n");
	        TextArea.append(identification);
	        TextArea.setFont(new Font("Arial", Font.BOLD, 22));
	        TextArea.append("\n");
	    }
	  
	    else 
	    {
	    	identification="Brain Image Is Healthy";
	    	JOptionPane.showMessageDialog(this, identification);
	    	TextArea.setText("");
	        TextArea.append("\n");
	        TextArea.append("Identification=");
	        TextArea.append("\n\n");
	        TextArea.append(identification);
	        TextArea.setFont(new Font("Arial", Font.BOLD, 22));
	        TextArea.append("\n");
	    }
	  
}

public void getPrediction()
{
	
	String stage=Identification.stage;
	
	if(stage.equalsIgnoreCase("Initial"))
	{
		TextArea.setText("");
	    TextArea.append("\n");
	    JOptionPane.showMessageDialog(this, "Disease Risk Level is Low");
	    TextArea.append("Disease Risk Level is Low");
	    TextArea.setFont(new Font("Arial", Font.BOLD, 22));
	    TextArea.append("\n");
	}
	else if(stage.equalsIgnoreCase("Critical"))
	{
		TextArea.setText("");
	    TextArea.append("\n");
	    TextArea.append("Disease Risk Level is High");
	    JOptionPane.showMessageDialog(this, "Disease Risk Level is High");
	    TextArea.setFont(new Font("Arial", Font.BOLD, 22));
	    TextArea.append("\n");
	}
	else
	{
		TextArea.setText("");
	    TextArea.append("\n");
	    TextArea.append("No Any Disease Risk");
	    JOptionPane.showMessageDialog(this, "No Any Disease Risk");
	    TextArea.setFont(new Font("Arial", Font.BOLD, 22));
	    TextArea.append("\n");
		
	}
}

public void report()
{
	try
	  {
		
		//PSNR calculation on Kmeans Cluster
		File f1=new File(appPath+"/"+patientName+"_Img.jpg");
		File f2=new File(appPath+"/cluster4.jpg");
		PSNR p1=new PSNR();
	    p1.calcPSNR(f1, f2);
	    
	    //PSNR calculation on Fuzzy Cluster
	  	f1=new File(appPath+"/"+patientName+"_Img.jpg");
	  	f2=new File(appPath+"/fuzzyoutput.jpg");
	  		
	  	PSNR p2=new PSNR();
	  	p2.calcPSNR(f1, f2);
	  	
	    //PSNR calculation on threshold Cluster
	  	f1=new File(appPath+"/"+patientName+"_Img.jpg");
	  	f2=new File(appPath+"/ThresholdImg.jpg");
	  		
	  	PSNR p3=new PSNR();
	  	p3.calcPSNR(f1, f2);
	  	
	  	/******************************************/
		
		String doc_email=DoctorLogin.doc_email;
		
		String report=appPath+"/report.pdf";
		
		PatientDao ud=new PatientDao();
		
		ArrayList<PatientBean> details = new ArrayList<PatientBean>();
		
		details=ud.patientDetails(patientName);
		
		PDF p=new PDF();
		
		PDF.generateReport(TumorArea, stageofTumor, details, report,identification,doc_email );
		
		JOptionPane.showMessageDialog(this, "Report Generated Successfully!!!");
		Desktop desktop = Desktop.getDesktop();
		File file = new File(report);
		desktop.open(file);
	  }
	catch(Exception e)
	{
	   JOptionPane.showMessageDialog(this, e.getMessage());
	   e.printStackTrace();
	}
	  
}
private void initComponents() 
   {

     jLabel1 = new javax.swing.JLabel();
     path = new javax.swing.JLabel();
     filename = new javax.swing.JLabel();
     showimage = new javax.swing.JLabel();
     patient_nameLabel = new javax.swing.JLabel();
     
     clustlbl1= new javax.swing.JLabel();
     clustlbl2= new javax.swing.JLabel();
     clustlbl3= new javax.swing.JLabel();
     clustlbl4= new javax.swing.JLabel();
     
     browse_btn = new javax.swing.JButton();
     original_btn = new javax.swing.JButton();
     gray_btn = new javax.swing.JButton(); 
     filter_btn = new javax.swing.JButton();
     kmeans_btn = new javax.swing.JButton();
     fuzzy_btn= new javax.swing.JButton();
     thresh_btn= new javax.swing.JButton();
     detect_btn= new javax.swing.JButton();
     area_btn= new javax.swing.JButton();
     stage_btn= new javax.swing.JButton();
     identification_btn= new javax.swing.JButton();
     prediction_btn= new javax.swing.JButton();
     report_btn= new javax.swing.JButton();
     exit_btn= new javax.swing.JButton();
     
     mainPanel= new javax.swing.JPanel();
     TextArea = new javax.swing.JTextArea();
     nameText = new javax.swing.JTextField();
     
     
     jScrollPane1 = new javax.swing.JScrollPane();
     jScrollPane2 = new javax.swing.JTextArea();	

     setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
     getContentPane().setLayout(null);
     
     setContentPane(new JLabel(new ImageIcon("images\\sky2.jpg")));

     
     jLabel1.setText("BRAIN TUMOR SEGMENTATION AND PREDICTION");
     jLabel1.setFont(new Font("Arial", Font.BOLD, 20));
     jLabel1.setForeground(Color.BLACK);
     getContentPane().add(jLabel1);
     jLabel1.setBounds(330, 30, 600, 16);

     /*jScrollPane1.setViewportView(showimage);
     getContentPane().add(jScrollPane1);
     jScrollPane1.setBounds(330, 70, 400, 375);*/
     
     mainPanel.revalidate();
     mainPanel.add(showimage);
     getContentPane().add(mainPanel);
     mainPanel.setBounds(330, 110, 400, 500);
     
     patient_nameLabel.setText("PATIENT NAME");
     patient_nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
     patient_nameLabel.setForeground(Color.BLACK);
     getContentPane().add(patient_nameLabel);
     patient_nameLabel.setBounds(150, 70, 150, 30);
     
     ArrayList<String> details = new ArrayList<String>();
     
     String[] list = null;
     
     PatientDao dao=new PatientDao();
     details=dao.getAllPatientDetails();
     System.out.println("Size of list= "+details.size());
     list = details.toArray(new String[details.size()]);
    
     patient_List = new JComboBox<>(list);
     patient_List.setBounds(330, 70, 400, 25);
     getContentPane().add(patient_List);
     /*nameText= new JTextField(20);
     nameText.setBounds(330, 70, 400, 25);
     getContentPane().add(nameText);*/
     
     browse_btn.setText("Select Image");
     //browse_btn.setForeground(new java.awt.Color(51, 51, 255));
     browse_btn.setForeground(Color.BLACK);
     getContentPane().add(browse_btn);
     browse_btn.setBounds(150, 110, 150, 30);
     browse_btn.addActionListener(new ActionListener() 
     {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			browse_btnActionPerformed(e);	
			
		}
	});
    
     original_btn.setText("Original Image");
     original_btn.setForeground(Color.BLACK);
     getContentPane().add(original_btn);
     original_btn.setBounds(150, 150, 150, 30);
     original_btn.addActionListener(new ActionListener() 
     {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			original_btnActionPerformed(e);	
			
		}
	});
     
    gray_btn.setText("Gray Image");
    gray_btn.setForeground(Color.BLACK);
    getContentPane().add(gray_btn);
    gray_btn.setBounds(150, 190, 150, 30);
    gray_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			gray_btnActionPerformed(e);	
			
		}
	});
     
    filter_btn.setText("Filter Image");
    filter_btn.setForeground(Color.BLACK);
    getContentPane().add(filter_btn);
    filter_btn.setBounds(150, 230, 150, 30);
    filter_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			filter_btnActionPerformed(e);	
			
		}
	});
    
    kmeans_btn.setText("K-Means Result");
    kmeans_btn.setForeground(Color.BLACK);
    getContentPane().add(kmeans_btn);
    kmeans_btn.setBounds(150, 270, 150, 30);
    kmeans_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			kmeans_btnActionPerformed(e);	
			mainPanel.removeAll();
			mainPanel.add(clustlbl1);
			mainPanel.revalidate();
			mainPanel.add(clustlbl2);
			mainPanel.revalidate();
			mainPanel.add(clustlbl3);
			mainPanel.revalidate();
			mainPanel.add(clustlbl4);
			mainPanel.revalidate();
			
		}
	});
    
    
    fuzzy_btn.setText("Fuzzy-C Result");
    fuzzy_btn.setForeground(Color.BLACK);
    getContentPane().add(fuzzy_btn);
    fuzzy_btn.setBounds(150,310, 150, 30);
    fuzzy_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			fuzzy_btnActionPerformed(e);	
			mainPanel.removeAll();
			mainPanel.updateUI();
			mainPanel.add(showimage);
			mainPanel.revalidate();
			getContentPane().add(mainPanel);
		}
	});
    
    
    thresh_btn.setText("Threshold Result");
    thresh_btn.setForeground(Color.BLACK);
    getContentPane().add(thresh_btn);
    thresh_btn.setBounds(150, 350, 150, 30);
    thresh_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			thresh_btnActionPerformed(e);	
			
		}
	});
    
    
    detect_btn.setText("Detect Tumor");
    detect_btn.setForeground(Color.BLACK);
    getContentPane().add(detect_btn);
    detect_btn.setBounds(150, 390, 150, 30);
    detect_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			detect_btnActionPerformed(e);	
			
		}
	});
    
    area_btn.setText("Area of Tumor");
    area_btn.setForeground(Color.BLACK);
    getContentPane().add(area_btn);
    area_btn.setBounds(150, 430, 150, 30);
    area_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			area_btnActionPerformed(e);	
			getContentPane().add(TextArea);
			TextArea.setBounds(750, 70, 300, 275);
			TextArea.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
			TextArea.setEditable(false);
		}
	});
    
    stage_btn.setText("Stage of Tumor");
    stage_btn.setForeground(Color.BLACK);
    getContentPane().add(stage_btn);
    stage_btn.setBounds(150, 470, 150, 30);
    stage_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			
			stage_btnActionPerformed(e);
			getContentPane().add(TextArea);
			TextArea.setBounds(750, 70, 300, 275);
			TextArea.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
			TextArea.setEditable(false);	
			
		}
	});
    
    identification_btn.setText("Identification");
    identification_btn.setForeground(Color.BLACK);
    getContentPane().add(identification_btn);
    identification_btn.setBounds(150, 510, 150, 30);
    identification_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			
			identification_btnActionPerformed(e);
			getContentPane().add(TextArea);
			TextArea.setBounds(750, 70, 300, 275);
			TextArea.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
			TextArea.setEditable(false);	
			
		}
	});
    
    prediction_btn.setText("Prediction");
    prediction_btn.setForeground(Color.BLACK);
    getContentPane().add(prediction_btn);
    prediction_btn.setBounds(150, 550, 150, 30);
    prediction_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			prediction_btnActionPerformed(e);
			getContentPane().add(TextArea);
			TextArea.setBounds(750, 70, 300, 275);
			TextArea.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
			
		}
	});
    
    report_btn.setText("Report");
    report_btn.setForeground(Color.BLACK);
    getContentPane().add(report_btn);
    report_btn.setBounds(150, 590, 150, 30);
    report_btn.addActionListener(new ActionListener() 
    {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			report_btnActionPerformed(e);	
			
		}
	});
   
   exit_btn.setText("Exit");
   exit_btn.setForeground(Color.BLACK);
   getContentPane().add(exit_btn);
   exit_btn.setBounds(150, 630, 150, 30);
   exit_btn.addActionListener(new ActionListener() 
   {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			//threshold_btnActionPerformed(e);
			File[] flist=dest.listFiles();
	     	   if(flist.length>0)
	     	   {
	     		   for(File f:flist)
	     		   {
	     			  f.delete(); 
	     		   }
	     	   }
			dispose();
		}
	});
   
   path.setFont(new Font("Arial", Font.BOLD, 16));
   path.setForeground(Color.BLACK);
   getContentPane().add(path);
   path.setBounds(20, 680, 1000, 30);
            
     pack();
     
  }  

  private void browse_btnActionPerformed(java.awt.event.ActionEvent evt) 
   { 
	   showImage();  
   } 
  private void original_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showOriginalImage();  
  } 
  private void gray_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showGrayImage();
  } 
  private void filter_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showFilterImage();
  }
  
  private void kmeans_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showClusters();
  }
  private void fuzzy_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showFuzzyClusters();
  }
  
  private void thresh_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showThresholdImage();
  }
  private void detect_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  showTumorDetection();
  }
  private void area_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  areaOfTumor();
  }
  private void stage_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	 stageOfTumor();
  }
  private void identification_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  identification();
  }
  private void prediction_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  getPrediction();
  }
  private void report_btnActionPerformed(java.awt.event.ActionEvent evt) 
  { 
	  report();
  }
 
  /* public static void main(String args[])
   {
	   try 
	    {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
       new ImageProcessing().setVisible(true);
   }*/


   // Variables declaration - do not modify 
   private javax.swing.JButton browse_btn,original_btn,gray_btn,filter_btn, kmeans_btn, fuzzy_btn,thresh_btn,detect_btn,area_btn, stage_btn,identification_btn,prediction_btn,report_btn,exit_btn;
   private javax.swing.JLabel patient_nameLabel,jLabel1;
   private javax.swing.JLabel showimage,clustlbl1,clustlbl2,clustlbl3,clustlbl4;
   private javax.swing.JLabel path,filename;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JPanel mainPanel;
   private javax.swing.JTextArea jScrollPane2,TextArea;
   private javax.swing.JTextField nameText;
   private JComboBox<String> patient_List;
   // End of variables declaration 

    private boolean check() 
    {
      if(filePath!=null) 
      {
       if(filePath.endsWith(".jpeg")||filePath.endsWith(".gif")||filePath.endsWith(".jpg")||filePath.endsWith(".JPEG")||filePath.endsWith(".GIF")||filePath.endsWith(".JPG")||filePath.endsWith(".png"))
        {
         return true;
        }
        return false;
       }
       return false;
    }
}