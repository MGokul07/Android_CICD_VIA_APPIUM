package com.soucelab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	
	private static String DIR_PATH = System.getProperty("user.dir");
	private static String Resource_PATH = DIR_PATH+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"Config"+File.separator+"config.properties";
	
	String path =  getFilePath();  
	
	/**
	 *@method getProperty() which helps to fetch the data from config.properties file
	 */
    public String getProperty(String key){ 
    	String value = "";
        try{         	  
	          Properties prop = new Properties();
		      prop.load(new FileInputStream(Resource_PATH));
		      value = prop.getProperty(key); 		                 
	   }
        catch(Exception e){  
           System.out.println("Failed to read from application.properties file.");  
        }
        return value;
     } 
    
    /**
	 *@method getFilePath() get the current file path
	 */
	public String getFilePath()
	{
		String filepath ="";		
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		filepath = absolutePathOfFirstFile.replaceAll("\\\\+", "/");		
		return filepath;
	}
 

}
