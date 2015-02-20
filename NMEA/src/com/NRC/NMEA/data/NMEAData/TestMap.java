/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.data.NMEAData;

import com.NRC.NMEA.analyze.PositionDeviation;
import com.NRC.NMEA.data.UTCTime;
import com.NRC.NMEA.nmea.SentenceParser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author Noah
 */
public class TestMap implements Iterable<DataPointInTime>{
    public Map<UTCTime, DataPointInTime> timeMap = new HashMap();
    SentenceParser parser = new SentenceParser();
    private UTCTime LRC = null;
    public String name;
    public PositionDeviation dev;
    
    
    
    
    
    public TestMap(String desc){
        name = desc;
    }
    public void translateAndAdd(String sentence){
        NMEASentence dataPoint = parser.parseSentence(sentence); // can be null :[
        if(dataPoint.isVoid){
            com.NRC.NMEA.debug.Debug.debugOut("<err--> void data point, discarding data point");
        }
       if(dataPoint.containsTime){
           if(timeMap.containsKey(dataPoint.time)){
               DataPointInTime d = timeMap.get(dataPoint.time);
               d.add(dataPoint);
               
           }else{
               DataPointInTime d = new DataPointInTime();
               d.add(dataPoint);
               timeMap.put(dataPoint.time, d);
              
           }
            LRC = dataPoint.time;
       }else{
           try{
           dataPoint.lastRecordedTime = LRC;
           DataPointInTime d = timeMap.get(LRC);
           d.add(dataPoint);
           }catch(NullPointerException e){
               com.NRC.NMEA.debug.Debug.debugOut("<err--> no last recorded time, discarding data point");
           }
       }
    }
    public void analyze(){
        dev = new PositionDeviation(this);
    }

    @Override
    public Iterator<DataPointInTime> iterator() {
        return timeMap.values().iterator();
    }
}