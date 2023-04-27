package com.soucelab.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {
	
	public static ExtentSparkReporter es;
    public static ExtentReports extent;
    public static ExtentTest test;
    
    private static PropertyReader configProperty = new PropertyReader();
    private static String DIR_PATH = System.getProperty("user.dir");
    private static String reportPath = DIR_PATH+File.separator+"report"+File.separator;
    private static String sprintName = configProperty.getProperty("SprintName");
    private static String reportName;
    
    /**
	  *@method getExtentInstance() method which helps to generate the extent report to track the status of Test's
	  *@return object of ExtentReports
	  */
    public static ExtentReports getExtentInstance()
    {
    	reportName = reportPath+getReportName();
        es = new ExtentSparkReporter(reportName);
        es.config().setDocumentTitle(configProperty.getProperty("ReportTitle"));
        es.config().setReportName(configProperty.getProperty("ReportName")+configProperty.getProperty("suiteName")+"_"+sprintName);
        extent = new ExtentReports();
        extent.attachReporter(es);
        return extent;
    }

    /**
	  *@method getReportName() method which helps to create the report name with time stamp
	  *@return String -- name of the extent report postfix with time stamp
	  */
    public static String getReportName()
    {
    	String d = new SimpleDateFormat("E MMM.dd.yyyy.HH.mm.ss").format(new java.util.Date());
        
        String reportName=configProperty.getProperty("ReportName")+"_"+sprintName+"_"+d.toString().replace(":","_").
                replace(" ","_")+".html";

        return reportName;
    }
    
    public static String getLoggertPath()
    {
        Date d = new Date();

        String loggerName=configProperty.getProperty("LoggerName")+sprintName+"_"+"TestName_"+d.toString().replace(":","_").
                replace(" ","_")+".txt";

        return loggerName;
    }
    
    /**
	  *@method getScreenshotnameofFailureTest() method which helps to create the screenshot name with time stamp
	  *@return String -- name of the extent report postfix with time stamp
	  */
    public static String getScreenshotnameofFailureTest(String testCasename) {
		 Date d = new Date();
        return testCasename+"_"+d.toString().replace(":","_").
	                replace(" ","_");
	}
    
        
    
    public static void main(String[] args) {
		System.out.println(reportPath);
	}

}
