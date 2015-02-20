/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.data.NMEAData;

import com.NRC.NMEA.data.SatelliteInView;
import java.util.List;

/**
 *
 * @author Noah
 */
public class GSV extends NMEASentence{
    static int numOfGSVStatements;
    public List<SatelliteInView> satellitesInView;
    
    
    public GSV() {
        super(false);
    }
    
}
