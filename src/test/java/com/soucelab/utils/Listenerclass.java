package com.soucelab.utils;
import java.io.File;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Listenerclass extends ExtentReport implements ITestListener
{

   private static ThreadLocal<ExtentTest> extentTest= new ThreadLocal<ExtentTest>();
   private static String testCaseName = null;
   private static String DIR_PATH = System.getProperty("user.dir");
   private static String screenshotPath = DIR_PATH+File.separator+"report"+File.separator;

   
	public void onTestStart(ITestResult result) {
		testCaseName = result.getMethod().getMethodName();
		test = extent.
				createTest("TC_Name :: "+testCaseName);
		extentTest.set(test);
		
		try {
			if(CustomRetryListener.retryFlag) {
		     AppiumBaseClass.launchApp();
			 CustomRetryListener.retryFlag=false;
			 }
			} catch (Exception e) {
           }
	}

	public void onTestSuccess(ITestResult result) {
		String logtext="<b>Test Method "+testCaseName+" successful</b>";
		Markup m= MarkupHelper.createLabel(logtext, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS,m);
	}

	public void onTestFailure(ITestResult result)
	{
		String exceptionalMessage= result.getThrowable().getMessage();
		try {
		extentTest.get().fail("<details><summary><b><font color=red>"+"Failed status :: "+"</font></b></summary>"+
				exceptionalMessage.replaceAll(",","<br>")+"</details> \n");
        String screenshotName=getScreenshotnameofFailureTest(testCaseName);
        if(AppiumBaseClass.getDriver()!=null)
        {
        AppiumBaseClass.getScreenshotForFailures(screenshotName, AppiumBaseClass.getDriver());
		extentTest.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b><br>",
				MediaEntityBuilder.createScreenCaptureFromPath(screenshotName + ".png").build());
        }
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			extentTest.get().fail("Test failed, cannot attach screenshot");
		}

		String logtext="<b>Test Method "+testCaseName+" failed</b>";
		Markup m= MarkupHelper.createLabel(logtext, ExtentColor.RED);
		extentTest.get().log(Status.FAIL,m);
	}

	public void onTestSkipped(ITestResult result) {
		String logtext="<b>Test Method "+testCaseName+" skipped</b>";
		Markup m= MarkupHelper.createLabel(logtext, ExtentColor.YELLOW);
		extentTest.get().log(Status.SKIP,m);
	}


	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	public void onFinish(ITestContext context) {

	}
	
	public static String getScreenshotPath() {
		   return screenshotPath;
			}
			
}
