package com.project.algo;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class TumorDetection 
{

	int cnt=0;
	public void detect(String inputpath, String outputpath)
	
	{
		int count=0;
		//String path="C:/Users/admin/Pictures/BrainMRIImages/Image4.jpg";
		
		
		String fileName="img";
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    Mat mRgba = Highgui.imread(inputpath,Highgui.CV_LOAD_IMAGE_COLOR);
	            
	    Mat imageGray = new Mat();
	    Mat imageCny = new Mat();
	    Mat matBlack = new Mat();
	    
	    Imgproc.cvtColor(mRgba, imageGray, Imgproc.COLOR_BGR2GRAY);
	    
	    Imgproc.threshold(imageGray, matBlack, 180, 255, Imgproc.THRESH_BINARY);
	    		 
	    Imgproc.Canny(matBlack, imageCny, 10, 100, 3, true);
	    
	    ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	    Mat hierarchy = new Mat();
	    Imgproc.findContours(matBlack, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
	    
	    
	    hierarchy.release();
	    int t=0;
	   
	    float sum = 0;
	    for ( int contourIdx=0; contourIdx < contours.size(); contourIdx++ )
	    {
	        // Minimum size allowed for consideration
	        MatOfPoint2f approxCurve = new MatOfPoint2f();
	        MatOfPoint2f contour2f = new MatOfPoint2f( contours.get(contourIdx).toArray() );
	        //Processing on mMOP2f1 which is in type MatOfPoint2f
	        double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
	        Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);

	        //Convert back to MatOfPoint
	        MatOfPoint points = new MatOfPoint( approxCurve.toArray() );

	        // Get bounding rect of contour
	        Rect rect = Imgproc.boundingRect(points);

	        Core.rectangle(matBlack, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0, 255), 3);
	        /*Core.rectangle(matBlack,       //where to draw the box 
	   	         new Point(rect.x, rect.y),   //bottom left 
	   	         new Point(rect.x + rect.width, rect.y + rect.height),  //top right  
	   	         new Scalar(0, 0, 255), 
	   	         3);*/
	        //Calculate rectangle length and width
	        
	        
	       if(t==contourIdx) 
	       {
	    	   float h= rect.height;
	    	   float w=rect.width;
	    	   System.out.print("[");
	    	   System.out.print(h+"   ");
	    	   System.out.print(",");
	    	   System.out.print("   "+w);
	    	   System.out.print("]");
	    	   System.out.println();
	    	   t=t+2;
	    	   cnt++;
	       }
	      
	    }
	    
	    System.out.println("Count= "+cnt);
	    Highgui.imwrite(outputpath, matBlack);
		
	}
	public static void main(String args[])
	{
		
		
	 
	}

	
	  
	

}
