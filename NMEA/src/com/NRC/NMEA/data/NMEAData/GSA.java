/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.data.NMEAData;

import com.NRC.NMEA.data.UTCTime;

/**
 *
 * @author Noah
 */
public class GSA extends NMEASentence{
    public UTCTime lastRecordedTime;
    public GSA() {
        super(false);
    }
    
}
