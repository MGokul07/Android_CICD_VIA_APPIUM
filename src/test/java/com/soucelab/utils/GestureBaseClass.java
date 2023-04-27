package com.soucelab.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class GestureBaseClass {

	/**
	 * scroll_till_element_is_visible method is used to perform scroll action till
	 * element is visible
	 * 
	 * For example, scroll_till_element_is_visible(driver, "element text")
	 */
	public static void scroll_till_element_is_visible(String elementText) {
		if (AppiumBaseClass.configProperty.getProperty("platformName").contains("Android")) {
			String scrollValue = "new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\")).scrollIntoView(new UiSelector().textMatches(\""
					+ elementText + "\").instance(0))";
			AppiumBaseClass.getDriver().findElement(AppiumBy.androidUIAutomator(scrollValue));
		} else if (AppiumBaseClass.configProperty.getProperty("platformName").contains("iOS")) {
			Map<String, Object> params = new HashMap<>();
			WebElement element = AppiumBaseClass.getDriver().findElement(AppiumBy.className("className"));
			params.put("elementId", ((RemoteWebElement) element).getId());
			params.put("name", elementText);
			AppiumBaseClass.getDriver().executeScript("mobile: scroll", params);
		}

	}

	private static void waitForVisibility(WebElement e) {
		WebDriverWait wait = new WebDriverWait(AppiumBaseClass.getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public static boolean clear(WebElement e) {
		try {
			waitForVisibility(e);
			e.clear();
		} catch (Exception exc) {
			System.out.print(exc.getMessage());
			return false;
		}
		return true;
	}

	public static boolean click(WebElement e) {
		try {
			waitForVisibility(e);
			e.click();
		} catch (Exception exc) {
			System.out.print(exc.getMessage());
			return false;
		}
		return true;
	}

	public static boolean sendKeys(WebElement e, String txt) {
		try {
			waitForVisibility(e);
			e.sendKeys(txt);
		} catch (Exception exc) {
			System.out.print(exc.getMessage());
			return false;
		}
		return true;
	}

	public static boolean verifyMobileElement(WebElement e) {
		try {
			waitForVisibility(e);
		} catch (Exception exc) {
			System.out.print(exc.getMessage());
			return false;
		}
		return true;
	}

	public static String getText(WebElement e) {
		String value = "";
		try {
			waitForVisibility(e);
			value = e.getText();
		} catch (Exception exc) {
			System.out.print(exc.getMessage());
			return value;
		}
		return value;
	}
	
	 public static void longPressGesture(WebElement element){

		if (AppiumBaseClass.configProperty.getProperty("platformName").contains("Android")) {
			AppiumBaseClass.getDriver().executeScript("mobile: longClickGesture", ImmutableMap.of(
	                "elementId", ((RemoteWebElement) element).getId(),
	                "duration", 3000));
		} else if (AppiumBaseClass.configProperty.getProperty("platformName").contains("iOS")) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("elementId", ((RemoteWebElement) element).getId());
	        params.put("duration", 5);
	        AppiumBaseClass.getDriver().executeScript("mobile: touchAndHold", params);
		}
	    }
	
	
	@SuppressWarnings("deprecation")
	public static void scrollToViewAnElement(AppiumDriver driver, int scrollCount) {
		Dimension siz = driver.manage().window().getSize();
		int startx = siz.width / 2;

		int endx = startx;

		int starty = (int) (siz.height * 0.8);

		int endy = (int) (siz.height * 0.2);

		for (int i = 0; i < scrollCount; i++) {
			@SuppressWarnings("rawtypes")
			TouchAction act = new TouchAction((PerformsTouchActions) driver);
			act.press(PointOption.point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(endx, endy)).release().perform();
		}
	}

}
