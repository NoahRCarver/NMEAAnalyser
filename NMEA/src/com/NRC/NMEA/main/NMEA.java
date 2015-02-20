/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NRC.NMEA.main;

import com.NRC.NMEA.analyze.PseudoCollectionSet;
import com.NRC.NMEA.data.Coordinate;
import com.NRC.NMEA.data.NMEAData.DataPointInTime;
import com.NRC.NMEA.data.NMEAData.TestMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author Noah
 */
public class NMEA {
    static TestMap test1 = new TestMap("Test 1");
    static TestMap test2 = new TestMap("Test 2");
    static TestMap test3 = new TestMap("Test 3");
    static TestMap test4 = new TestMap("Test 4");
    static TestMap pControl = new TestMap("Positive Control");
    static TestMap nControl = new TestMap("Negative Control");
    
    //File stuff
    static File dirFolder = new File("C:\\Users\\Noah\\Desktop\\Test Data");
    FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".txt") && (name.contains("t")||name.contains("c"));
        }
    };
    static File[] listOfFiles = dirFolder.listFiles();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initValues();
        
        
        
        
       try{
            
            createExcelFile();
            
           
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
        } catch (Exception ex) {
           System.out.println("error");
        }
                
        
    }   
    public static void initValues(){
         for(File f:listOfFiles){
            File[] subFiles = f.listFiles();
            for(File f2:subFiles){
                try {
                    readFileToAppropriateTestMap(f2);
                } catch (IOException ex) {
                    com.NRC.NMEA.debug.Debug.debugOut("error reading file: " + f2.getName());
                }
            }
        }
        test1.analyze();
        test2.analyze();
        test3.analyze();
        test4.analyze();
        nControl.analyze();
        pControl.analyze();
    }
    
    
    public static void createExcelFile() throws IOException{
          Workbook wb = new XSSFWorkbook();
           FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Noah\\Desktop\\Excel stuff\\Data.xlsx");;
           Sheet PSheet = wb.createSheet("Position Data");
           Sheet ESheet = wb.createSheet("Elevation Data");
           Sheet AbESheet = wb.createSheet("Absolute Elevation Data");
           Sheet DSheet = wb.createSheet("Chart Data");
           DSheet = writeStatInfoToExcel(DSheet);
           
           {
            AbESheet = writeAbsElevationsToExcel(pControl, AbESheet, 0);
            AbESheet = writeAbsElevationsToExcel(test1, AbESheet, 4);
            AbESheet = writeAbsElevationsToExcel(test2, AbESheet, 9);
            AbESheet = writeAbsElevationsToExcel(test3, AbESheet, 14);
            AbESheet = writeAbsElevationsToExcel(test4, AbESheet, 19);
           
           
//           
           
           }
           {
               
            ESheet = writeElevationsToExcel(pControl, ESheet, 0);
            ESheet = writeElevationsToExcel(test1, ESheet, 4);
            ESheet = writeElevationsToExcel(test2, ESheet, 9);
            ESheet = writeElevationsToExcel(test3, ESheet, 14);
            ESheet = writeElevationsToExcel(test4, ESheet, 19);
            Set<Double> Eset2 = new HashSet();
            Eset2.add(pControl.dev.meanElevation);
            Eset2.add(test1.dev.meanElevation);
            Eset2.add(test2.dev.meanElevation);
            Eset2.add(test3.dev.meanElevation);
            Eset2.add(test4.dev.meanElevation);
            Set<Double> Eset3 = new HashSet();
            Eset3.add(pControl.dev.verticalStandardDeviation);
            Eset3.add(test1.dev.verticalStandardDeviation);
            Eset3.add(test2.dev.verticalStandardDeviation);
            Eset3.add(test3.dev.verticalStandardDeviation);
            Eset3.add(test4.dev.verticalStandardDeviation);
                
            Row erow21 = ESheet.createRow((short)24);
            Row erow22 = ESheet.createRow((short)25);
            Row erow23 = ESheet.createRow((short)27);
            Row erow24 = ESheet.createRow((short)28);
             
            Cell erow21c = erow21.createCell(0);
            erow21c.setCellValue("Means");
            Cell erow22c = erow22.createCell(0);
            erow22c.setCellValue("Height");
            
            Cell erow23c = erow23.createCell(0);
            erow23c.setCellValue("Standard Deviations");
            Cell erow24c = erow24.createCell(0);
            erow24c.setCellValue("Height");
            
            int i = 1;
            for(Double d : Eset2){
            erow22c = erow22.createCell(i);
            erow22c.setCellValue(d);
            i++;
            }
            i = 1;
            for(Double d : Eset3){
            erow24c = erow24.createCell(i);
            erow24c.setCellValue(d);
            i++;
            }
           
           
           }
           
           
           {
            PSheet = writePositionsToExcel(pControl, PSheet, 0);
            PSheet = writePositionsToExcel(test1, PSheet, 4);
            PSheet = writePositionsToExcel(test2, PSheet, 9);
            PSheet = writePositionsToExcel(test3, PSheet, 14);
            PSheet = writePositionsToExcel(test4, PSheet, 19);
            {
                Set<Coordinate> set = new HashSet();
                set.add(pControl.dev.center);
                set.add(test1.dev.center);
                set.add(test2.dev.center);
                set.add(test3.dev.center);
                set.add(test4.dev.center);
                Set<Double> set2 = new HashSet();
                set2.add(pControl.dev.latitudeStandardDeviation);
                set2.add(test1.dev.latitudeStandardDeviation);
                set2.add(test2.dev.latitudeStandardDeviation);
                set2.add(test3.dev.latitudeStandardDeviation);
                set2.add(test4.dev.latitudeStandardDeviation);
                Set<Double> set3 = new HashSet();
                set3.add(pControl.dev.longitudeStandardDeviation);
                set3.add(test1.dev.longitudeStandardDeviation);
                set3.add(test2.dev.longitudeStandardDeviation);
                set3.add(test3.dev.longitudeStandardDeviation);
                set3.add(test4.dev.longitudeStandardDeviation);
                   
             Row centerrow1 = PSheet.createRow((short)24);
             Row centerrow2 = PSheet.createRow((short)25);
             Row centerrow3 = PSheet.createRow((short)26);
             Cell centerrow1c = centerrow1.createCell(0);
             centerrow1c.setCellValue("Centers");
             Cell centerrow2c = centerrow2.createCell(0);
             centerrow2c.setCellValue("lat");
             Cell row3c = centerrow3.createCell(0);
             row3c.setCellValue("long");
             
             int i = 1;
            for(Coordinate c : set){
            centerrow1c = centerrow1.createCell(i);
            centerrow2c = centerrow2.createCell(i);
            centerrow1c.setCellValue(c.getLatitude());
            centerrow2c.setCellValue(-c.getLongitude());
            i++;
         
            
            }
           }
           }
               wb.write(fileOut);
           fileOut.close();
    }
    
     public static Sheet writeStatInfoToExcel(Sheet sheet) throws IOException{

            Queue<TestMap> q = new LinkedList();
                   q.add(pControl);q.add(test1);q.add(test2);q.add(test3);q.add(test4);
                
            
                Row row0 = sheet.createRow((short)0);
                
                row0.createCell(1).setCellValue("Mean Latitude");
                row0.createCell(2).setCellValue("Latitude Deviation");
                row0.createCell(3).setCellValue("Mean Longitude");
                row0.createCell(4).setCellValue("Longitude Deviation");
                row0.createCell(5).setCellValue("Mean Altitude");
                row0.createCell(6).setCellValue("Atitude Deviation");
                row0.createCell(7).setCellValue("Position Test Size");
                row0.createCell(8).setCellValue("Elevation Test Size");
                Row row;
                int i = 1;
                for(TestMap t: q){
                    row = sheet.createRow((short)i);
                    
                    row.createCell(0).setCellValue(t.name);
                    row.createCell(1).setCellValue(t.dev.center.getLatitude());
                    row.createCell(2).setCellValue(t.dev.latitudeStandardDeviation);
                    row.createCell(3).setCellValue(-t.dev.center.getLongitude());
                    row.createCell(4).setCellValue(t.dev.longitudeStandardDeviation);
                    row.createCell(5).setCellValue(t.dev.meanElevation);
                    row.createCell(6).setCellValue(t.dev.verticalStandardDeviation);
                    row.createCell(7).setCellValue(t.dev.PosTestSize);
                    row.createCell(8).setCellValue(t.dev.ETestSize);
                    i++;
                }
        return sheet;
    }
    public static Sheet writeElevationsToExcel(TestMap m, Sheet sheet, int startRow) throws IOException{
        PseudoCollectionSet s = new PseudoCollectionSet();
        
        for(DataPointInTime d : m){
            Double f = (d.elevation);
            if(!(f == 0.0)){
            s.add(f);
            }
        }
                Row row1 = sheet.createRow((short)startRow);
                Row row2 = sheet.createRow((short)startRow+1);
                Row row3 = sheet.createRow((short)startRow+2);
                Cell row1c = row1.createCell(0);
                row1c.setCellValue(m.name);
                Cell row2c = row2.createCell(0);
                row2c.setCellValue("Elevation");
                Cell row3c = row3.createCell(0);
                row3c.setCellValue("Multiples");
                int coli = 1;
                for(Object c: s){
                    row2c = row2.createCell(coli);
                    row2c.setCellValue((Double)c);
                    row3c = row3.createCell(coli);
                    row3c.setCellValue(s.getMultiples(c));
                    coli++;
                }
        return sheet;
    }
    public static Sheet writeAbsElevationsToExcel(TestMap m, Sheet sheet, int startRow) throws IOException{
        PseudoCollectionSet s = new PseudoCollectionSet();
        
        for(DataPointInTime d : m){
            Double f = (d.elevation);
            if(!(f == null)){
            s.add(f);
            }
        }
                Row row1 = sheet.createRow((short)startRow);
                Row row2 = sheet.createRow((short)startRow+1);
                Row row3 = sheet.createRow((short)startRow+2);
                Cell row1c = row1.createCell(0);
                row1c.setCellValue(m.name);
                Cell row2c = row2.createCell(0);
                row2c.setCellValue("Elevation");
                Cell row3c = row3.createCell(0);
                row3c.setCellValue("Multiples");
                int coli = 1;
                for(Object c : s){
                    int i = 0;
                    for(i = 0; i < s.getMultiples(c);i++){
                        row2c = row2.createCell(coli+i);
                        row2c.setCellValue((Double)c);  
                    }
                    
                    coli+=i;
                }
        return sheet;
    }
    
    public static Sheet writePositionsToExcel(TestMap m, Sheet sheet, int startRow) throws IOException{
        Map<Coordinate,Integer> s = new HashMap();
        
        for(DataPointInTime d : m){
            Coordinate f = (d.coordinate);
            if(!((f == null)||(f.getLatitude() == -1))){
            if(s.containsKey(f)){
                s.put(f,s.get(f)+1);
            }else{
            s.put(f,1);
            }
            }
        }
                Row row1 = sheet.createRow((short)startRow);
                Row row2 = sheet.createRow((short)startRow+1);
                Row row3 = sheet.createRow((short)startRow+2);
                Row row4 = sheet.createRow((short)startRow+3);
                Cell row1c = row1.createCell(0);
                row1c.setCellValue(m.name);
                Cell row2c = row2.createCell(0);
                row2c.setCellValue("lat");
                Cell row3c = row3.createCell(0);
                row3c.setCellValue("long");
                Cell row4c = row4.createCell(0);
                row4c.setCellValue("mult");
                
                int coli = 1;
                for(Coordinate c: s.keySet()){
                    row2c = row2.createCell(coli);
                    row3c = row3.createCell(coli);
                    row4c = row4.createCell(coli);
                    row2c.setCellValue(c.getLatitude());
                    row3c.setCellValue(-c.getLongitude());
                    row4c.setCellValue(s.get(c));
                    coli++;
                }
        return sheet;
    }

          
    private static void readFileToAppropriateTestMap(File fin) throws IOException {
        TestMap pointer;

        switch(fin.getName().substring(0, 2)){
            case "t1":
                pointer = test1;
                break;
            case "t2":
                pointer = test2;
                break;
            case "t3":
                pointer = test3;
                break;
            case "t4":
                pointer = test4;
                break;
            case "cp":
                pointer = pControl;
                break;
            case "cn":
                pointer = nControl;
                break;
            default:
                return;
        }
	FileInputStream fis = new FileInputStream(fin);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                pointer.translateAndAdd(line);
            }
        }
        
}
   
}
