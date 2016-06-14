///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package util;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
///**
// *
// * @author Sarkhan Rasullu
// */
//public class PropertyUtil {
//
//    public static String getProperty(String key) {
//        Properties prop = new Properties();
//        InputStream input = null;
//
//        try {
//
//            String filename = "config.properties";
//            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
//            if (input == null) {
//                System.out.println("Sorry, unable to find " + filename);
//                return null;
//            }
//
//            //load a properties file from class path, inside static method
//            prop.load(input); 
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return prop.getProperty(key);
//    }
//    
//    public static long getPropertyAsLong(String key){
//        String value = getProperty(key);
//        System.out.println("val="+value);
//        if(value == null){
//            return -1;
//        }
//        long l = Long.valueOf(value);
//        System.out.println("l="+l);
//        return l;
//        
//    }
//}
