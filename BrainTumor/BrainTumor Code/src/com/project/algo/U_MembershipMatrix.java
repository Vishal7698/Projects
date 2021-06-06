package com.project.algo;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class U_MembershipMatrix {

    
    private final int c;  
    private final int _nElem; 
    private float _error;  
    private FloatBuffer _data; 
   
    public U_MembershipMatrix( int c, int nElem ) {
        this.c = c;
        _nElem = nElem;


        _data = ByteBuffer.allocateDirect( nElem * c * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();

        fillRandom();
    }

  
    public void preUpdate() {
        _error = Float.MIN_VALUE;
    }

    public void updateColumn( int col, float[] distArr, int m ) {
        boolean zero = false;
        for ( int i = 0; i < distArr.length && !zero; i++ ) 
            if ( distArr[i] == 0 ) { 
                zero = true; 
                for ( int j = 0; j < distArr.length; j++ )
                    setData( j, col, ( i == j ) ? 1 : 0 );
            }
        if ( !zero )
            for ( int i = 0; i < distArr.length; i++ ) {
                float u = newMembership( distArr, i, m ); 
                float prev = get( i, col ); 
                setData( i, col, u );
                float e = Math.abs( u - prev ); 
                if ( e > _error )
                    _error = e;
            }
    }

   
    public boolean isAccurateEnough( float tolerance ) {
        System.out.println( "e: " + _error );
        return _error < tolerance;
    }

  
    public void deffuzify( PictureData data ) {
        for ( int i = 0; i < _nElem; i++ ) {
            int maxInd = 0;
            for ( int j = 1; j < c; j++ )
                if ( get( j, i ) > get( maxInd, i ) ) 
                    maxInd = j;
         
        }
    }

    public float get( int row, int col ) {
        return _data.get( _nElem * row + col );
    }

    
    private void fillRandom() { 
        for ( int i = 0, j = 0; i < _nElem; i++, j++ ) { 
            if ( j >= c )
                j = 0;
            setData( j, i, 1 );
        }
    }

   
    private float newMembership( float[] distArr, int setID, int m ) {
        float sum = 0;
        float base = distArr[setID];
        for ( int i = 0; i < distArr.length; i++ ) {
            float a = base / distArr[i];
            sum += Math.pow( a, 2 / ( m - 1 ) );
        }
        return 1 / sum;
    }

    private void setData( int row, int col, float data ) {
        _data.put( _nElem * row + col, data );
    }
}
