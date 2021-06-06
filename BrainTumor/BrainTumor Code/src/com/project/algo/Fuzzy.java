

package com.project.algo;

import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException;
import java.util.Arrays; 

import javax.imageio.ImageIO; 
public class Fuzzy 
{ 
    static BufferedImage original; 
    BufferedImage result; 
    Cluster[] clusters; 
    public static final int MODE_CONTINUOUS = 1; 
    public static final int MODE_ITERATIVE = 2; 
    static int  max=1;
    static   int min=1000000;  
   
   
    public static BufferedImage fuzzy_cluster(BufferedImage src ,String output, int iter)
    {
    	BufferedImage outImage=null;
         String m ="-i"; 
         int mode = 1; 
         if (m.equals("-i")) 
         { 
            mode = MODE_ITERATIVE; 
         } 
         else if (m.equals("-c")) 
         { 
            mode = MODE_CONTINUOUS; 
         } 
         
         Fuzzy fuzzy = new Fuzzy(); 
        
         for(int i=0;i<iter;i++)
         {
         
          outImage = fuzzy.fcalculate(src,i+1,mode); 
         }
         saveImage(output, outImage); 
		return outImage;
    }
    
    
    public Fuzzy()
    {    
    	U_MembershipMatrix u=new U_MembershipMatrix(1, 1);
    } 
     
    public BufferedImage fcalculate(BufferedImage image,  
                                            int k, int mode) 
    { 
        long start = System.currentTimeMillis(); 
        int w = image.getWidth(); 
        int h = image.getHeight(); 
        // create clusters 
        clusters = createFClusters(image,k); 
        // create cluster lookup table 
        int[] lut = new int[w*h]; 
        Arrays.fill(lut, -1); 
         
       
        boolean pixelChangedCluster = true; 
      
        int loops = 0; 
        while (pixelChangedCluster) { 
            pixelChangedCluster = false; 
            loops++; 
            for (int y=0;y<h;y++) { 
                for (int x=0;x<w;x++) { 
                    int pixel = image.getRGB(x, y); 
                    Cluster cluster = findFMinimalCluster(pixel); 
                    if (lut[w*y+x]!=cluster.getId()) { 
                       
                        if (mode==MODE_CONTINUOUS) { 
                            if (lut[w*y+x]!=-1) { 
                               
                                clusters[lut[w*y+x]].removePixel( 
                                                            pixel); 
                            } 
                            
                            cluster.addPixel(pixel); 
                        } 
                       
                        pixelChangedCluster = true; 
                     
                       
                        lut[w*y+x] = cluster.getId(); 
                    } 
                } 
            } 
            if (mode==MODE_ITERATIVE) { 
                // update clusters 
                for (int i=0;i<clusters.length;i++) { 
                    clusters[i].clear(); 
                } 
                for (int y=0;y<h;y++) { 
                    for (int x=0;x<w;x++) { 
                        int clusterId = lut[w*y+x]; 
                        // add pixels to cluster 
                        clusters[clusterId].addPixel( 
                                            image.getRGB(x, y)); 
                    } 
                } 
            } 
             
        } 
        
        // create result image 
        BufferedImage result = new BufferedImage(w, h,  
                                    BufferedImage.TYPE_INT_RGB); 
        for (int y=0;y<h;y++) { 
            for (int x=0;x<w;x++) { 
                int clusterId = lut[w*y+x]; 
              
                result.setRGB(x, y, clusters[clusterId].getRGB()); 
            } 
            
        } 
        long end = System.currentTimeMillis(); 
       /* System.out.println("Clustered to "+k 
                            + " clusters in "+loops 
                            +" loops in "+(end-start)+" ms."); */
        return result; 
    } 
     
    public Cluster[] createFClusters(BufferedImage image, int k) { 
       
        Cluster[] result = new Cluster[k]; 
        int x = 0; int y = 0; 
        int dx = image.getWidth()/k; 
        int dy = image.getHeight()/k; 
        for (int i=0;i<k;i++) { 
            result[i] = new Cluster(i,image.getRGB(x, y)); 
            x+=dx; y+=dy; 
        } 
        return result; 
    } 
     
    public Cluster findFMinimalCluster(int rgb) { 
        Cluster cluster = null; 
        int min = Integer.MAX_VALUE; 
        for (int i=0;i<clusters.length;i++) { 
            int distance = clusters[i].distance(rgb); 
          
            if (distance<min) { 
                min = distance; 
                cluster = clusters[i]; 
            } 
        } 
        return cluster; 
    } 
     
    public static void saveImage(String filename,  
            BufferedImage image) { 
        File file = new File(filename); 
        try { 
            ImageIO.write(image, "png", file); 
        } catch (Exception e) { 
        	
           /* System.out.println(e.toString()+" Image '"+filename 
                                +"' saving failed."); */
        } 
    } 
     
  
    class Cluster { 
        int id; 
        int pixelCount; 
        int red; 
        int green; 
        int blue; 
        int reds; 
        int greens; 
        int blues; 
         
        public Cluster(int id, int rgb) 
        { 
        	 int r = rgb>>16&0x000000FF;  
             int g = rgb>> 8&0x000000FF;  
             int b = rgb>> 0&0x000000FF;  
            red = r; 
            green = g; 
            blue = b; 
            this.id = id; 
         
            addPixel(rgb); 
        } 
         
        public void clear() { 
            red = 0; 
            green = 0; 
            blue = 0; 
            reds = 0; 
            greens = 0; 
            blues = 0; 
            pixelCount = 0; 
        } 
         
        int getId() { 
            return id; 
        } 
         
        int getRGB() { 
            int r = reds / pixelCount; 
            int g = greens / pixelCount; 
            int b = blues / pixelCount; 
           
           return 0xff000000|r<<16|g<<8|b; 
           // return ((255<<24) & 0xFF000000)|(r<<16) |g<<8|b;
        } 
        void addPixel(int color) { 
            
            int r = color>>16&0x000000FF;  
            int g = color>> 8&0x000000FF;  
            int b = color>> 0&0x000000FF;  
            reds+=r; 
            greens+=g; 
            blues+=b; 
            pixelCount++; 
            red   = reds/pixelCount; 
            green = greens/pixelCount; 
            blue  = blues/pixelCount; 
   int        meanValues = ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
  
   
    if(meanValues>max)
    {
        max=meanValues;
    }
    if(meanValues<min)
    {
        min=meanValues;
    }
            
        } 
         
        void removePixel(int color) { 
           
        	 int r = color>>16&0x000000FF;  
             int g = color>> 8&0x000000FF;  
             int b = color>> 0&0x000000FF;  
            reds-=r; 
            greens-=g; 
            blues-=b; 
            pixelCount--; 
            red   = reds/pixelCount; 
            green = greens/pixelCount; 
            blue  = blues/pixelCount; 
        } 
         
        int distance(int color) { 
            
        	 int r = color>>16&0x000000FF;  
             int g = color>> 8&0x000000FF;  
             int b = color>> 0&0x000000FF;  
            int rx = Math.abs(red-r); 
            int gx = Math.abs(green-g); 
            int bx = Math.abs(blue-b); 
            int d = (rx+gx+bx) / 3; 
         //   System.out.println(d);
            return d; 
        } 
    } 
     
} 

