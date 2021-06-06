package com.project.algo;

import java.awt.image.BufferedImage;

public class PixelData {

   
	public static int[] _values;
   
    public static byte[] _groups; 
    public static int in, przes; 
    public static int iloscCech; 
   
    public PixelData( int size, int iloscCech ) {
      
    	int s = size * iloscCech / 4 + 3; 
        _values = new int[s];
        _groups = new byte[size];
        this.iloscCech = iloscCech;
        in = przes = 0;
    }

  
    public static float[] y = {   0.299f,  0.587f,   0.114f };
    public static float[] cb = { -0.169f, -0.331f,   0.5f };
    public static float[] cr = {  0.5f,   -0.419f,  -0.081f };
    public static float[] cg = { -0.169f,  0.5f,    -0.331f };

   
    public static void demo(BufferedImage src)
    {
    	System.out.println("In demo");
    	System.out.println(src.getHeight());
    	
    }
    public static void ReadImageInformation( BufferedImage srcImage )
    {
    	
        System.out.println("in method");
        
    	int rMask = 0x00ff0000;
        int gMask = 0x0000ff00;
        int bMask = 0x000000ff;

        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        for ( int i = 0; i < w; i++ )
            for ( int j = 0; j < h; j++ ) 
            {
                int rgb = srcImage.getRGB( i, j );
                int r = ( rgb & rMask ) >> 16;
                int g = ( rgb & gMask ) >> 8;
                int b = rgb & bMask;

                setNextValue( convertColor( r, g, b ) ); 
            }
    }

    public static int[] convertColor( int r, int g, int b ) {
        float Y = ( float ) ( y[0] * r + y[1] * g + y[2] * b );
        float Cb = ( float ) ( cb[0] * r + cb[1] * g + cb[2] * b );
        float Cr = ( float ) ( cr[0] * r + cr[1] * g + cr[2] * b );
        float Cg = ( float ) ( cg[0] * r + cg[1] * g + cg[2] * b );


        int[] re = {
            ( int ) Math.max( 0, Y ),
            ( int ) Math.max( 0, Cb ),
            ( int ) Math.max( 0, Cr ),
            ( int ) Math.max( 0, Cg ),
            ( int ) Math.random() * 255
        };
        return re;
    }
    
    public static void setNextValue( int[] val ) {
        for ( int i = 0; i < iloscCech; i++ ) {
            if ( przes == 4 ) {
                przes = 0; 
                _values[++in] = 0;
            }
            int v = val[i] & 0xff;
            v = v << ( przes * 8 );  
            _values[in] |= v;
            przes++;
        }
    }

    
    public static int value( int index, int cecha ) {

        int i = ( index ) * iloscCech + cecha; 
        int a = _values[i / 4]; 
        int move = ( i % 4 ) * 8; 
        int mask = 0xff << move;
        a = a & mask;
        return a >> move;
    }

    public static int getValueCount() { 
    	
        return iloscCech;
    }

 public static void setGroup( int index, int val ) {
       
    	_groups[index] =(byte) val;
    	System.out.println("index set group "+index);
        
    }

 public static int getGroup( int index ) {
	
        return _groups[index];
    }
}
