package com.project.algo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.bean.PatientBean;

public class PDF {
	
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
	public static void generateReport(String TumorArea, String stageofTumor, ArrayList<PatientBean> details, String filepath, String identification, String doc_email)
	{
	      Document document = new Document();
	      try
	      {
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filepath));
	         document.open();
	         
	         ArrayList<PatientBean> userdetails=details;
	         
	         PatientBean bean=new PatientBean();
	         
	         for(int i=0;i<userdetails.size();i++)
	         {
	        	 bean=userdetails.get(i);
	         }
	         
	         Font f = new Font();
	         f.setStyle(Font.BOLD);
	         f.setSize(4);
	         
	         Paragraph preface = new Paragraph();

	       //*************************************
	         Font ff = new Font();
	         ff.setStyle(Font.BOLD);
	         ff.setSize(14);
	         Paragraph p2 = new Paragraph(" Patient Report ", ff);
	         p2.setAlignment(Element.ALIGN_CENTER);
	         document.add(p2);
	         document.add(new Phrase("\n"));
	         //document.add("");
	         List list = new List(true, false, 10);
	         list.add(new ListItem("Patient Name:           "+bean.getPatient_name()));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Patient Address:       "+bean.getAddress()));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Patient Age:              "+bean.getAge()));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Patient Email:           "+bean.getEmail()));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Patient Mob:             "+bean.getMobNo()));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Doc Email:                "+doc_email));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Tumor Area:             "+TumorArea+" mm"));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Tumor Stage:            "+stageofTumor));
	         addEmptyLine(preface,1);
	         list.add(new ListItem("Identification:             "+identification));
	         addEmptyLine(preface,1);
	         document.add(list);
	         
	         document.close();
	         writer.close();
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	   }
	
	 public  static void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	    }
	public static void main(String[] args)
	   {
	      Document document = new Document();
	      try
	      {
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D://HelloWorld.pdf"));
	         document.open();
	         document.add(new Paragraph("A Hello World PDF document."));
	         
	         List list = new List(true, false, 10);
	         list.add(new ListItem("First point"));
	         list.add(new ListItem("Second point"));
	         list.add(new ListItem("Third point"));
	         document.add(list);
	         document.close();
	         writer.close();
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	   }
}
