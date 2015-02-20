/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.data.NMEAData;

import com.NRC.NMEA.data.Coordinate;
import com.NRC.NMEA.data.UTCTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Noah
 */
public class DataPointInTime implements Iterable<NMEASentence> {
    public Set<NMEASentence> data = new HashSet();
    public UTCTime time;
    public Coordinate coordinate = new Coordinate();
    public double elevation;
    
    
    
    public void add(NMEASentence par1){
        data.add(par1);
        if(par1 instanceof GGA){
            coordinate = ((GGA)par1).position;
            this.elevation = ((GGA)par1).elevation;
        }
    }

    @Override
    public Iterator<NMEASentence> iterator() {
       return data.iterator();
    }
}
