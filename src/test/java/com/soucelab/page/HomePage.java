package com.soucelab.page;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.soucelab.utils.AppiumBaseClass;
import com.soucelab.utils.ExtentReport;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {
	
	
	public HomePage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	Boolean actionStatus;

	@AndroidFindBy(accessibility =  "test-Cart drop zone")
	private WebElement productTitle;
	
	@AndroidFindBy(accessibility = "test-Menu")
	private WebElement menuicon;
	
	public void verifyHomePage()
	{
		actionStatus=AppiumBaseClass.verifyMobileElement(productTitle);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Home page loaded successfully");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to verify the home page");
			assertTrue(actionStatus);
		}
	}
	
	public void clickOnMenuIcon()
	{
		actionStatus=AppiumBaseClass.click(menuicon);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Clicked on menu button");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to click on mune button");
			assertTrue(actionStatus);
		}
	}
}
