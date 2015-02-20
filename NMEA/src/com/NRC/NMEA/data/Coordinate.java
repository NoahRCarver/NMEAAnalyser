package com.NRC.NMEA.data;

import java.util.Objects;


/**
 *
 * @author noah
 */
public class Coordinate {

    private double mLatitude;
    private Hemisphere mLatitudeHemisphere;
    private double mLongitude;
    private Hemisphere mLongitudeHemisphere;

    //A default Coordinate for initializing values.
    public Coordinate(){
        mLatitude = -1;
        mLatitudeHemisphere = Hemisphere.NORTH;
        mLongitude = -1;
        mLongitudeHemisphere = Hemisphere.WEST;
    }

    public Coordinate(double latitude, Hemisphere latHemisphere, double longitude, Hemisphere lonHemisphere){
        mLatitude = latitude;
        mLatitudeHemisphere = latHemisphere;
        mLongitude = longitude;
        mLongitudeHemisphere = lonHemisphere;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public Hemisphere getLatitudeHemisphere() {
        return mLatitudeHemisphere;
    }

    public void setLatitudeHemisphere(Hemisphere hemisphere) {
        mLatitudeHemisphere = hemisphere;
    }

    public Hemisphere getLongitudeHemisphere() {
        return mLongitudeHemisphere;
    }
    public void setLongitudeHemisphere(Hemisphere hemisphere) {
        mLongitudeHemisphere = hemisphere;
    }
    
    public double getLongitudeDistance(Coordinate c){
        if(c != null){
         return mLongitude - c.getLongitude();
        }
        return -1;
    }
    public double getLatitudeDistance(Coordinate c){
        if(c != null){
         return mLatitude - c.getLatitude();
        }
        return -1;
    }
    
    @Override
    public String toString(){
        return mLatitude + " " +  mLatitudeHemisphere + ", " + mLongitude + " " +  mLongitudeHemisphere;
    }
    
    @Override
    public boolean equals(Object c){
        return((c instanceof Coordinate)&&(((Coordinate)c).mLatitude == this.mLatitude)&&(((Coordinate)c).mLongitude == this.mLongitude)&&(((Coordinate)c).mLongitudeHemisphere == this.mLongitudeHemisphere)&&(((Coordinate)c).mLatitudeHemisphere == this.mLatitudeHemisphere));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.mLatitude) ^ (Double.doubleToLongBits(this.mLatitude) >>> 32));
        hash = 43 * hash + Objects.hashCode(this.mLatitudeHemisphere);
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.mLongitude) ^ (Double.doubleToLongBits(this.mLongitude) >>> 32));
        hash = 43 * hash + Objects.hashCode(this.mLongitudeHemisphere);
        return hash;
    }


}