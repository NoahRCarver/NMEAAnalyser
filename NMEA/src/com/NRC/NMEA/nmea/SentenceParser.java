package com.NRC.NMEA.nmea;


import com.NRC.NMEA.data.*;
import com.NRC.NMEA.data.NMEAData.GGA;
import com.NRC.NMEA.data.NMEAData.GSA;
import com.NRC.NMEA.data.NMEAData.GSV;
import com.NRC.NMEA.data.NMEAData.NMEASentence;
import com.NRC.NMEA.data.NMEAData.RMC;
import com.NRC.NMEA.debug.Debug;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Noah Carver
 */
public class SentenceParser {

    
   
    private int mNumberOfGSVSentences = 0;
    private int mCurrentGSVSentence = 0;
    private int mNumberParsedSatellites = 0;
    private List<SatelliteInView> mSatellitesInView;

    public SentenceParser(){
    }

    public String[] processData(String data){
        String[] sentences = null;
        if (data.contains("$")){
            //Trim any data leading $
            if (data.indexOf("$") > 0){
                data = data.substring(data.indexOf("$"));
            }

            //Parse out the sentences on $
            sentences = data.split("[$]");
            for (String sentence : sentences){
                if (!sentence.isEmpty()){
                    parseSentence("$" + sentence);
                }
            }
        }
        return sentences;
    }

    public NMEASentence parseSentence(String sentence) {
        NMEASentence ret = new NMEASentence(false, true);
        sentence = sentence.trim();
        String[] splitMessage = sentence.split(",");
        switch (splitMessage[0]) {
            case "$GPGSA":
                
                    Debug.debugOut("GSA message found");
                    ret = parseGsa(sentence);
                   break;
            case "$GPRMC":
                
                    Debug.debugOut("RMC message found");
                    ret = parseRmc(sentence);
                   break;
            case "$GPGGA":
                
                    Debug.debugOut("GGA message found");
                    ret = parseGga(sentence);
                    
               break;
            case "$GPGLL":
                
                    Debug.debugOut("GLL message found");
                    ret = parseGll(sentence);
                
                   break;
            case "$GPGSV":
               
                    Debug.debugOut("GSV message found");
                    ret = parseGsv(sentence);
                
                   break;
            case "$GPVTG":
                
                    Debug.debugOut("VTG message found");
                    ret = parseVTG(sentence);
                
               break;
        }
        return ret;
    }

    /**
     * Parse the $GPGGA message
     * @param message
     * @return GGA ret
     */
    private GGA parseGga(String message){
       
        GGA ret = new GGA();
       try{
            Debug.debugOut("GGA Checksum Passed");
            
            String[] values = message.split(",");

            //Set GGA UTC time
            UTCTime time = parseUtcTime(values[1]);
            ret.time = time;
            Debug.debugOut("UTC");

            //Set GGA Coordinate
            Coordinate ggaCoordinate = parseCoordinate(values[2], values[3], values[4], values[5]);
            ret.position = ggaCoordinate;
            Debug.debugOut("Coordinate");

            //Set fix quality
            String fixQuality = values[6];
            if (!fixQuality.isEmpty()){
                ret.fixQuality = Integer.parseInt(fixQuality);
            }
            Debug.debugOut("Fix");

            //Set number of stellites in use
            String numberOfSatelites = values[7];
            if (!numberOfSatelites.isEmpty()){
                ret.numOfSats = Integer.parseInt(numberOfSatelites);
            }
            Debug.debugOut("Num Sat");

            //Set HDOP
            String hdop = values[8];
            if (!hdop.isEmpty()){
                ret.HDOP = Double.parseDouble(hdop);
            }
            Debug.debugOut("HDOP");

            //Set altitude
            String altitude = values[9];
            if (!altitude.isEmpty()){
                ret.elevation = Double.parseDouble(altitude);
            }
            Debug.debugOut("Altitude");
            Debug.debugOut("GGA message parsed");
          
       }catch(Exception e){
           
       }
        return ret;
    }

    /**
     * Parse the $GPRMC message
     * @param message
     */
    private RMC parseRmc(String message){
      
        
            String[] values = message.split(",");
            
            RMC ret = new RMC();
            
            try{
            //Set UTC Time
            String utcTime = values[1];
            if (!utcTime.isEmpty()){
                UTCTime time = parseUtcTime(utcTime);
                ret.time = time;
            }

            //Set status
            String status = values[2];
            if (!status.isEmpty()){
                ret.status = status;
            }

            //Set coordinate
            Coordinate coordinate = parseCoordinate(values[3], values[4], values[5], values[6]);
            ret.position = coordinate;

            //Set speed over ground
            String speed = values[7];
            if (!speed.isEmpty()){
                ret.speed = Double.parseDouble(speed);
            }

            //Set course over ground
            String course = values[8];
            if (!course.isEmpty()){
                ret.course = Double.parseDouble(course);
            }

            //Set the UTC date
            String date = values[9];
            if (!date.isEmpty()){
                ret.date = parseUtcDate(date);
            }

            //Set magnetic variation
            String variation = values[10];
            if (!variation.isEmpty()){
                ret.magVar = Double.parseDouble(variation);
            }

           
            Debug.debugOut("RMC message parsed");
            
             }catch(Exception e){
           
       }
            
            
            
            return ret;

        
        
    }

