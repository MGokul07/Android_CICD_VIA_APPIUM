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

public class MenuPage {
	
	
	public MenuPage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	Boolean actionStatus;
	
	@AndroidFindBy(accessibility =  "test-Close")
	private WebElement menupage;
	
	@AndroidFindBy(accessibility =  "test-LOGOUT")
	private WebElement logout;
	
	public void verifyHomePage()
	{
		actionStatus=AppiumBaseClass.verifyMobileElement(menupage);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Menu page loaded successfully");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to verify the Menu page");
			assertTrue(actionStatus);
		}
	}
	
	public void clickOnLogout()
	{
		actionStatus=AppiumBaseClass.click(logout);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Clicked on logout button");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to click on logout button");
			assertTrue(actionStatus);
		}
	}
	
}
