package com.beacon.framework.report;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.beacon.framework.listener.TestExecutionUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporterNG implements IReporter {
	private static ExtentReports extent;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		extent = new ExtentReports(outputDirectory + File.separator
				+ "Extent.html", true);
		extent.config().documentTitle("Automation Report")
				.reportName("Regression").reportHeadline("");
		Map<String, String> sysInfo = new HashMap<String, String>();
		sysInfo.put("Processor", System.getenv("PROCESSOR_ARCHITECTURE"));
		sysInfo.put("Selenium Version", "2.48.2");
		sysInfo.put("Environment", "Test");
		extent.addSystemInfo(sysInfo);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());
				/*Method[] methods = result.getMethod().getRealClass()
						.getMethods();
				for (Method method : methods) {
					Annotation aMethods = method
							.getAnnotation(MethodInfo.class);
					if (aMethods instanceof MethodInfo) {
						MethodInfo a = (MethodInfo) aMethods;
						System.out.println("Author Name for method "
								+ result.getMethod().getMethodName() + " is "
								+ a.author());

					}
				}*/
				test.getTest().startedTime = getTime(result.getStartMillis());
				test.getTest().endedTime = getTime(result.getEndMillis());
				String imagePath = TestExecutionUtil.getInstance().getFileName(result.getMethod(), status.toString());
						
				test.log(status,
						"Snapshot below: " + test.addScreenCapture(imagePath));
				;
				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "Test " + status.toString().toLowerCase()
						+ "ed";

				if (result.getThrowable() != null)
					message = result.getThrowable().getMessage();

				test.log(status, message);

				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
