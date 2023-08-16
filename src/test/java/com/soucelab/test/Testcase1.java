package com.soucelab.test;

import java.util.Random;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.soucelab.page.HomePage;
import com.soucelab.page.LoginPage;
import com.soucelab.page.MenuPage;
import com.soucelab.utils.AppiumBaseClass;

@Listeners(com.soucelab.utils.Listenerclass.class)
public class Testcase1 extends AppiumBaseClass{
	
	Random random= new Random();

	@Test()
	public void loginIntoAppWithValidCredentials() throws InterruptedException {
		
	    logtest("Verifying the Login Page functionality with valid credentials");
		LoginPage lp =new LoginPage(driver);
		lp.setUsername(getPropertyInstance().getProperty("Username"));
		lp.setPassword(getPropertyInstance().getProperty("Password"));
		lp.clickOnLoginButton();
		Thread.sleep(3000);
		getScreenshotForValidation("homepage_"+random.nextInt(10000), driver);
		HomePage hp=new HomePage(driver);
		hp.verifyHomePage();
		scroll_till_element_is_visible("Sauce Labs Onesie");
		Thread.sleep(3000);
		hp.clickOnMenuIcon();
		MenuPage mp=new MenuPage(driver);
		mp.clickOnLogout();
		lp.verifyLoginPage();
	}
	
	@Test(retryAnalyzer = com.soucelab.utils.CustomRetryListener.class)
	public void loginIntoAppWithInValidCredentials() {
		
	    logtest("Verifying the Login Page functionality with Invalid credentials");
		LoginPage lp =new LoginPage(driver);
		lp.setUsername(getPropertyInstance().getProperty("Username"));
		lp.setPassword("wrongpwd");
		lp.clickOnLoginButton();
		lp.verifyLoginErrorMessage();
	}
	
	@Test(retryAnalyzer = com.soucelab.utils.CustomRetryListener.class)
	public void loginIntoApp() {
		
	    logtest("Verifying the Login Page functionality with Invalid credentials");
		LoginPage lp =new LoginPage(driver);
		lp.setUsername(getPropertyInstance().getProperty("Username"));
		lp.setPassword(getPropertyInstance().getProperty("Password"));
		lp.clickOnLoginButton();
	}

}
