package com.NRC.NMEA.data;


/**
 *
 * @author noah
 */
public enum Hemisphere {

    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    private String mCharacter;

    Hemisphere(String character){
        mCharacter = character;
    }

    public String getHemisphere(){
        return mCharacter;
    }

}
