package com.project.algo;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class PixelCount {
   
  BufferedImage image;
   int width;
   int height;
   int a;
   int count = 0;
   
   public int Pixel(File input)
   {
      try {
    	  
        
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         
        
         
         int p = image.getRGB(0,0);
         int pixels[];
        
        	    pixels = new int[width * height];
        	    
        	    image.getRGB(0, 0, width, height, pixels, 0, width);

        	    for (int i = 0; i < pixels.length; i++) 
        	    {
        	       
        	        if (pixels[i] == 0xFFffffff) {
        	           
        	        	count++;
        	        }
        	   
        	
                  
        	    }
        	 
        	  
      } catch (Exception e) {}
      return count; 
   }
  
}
