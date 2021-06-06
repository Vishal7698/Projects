package com.project.algo;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import com.project.bean.PSNRBean;


public class PSNR {

	public static double psnrval=0;
	public static double snrval=0;
	  public static double log10(double x) {
	    return Math.log(x)/Math.log(10);
	  }

	  public static PSNRBean calcPSNR(File f,File f2) {
		  
		  PSNRBean psnrBean = new PSNRBean();
	    int     nrows, ncols;
	    int     img1[][], img2[][];
	    double  peak, signal, noise, mse, psnr,snr;

	    /*if (args.length != 4) {
	      System.out.println("Usage: Psnr <nrows> <ncols> <img1> <img2>");
	      return;
	    }*/
	    //File f = new File("D:/Shilpa/ProjectGuru/workspace1/secretshare/input/Jellyfish.jpg");
	    //File f2 = new File("D:/Shilpa/ProjectGuru/workspace1/secretshare/output/Jellyfish1.jpg");
	    try {
			BufferedImage buf1 = ImageIO.read(f);
			int width =buf1.getWidth();
			int height = buf1.getHeight();
			BufferedImage buf2 = ImageIO.read(f2);
			nrows = buf2.getWidth();
			ncols = buf2.getHeight();
		
	    
	    img1 = new int[nrows][ncols];
	    img2 = new int[nrows][ncols];
	    int e=24;
	    ArrayIO.readByteArray(f.getAbsolutePath(), img1, width, height);
	    ArrayIO.readByteArray(f2.getAbsolutePath(), img2, nrows, ncols);
	    System.out.println("Width:"+width+"\t"+nrows);
	    System.out.println("height:"+height+"\t"+ncols);
	    signal = noise = peak = 0;
	    for (int i=0; i<width; i++) {
	      for (int j=0; j<height; j++) {
	        signal += img1[i][j] * img1[i][j];
	        noise += (img1[i][j] - img2[i][j]) * (img1[i][j] - img2[i][j]);
	        if (peak < img1[i][j])
	          peak = img1[i][j];
	      } 
	    }
	    
	    mse = noise/(width*height); // Mean square error
	    snr = 10*log10(signal/noise)+e;
	    psnr=10*log10(peak*peak/mse)+e;
	   
	    snrval=10*log10(signal/noise)+e;
	    psnrval=10*log10((peak*peak)/mse)+e;
	    System.out.println("**********************************");
	    System.out.println("MSE: " + mse);
	    System.out.println("SNR: " + snrval);
	    System.out.println("PSNR(max=" + peak + "): "+ psnrval);
	    System.out.println("**********************************");
	    psnrBean.setF1(f);
	    psnrBean.setF2(f2);
	    psnrBean.setMse(mse);
	    psnrBean.setNoise(noise);
	    psnrBean.setSignal(signal);
	    psnrBean.setPeak(peak);
	    psnrBean.setSnr(snr);
	    psnrBean.setPsnr(psnr);
	   
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return psnrBean;
	    
	  }
	  
	}