/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.analyze;

import com.NRC.NMEA.data.Coordinate;
import com.NRC.NMEA.data.Hemisphere;
import com.NRC.NMEA.data.NMEAData.DataPointInTime;
import com.NRC.NMEA.data.NMEAData.TestMap;

/**
 *
 * @author Noah
 */
public class PositionDeviation {
    public Coordinate center;
    public double meanElevation;
    public PseudoCollectionSet<Coordinate> coordinates = new PseudoCollectionSet<>();
    public PseudoCollectionSet<Double> elevations = new PseudoCollectionSet<>();
    public double latitudeStandardDeviation;
    public double longitudeStandardDeviation;
    public double verticalStandardDeviation;
    public int PosTestSize = 0;
    public int ETestSize = 0;    
    
    public PositionDeviation(TestMap test){
        for(DataPointInTime d : test){
            coordinates.add(d.coordinate);
            elevations.add(d.elevation);
            
        }
        int size = coordinates.size();
        int eSize = elevations.size();
        double latSum = 0;
        double longSum = 0;
        double elevSum = 0;
        
        for(Object d : elevations){
            if(((Double)d)!=0.0){
            elevSum += (elevations.getMultiples((Double)d) * (Double)d);
            }else{
                eSize -= elevations.getMultiples(d);
            }
        }
      
        for(Object c : coordinates){
            if(c != null && ((Coordinate)c).getLatitude()!=-1 && ((Coordinate)c).getLongitude()!=-1){
               
                latSum += ((Coordinate)c).getLatitude() * coordinates.getMultiples(c);
            
                longSum += ((Coordinate)c).getLongitude() * coordinates.getMultiples(c);
            }else{
                size-=coordinates.getMultiples(c);
            }
        }
        
        PosTestSize = size;
        ETestSize = eSize;
        center = new Coordinate((latSum/size),Hemisphere.NORTH,longSum/size,Hemisphere.WEST);
        meanElevation = elevSum/eSize;
        
        double hDevSum = 0;
        for(Object d : elevations){
            if(((Double)d) != 0.0)
           hDevSum += Math.abs(meanElevation-(Double)d);
        }
        verticalStandardDeviation = Math.sqrt(hDevSum/eSize);
        
        
        double latDevSum = 0;
        double longDevSum = 0;
        for(Object c : coordinates){
            if(c != null && ((Coordinate)c).getLatitude()!=-1 && ((Coordinate)c).getLongitude()!=-1){
            
           longDevSum += Math.abs(center.getLongitudeDistance(((Coordinate)c)));
           latDevSum += Math.abs(center.getLatitudeDistance(((Coordinate)c)));
            
            }
            
        }

        longitudeStandardDeviation = Math.sqrt(longDevSum/size);
         latitudeStandardDeviation = Math.sqrt(latDevSum/size);
    }
    
   
}