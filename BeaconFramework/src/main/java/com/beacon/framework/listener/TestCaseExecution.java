package com.beacon.framework.listener;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.beacon.framework.core.WebUIDriver;
import com.beacon.framework.driver.DriverManager;
import com.relevantcodes.extentreports.LogStatus;

public class TestCaseExecution implements ITestListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseExecution.class);
	private static String parallelMode;
	
	public void onTestStart(ITestResult result) {
		

	}

	public void onTestSuccess(ITestResult result) {
		LOGGER.debug("Test Success Event for " + result.getName());
		File scrFile = ((TakesScreenshot)DriverManager.getWebDriver()).getScreenshotAs(OutputType.FILE);
		try {
			String destFileName = TestExecutionUtil.getInstance().getFileName(result.getMethod(), LogStatus.PASS.toString());
			LOGGER.debug("Screenshot taken for success test case " + result.getName() + " stored in " + destFileName);
			FileUtils.copyFile(scrFile, new File(destFileName));
		} catch (IOException e) {
			LOGGER.error("Not able to store the file in the provided destination");
			e.printStackTrace();
		}
	}

	public void onTestFailure(ITestResult result) {
		File scrFile = ((TakesScreenshot)DriverManager.getWebDriver()).getScreenshotAs(OutputType.FILE);
		try {
			String destFileName = TestExecutionUtil.getInstance().getFileName(result.getMethod(), LogStatus.FAIL.toString());
			LOGGER.debug("Screenshot taken for success test case " + result.getName() + " stored in " + destFileName);
			FileUtils.copyFile(scrFile, new File(destFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		File scrFile = ((TakesScreenshot)DriverManager.getWebDriver()).getScreenshotAs(OutputType.FILE);
		try {
			String destFileName = TestExecutionUtil.getInstance().getFileName(result.getMethod(), LogStatus.SKIP.toString());
			LOGGER.debug("Screenshot taken for success test case " + result.getName() + " stored in " + destFileName);
			FileUtils.copyFile(scrFile, new File(destFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		

	}

	public void onStart(ITestContext context) {
		LOGGER.info("Config Start : " + context.getSuite().getName() + " with parallel mode : " + context.getSuite().getParallel());
		
		parallelMode = context.getSuite().getParallel();
		
		if("false".equals(parallelMode)){
			WebDriver driver = TestExecutionUtil.getInstance().createWebDriver();
			driver = TestExecutionUtil.getInstance().enableEventFiring(driver);
			TestExecutionUtil.getInstance().maximizeBrowserWindow(driver);
		}else {
			LOGGER.error("Parallel Mode Not provided correctly");
		}
	}

	public void onFinish(ITestContext context) {
		LOGGER.info("Config Finish : " + context.getSuite().getName());
		WebUIDriver.closeWebBrowser();
	}

}
