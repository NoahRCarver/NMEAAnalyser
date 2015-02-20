package com.NRC.NMEA.data.NMEAData;

import com.NRC.NMEA.data.Coordinate;

public class GGA extends NMEASentence {
    public Coordinate position;
    public int fixQuality;
    public int numOfSats;
    public double HDOP;
    public double elevation;
    
	public GGA(){
            super(true);
        }
	
}
