/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.data.NMEAData;

import com.NRC.NMEA.data.Coordinate;
import com.NRC.NMEA.data.UTCDate;


/**
 *
 * @author Noah
 */
public class RMC extends NMEASentence {
    public String status;
    public Coordinate position;
    public double speed;
    public double course;
    public UTCDate date;
    public double magVar;
    
    
    public RMC() {
        super(true);
    }
    
}
