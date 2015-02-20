/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.analyze;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Noah
 * @param <C>
 */
public class PseudoCollectionSet<C> implements Iterable{
    Map<C, Integer> map = new HashMap();
    
    public PseudoCollectionSet(){
        
    }
    public boolean add(C value){
       if(map.containsKey(value)){
           
           map.put(value, map.get(value)+1);
           return true;
       }else{
           map.put(value, 1);
           return true;  
       }
    }
    public Integer getMultiples(Object value){
        return map.get(value);
    }
    
    public int size() {
        int i = 0;
       for(Integer o: map.values()){
           i += o;
       }
       return i;
       
    }


    public boolean isEmpty() {
       return map.isEmpty();
    }


    public boolean contains(Object o) {
        return map.containsKey(o)|| map.containsValue(o);
    }


    @Override
    public Iterator<C> iterator() {
       return map.keySet().<C>iterator();
    }


    public boolean remove(Object o) {
         map.remove(o);
         return true;
    }

    
    
    
    
    
}