    /**
     * Parse the $GPGSA message
     * @param message
     */
    private GSA parseGsa(String message) {
       
        GSA ret = new GSA();
        ret.sentance = message;
        return ret;
    }

    private NMEASentence parseVTG(String message){
        return (new NMEASentence(false));
    }

    private NMEASentence parseGll(String message){
        return (new NMEASentence(false));
    }

    private UTCTime parseUtcTime(String utcTime){
        try {
            Debug.debugOut("UTC: " + utcTime);
            String[] splitTime = utcTime.split("[.]");
            //Parse time from end of utcTime String
            String utcSecond = splitTime[0].substring(splitTime[0].length() - 2);
            String utcMinutes = splitTime[0].substring(splitTime[0].length() - 4, splitTime[0].length() - 2);
            String utcHour = splitTime[0].substring(0, splitTime[0].length() - 4);
            UTCTime time = new UTCTime(
                    Integer.parseInt(utcHour),
                    Integer.parseInt(utcMinutes),
                    Integer.parseInt(utcSecond));

            return time;
        } catch (NumberFormatException ex){
            Debug.debugOut("Number format exception in parseUTCTime");
            //Return the default UTC Time
            return new UTCTime();
        }
    }

    private UTCDate parseUtcDate(String utcDate){
        try {
            //Parse date from end
            String utcYear = utcDate.substring(utcDate.length() - 2);
            String utcMonth = utcDate.substring(utcDate.length() - 4, utcDate.length() - 2);
            String utcDay = utcDate.substring(0, utcDate.length() - 4);

            UTCDate date = new UTCDate(Integer.parseInt(utcDay), Integer.parseInt(utcMonth), Integer.parseInt(utcYear));
            return date;
        } catch (NumberFormatException ex){
            Debug.debugOut("Number format exception in parseUtcDate");
            //Return the default UTC Date
            return new UTCDate();
        }
    }

    private Coordinate parseCoordinate(
            String latitude,
            String latitudeHemisphere,
            String longitude,
            String longitudeHemisphere){
        try {
        Hemisphere latHemisphere;
        Hemisphere lonHemisphere;
        if (latitudeHemisphere.equals("N")){
            latHemisphere = Hemisphere.NORTH;
        } else {
            latHemisphere = Hemisphere.SOUTH;
        }

        if (longitudeHemisphere.equals("E")){
            lonHemisphere = Hemisphere.EAST;
        } else {
            lonHemisphere = Hemisphere.WEST;
        }

        String latdegrees = latitude.substring(0, 2);
        String latmins = latitude.substring(3);
        
        //if(longitude.startsWith("0")){longitude = longitude.substring(1);}
        String longdegrees = longitude.substring(0, 3);
        String longmins = longitude.substring(3);
        double nLatitude = Double.parseDouble(latdegrees)+(Double.parseDouble(latmins)/60);
        double nLongitude = Double.parseDouble(longdegrees)+(Double.parseDouble(longmins)/60);
        
        
        

        Coordinate coordinate = new Coordinate(nLatitude,latHemisphere,nLongitude,lonHemisphere);

        return coordinate;
        } catch (NumberFormatException ex){
            Debug.debugOut("Number format exception in parseCoordinate");
            //Return the default coordinate
            return new Coordinate();
        }
    }

    //Parse values from checksum
    private String parseFromChecksum(String value){
        String[] splitVale = value.split("\\*");
        return splitVale[0];
    }

    private GSV parseGsv(String message) {
       
        GSV ret = new GSV();
       
        try{
            String[] values = message.split(",");
            
            
            mNumberOfGSVSentences = Integer.parseInt(values[1]);
            mCurrentGSVSentence++;
            int totalSatellites = Integer.parseInt(values[3]);

            //Parse message
            if (mCurrentGSVSentence == 1){
                mSatellitesInView = new ArrayList<>();
            }
            for (int i = 0; i < 4; i++){
                int prn = Integer.parseInt(values[4+(i*4)]);
                int elevation = Integer.parseInt(values[5+(i*4)]);
                int azimuth = Integer.parseInt(values[6+(i*4)]);
                int carrierToNoise = Integer.parseInt(parseFromChecksum(values[7+(i*4)]));
                SatelliteInView satInView = new SatelliteInView(prn,elevation,azimuth,carrierToNoise);
                mSatellitesInView.add(satInView);
                if (totalSatellites == mSatellitesInView.size()){
                    break;
                }
            }

            if (mCurrentGSVSentence == mNumberOfGSVSentences){
                mCurrentGSVSentence = 0;
                ret.satellitesInView = mSatellitesInView;
            }
             }catch(Exception e){
           
       }
           return ret;

        
    }

}
