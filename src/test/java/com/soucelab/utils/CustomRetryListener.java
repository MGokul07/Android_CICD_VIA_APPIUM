package com.soucelab.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class CustomRetryListener implements IRetryAnalyzer {
	/*
	 * retryCount MAX_RETRY_COUNT
	 */
	public static boolean retryFlag = false;
	public static boolean testStatus = true;

	private int retryCount = 1;
	private final int MAX_RETRY_COUNT = 1;

	public boolean retry(ITestResult result) {
	if(	result.getStatus() == ITestResult.FAILURE )
		testStatus = false;
		if (result.getStatus() == ITestResult.FAILURE && retryCount <= MAX_RETRY_COUNT) {
			retryFlag = true;
			retryCount++;
			return true;
		}
		return false;
	}
}
