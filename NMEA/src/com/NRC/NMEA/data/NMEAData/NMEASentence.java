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
public class NMEASentence {
    private static int numSentences = 0;
    public boolean containsTime = false;
    public UTCTime time, lastRecordedTime;
    public String sentance;
    public boolean isVoid;
    
    
    
    
    public NMEASentence(boolean hasTime){
        containsTime = hasTime;
        numSentences++;
    }
    public NMEASentence(boolean hasTime, boolean ivoid){
        isVoid = ivoid;
    }
    public static int getTheAwesome(){
        return numSentences;
    }
}