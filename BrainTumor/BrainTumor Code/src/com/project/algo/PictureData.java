package com.project.algo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class PictureData {

 
    //public PixelData _bw;
    public final int w, h, size; 
    public BufferedImage _srcImage = null; 
    //public static int index = 0;
  
    

    public PictureData( File source ) throws IOException 
    {
    	
    	System.out.println("SOurce=="+source);
    	System.out.println("SOurcepath=="+ source.getAbsolutePath());
    	
        System.out.println( "file: " + source.getAbsolutePath() );
        File f1=new File(source.getAbsolutePath());
        _srcImage = ImageIO.read(f1);

        System.out.println( "reading image data" );
        w = _srcImage.getWidth();
        h = _srcImage.getHeight();
        size = w * h;
        
        System.out.println(size);
        
        PixelData  Pixeldata=new  PixelData(size,4);
        
        PixelData.demo(_srcImage);
        PixelData.ReadImageInformation(_srcImage); 
        
        System.out.println( "image data stored" );

    }

   
    public BufferedImage createResultImage( int[] colors ) {
        BufferedImage resImage = new BufferedImage( w, h, _srcImage.getType() );

        int index = 0;
        for ( int i = 0; i <w; i++ )
            for ( int j = 0; j < h; j++, index++ ) {
            	System.out.println("loop");
            	
            	  System.out.println("index "+index);
                int gr =PixelData.getGroup( index );
                resImage.setRGB( i, j, colors[gr] );
            }
        return resImage;
    }

  public static void setGroup( int index, int val ) {
    	
	   System.out.println("index in setGroup"+index);
	   
    	PixelData.setGroup( index, val );
    }

    public final BufferedImage getSourceImage() {
        return _srcImage;
    }
    

    public int getSize() {
        return size;
    }
    

    public static int getValue( int index, int cecha ) {
    	
        return PixelData.value( index, cecha );
    }

    public static int getValueCount() 
    {
    	 
        return PixelData.getValueCount();
    }
}
