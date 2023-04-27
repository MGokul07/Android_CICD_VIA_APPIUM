package com.soucelab.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.SupportsLegacyAppManagement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumBaseClass extends GestureBaseClass {

	protected static PropertyReader configProperty = new PropertyReader();
	protected static AppiumDriver driver;
	private static String platform;
	public static ExtentReports extent;
	private String UDID;
	private static String DIR_PATH = System.getProperty("user.dir");
	private static String Resource_PATH = DIR_PATH + File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator;
	private static final String androidAppPath = Resource_PATH + "APP" + File.separator + "Android" + File.separator;
	private static final String iOSAppPath = Resource_PATH + "APP" + File.separator + "iOS" + File.separator;
	private static final String JSON_FILE_PATH = Resource_PATH+"TestData"+File.separator+"StringValidation.json";
	public static TreeMap<String, String> jsonMapData; 
	private static AppiumDriverLocalService service;
	private static DesiredCapabilities capabilities;
	private static String URL="";

	public static DesiredCapabilities setDesiredCapabilitiesForAndroid(String grantPermissions, String noReset,
			String UDID_device,String deviceName, String platformName) throws Exception {
		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		capabilities.setCapability(MobileCapabilityType.UDID, UDID_device);

		capabilities.setCapability(MobileCapabilityType.APP, AppiumBaseClass.getAppAbsoultePath());
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 200000);
		capabilities.setCapability("appWaitActivity", configProperty.getProperty("appActivity"));

		if (noReset.contains("true")) {
			capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		} else {
			capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
		}

		capabilities.setCapability("autoGrantPermissions", grantPermissions);

		return capabilities;
	}

	public static DesiredCapabilities setDesiredCapabilitiesForiOS(String grantPermissions, String noReset,
			String UDID_device1) throws Exception {

		capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, configProperty.getProperty("platformName"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
				configProperty.getProperty("platformVersion"));
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, configProperty.getProperty("deviceName"));
		capabilities.setCapability(MobileCapabilityType.UDID, UDID_device1);
		capabilities.setCapability(MobileCapabilityType.APP, AppiumBaseClass.getAppAbsoultePath());
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 50000);
		capabilities.setCapability("appWaitDuration", 50000);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, noReset);
		capabilities.setCapability("autoAcceptAlerts", grantPermissions);
		return capabilities;
	}

	/**
	 * Get absolute path of android application apk file. Apk file is under the app
	 * folder
	 *
	 * @paramno parameter
	 * @return absolute path.
	 */
	public static String getAppAbsoultePath() {
		File appDir;
		if (platform.toLowerCase().equalsIgnoreCase("Android")) {
			appDir = new File(androidAppPath);
		} else {
			appDir = new File(iOSAppPath);
		}

		File app = new File(appDir, getAppName());
		String appName = app.getAbsolutePath();
		System.out.println(appName);
		return appName;
	}

	/**
	 * This function get run time application name from app folder
	 */
	public static String getAppName() {
		String fileName = "";
		File folder;
		if (platform.toLowerCase().equalsIgnoreCase("Android")) {
			folder = new File(androidAppPath);
		} else {
			folder = new File(iOSAppPath);
		}

		File[] listOfFiles = folder.listFiles();
		for (File listFile : listOfFiles) {
			String fileExtension = FilenameUtils.getExtension(listFile.getAbsolutePath());
			if (fileExtension.equals("apk") || fileExtension.equals("ipa") || fileExtension.equals("app")) {
				fileName = listFile.getName();
				break;
			}
		}
		System.out.println(fileName);
		return fileName;
	}

	public static void disableWarning() {
		System.err.close();
		System.setErr(System.out);
	}

	@BeforeSuite
	public void setUpSuite() throws Exception {
		extent = ExtentReport.getExtentInstance();
	}

	@BeforeClass
	@Parameters({ "grantPermissions", "noReset", "udid", "deviceName", "platformName"})
	public void setupClass(String grantPermissions, String noReset, String udid, String deviceName, String  platformName) {
		try {
			UDID=udid;
			platform=platformName;
			startAppiumServer();
			jsonMapData = JsonDataReader.readJSONSpecifiedParentKey(configProperty.getProperty("JSONSource"), JSON_FILE_PATH);
			
			switch (platform) {
			case "Android":
				driver = new AndroidDriver(new URL(URL),
						setDesiredCapabilitiesForAndroid(grantPermissions, noReset, UDID, deviceName, platformName));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				break;
			case "iOS":
				driver = new IOSDriver(new URL(URL),
						setDesiredCapabilitiesForiOS(grantPermissions, noReset, UDID));
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				break;
			default:
				// need to add logs here for nat launching device
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void launchApp(Method method) throws Exception {
		try {
			String testName = method.getName();
			System.out.println("**********Started the execution for Testcase: " + testName + " **********");

			ApplicationState st = ((InteractsWithApps)driver).queryAppState("com.swaglabsmobileapp");
			System.out.println(st.name());
			if (!(st.name().equalsIgnoreCase("RUNNING_IN_FOREGROUND"))) {// RUNNING_IN_FOREGROUND,NOT_RUNNING
				((InteractsWithApps) driver).activateApp("com.swaglabsmobileapp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void closeApp() throws InterruptedException {
		if (platform.equalsIgnoreCase("iOS") || platform.equalsIgnoreCase("Android")) {
			((InteractsWithApps) driver).terminateApp("com.swaglabsmobileapp");
		}
	}

	@AfterClass
	public void tearDownClass() {
		try {
			if (platform.equalsIgnoreCase("iOS") || platform.equalsIgnoreCase("Android")) {
				driver.quit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void tearDownSuite() {
		try {
			extent.flush();
			service.close();
			System.out.println(service.isRunning());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getScreenshotForValidation(String screenshotName, AppiumDriver driver) {
		try {
			File srcFile = driver.getScreenshotAs(OutputType.FILE);
			File targetFile = new File(DIR_PATH + File.separator + "report" + File.separator + screenshotName + ".png");
			FileUtils.copyFile(srcFile, targetFile);

			ExtentReport.test.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenshotName + ".png").build());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ExtentReport.test.fail("getScreenshot method failed, cannot attach screenshot");
		}
	}

	@SuppressWarnings("deprecation")
	public static void closeApp(AppiumDriver driver) {

		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).closeApp();
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).closeApp();
		}
	}

	public void terminateApp() {

		String appID = null;
		if (driver != null) {
			try {
				if (driver instanceof AndroidDriver) {
					appID = (String) driver.getCapabilities().getCapability(AndroidMobileCapabilityType.APP_PACKAGE);
				} else if (driver instanceof IOSDriver) {
					appID = String.valueOf(driver.getCapabilities().getCapability(IOSMobileCapabilityType.BUNDLE_ID));
				}
				if (appID != null)
					((InteractsWithApps) driver).terminateApp(appID);
			} catch (Exception e) {

			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void launchApp() {
		if (driver instanceof AndroidDriver) {
			((AndroidDriver) driver).launchApp();
		} else if (driver instanceof IOSDriver) {
			((IOSDriver) driver).launchApp();
		}
	}

	public static void logtest(String testDescription) {
		String logtext = "<b>*** " + testDescription + " ***</b>";
		Markup m = MarkupHelper.createLabel(logtext, ExtentColor.CYAN);
		ExtentReport.test.log(Status.INFO, m);
	}

	public static void logtestStep(String testDescription) {
		String logtext = "<b>*** " + testDescription + " ***</b>";
		Markup m = MarkupHelper.createLabel(logtext, ExtentColor.GREY);
		ExtentReport.test.log(Status.INFO, m);
	}

	public static AppiumDriver getDriver() {
		return driver;
	}

	public static PropertyReader getPropertyInstance() {
		return configProperty;
	}

	public static void getScreenshotForFailures(String screenshotName, AppiumDriver driver) {
		try {
			File srcFile = driver.getScreenshotAs(OutputType.FILE);
			File targetFile = new File(DIR_PATH + File.separator + "report" + File.separator + screenshotName + ".png");
			FileUtils.copyFile(srcFile, targetFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ExtentReport.test.fail("getScreenshot method failed, cannot attach screenshot");
		}
	}
	
	public static boolean enterSessionCodeViaKeyPad(AppiumDriver driver, String sessionCode) {
		try {
			int i;
			String[] values = sessionCode.split("");
			for (i = 0; i < values.length; i++) {
				if (values[i].matches("[0-9]+")) {
					((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.valueOf("DIGIT_" + values[i])));
				} else {
					((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.valueOf(values[i].toUpperCase())));
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static AppiumDriverLocalService startAppiumServer() throws Exception {
		
//	 	String nodePath=configProperty.getProperty("nodePath");
//	 	String npmPath=configProperty.getProperty("npmPath");
//	 	String ipAddress=configProperty.getProperty("ipAddress");
//	 	
//	  service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort()
//						.withAppiumJS(new File("C:\\Users\\2430835\\AppData\\Roaming\\npm\\node_modules\\appium\\lib\\appium.js")).withArgument(() -> "--log-level","error")
//						.withCapabilities(capabilities)); // -- to start
        Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
		service = AppiumDriverLocalService.buildDefaultService(); // to start appium server on default port
	 	
		service.start();
		Thread.sleep(10000);
		URL=service.getUrl().toString();
        System.out.println(URL);
		return service;
	}

	public static void main(String[] args) throws Exception {

		PropertyReader p = new PropertyReader();
		System.out.println(p.getProperty("suiteName"));
		startAppiumServer();
	}

}
