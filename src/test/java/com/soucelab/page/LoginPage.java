package com.soucelab.page;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.soucelab.utils.AppiumBaseClass;
import com.soucelab.utils.ExtentReport;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage {
	
	
	public LoginPage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	Boolean actionStatus;

	@AndroidFindBy(accessibility = "test-Username")
	private WebElement username;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@content-desc=\"test-Password\"]")
	private WebElement password;
	
	@AndroidFindBy(accessibility = "test-LOGIN")
	private WebElement loginbutton;
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView")
	private WebElement loginerrortoastmessage;
	
	public void setUsername(String value)
	{
		actionStatus=AppiumBaseClass.sendKeys(username, value);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Username entered as '"+value+"'");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to enter the username");
			assertTrue(actionStatus);
		}
		
	}
	
	public void setPassword(String value)
	{
		actionStatus=AppiumBaseClass.sendKeys(password, value);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Password entered as '"+value+"'");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to enter the password");
			assertTrue(actionStatus);
		}
	}
	
	public void clickOnLoginButton()
	{
		actionStatus=AppiumBaseClass.click(loginbutton);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Clicked on login button");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to click on login button");
			assertTrue(actionStatus);
		}
	}
	
	public void verifyLoginPage()
	{
		actionStatus=AppiumBaseClass.verifyMobileElement(loginbutton);
		if (actionStatus) {
			ExtentReport.test.log(Status.PASS, "Login page loaded successfully");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to verify the home page");
			assertTrue(actionStatus);
		}
	}
	
	public void verifyLoginErrorMessage()
	{
	    String expectText = AppiumBaseClass.jsonMapData.get("Invalidloginerrormessage");
		String actualText = AppiumBaseClass.getText(loginerrortoastmessage);
		if (expectText.contains(actualText)) {
			ExtentReport.test.log(Status.PASS, "'"+expectText+"' text verified");
		} else {
			ExtentReport.test.log(Status.FAIL, "Failed to verify the "+expectText+" text");
			assertEquals(expectText, actualText);
		}
	}
}
