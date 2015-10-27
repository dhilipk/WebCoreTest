package com.beacon.framework.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;

import com.beacon.framework.config.Config;
import com.beacon.framework.driver.DriverFactory;
import com.beacon.framework.driver.DriverManager;
import com.beacon.framework.driver.RemoteDriverFactory;

public class TestExecutionUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestExecutionUtil.class);
	
	private static final Boolean eventFiringDriverEnabled = Boolean.parseBoolean(Config.EVENT_FIRING_ENABLED.getValue());
	
	private static final Boolean isFullScreen = Boolean.parseBoolean(Config.FULL_SCREEN.getValue());
	
	private static final Integer MAX_WAIT = Integer.parseInt(Config.MAX_WAIT.getValue());
	
	private TestExecutionUtil() {}

	private static TestExecutionUtil instance = new TestExecutionUtil();
	
	public static TestExecutionUtil getInstance(){
		return instance;
	}
	
	/**
	 * Screenshot filename will be based on the methodName, Listener and DateTime 
	 * @param method
	 * @param s
	 * @return
	 */
	public String getFileName(ITestNGMethod method, String s){
		String sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		String ssName = Config.SS_DIRECTORY.getValue() + method.getMethodName() + s + sdf + "." + Config.SS_FILETYPE.getValue();
		return ssName;
	}
	
	public void maximizeBrowserWindow(WebDriver driver) {
		
		if(isFullScreen){
			driver.manage().window().maximize();
		}
		
		driver.manage().timeouts().pageLoadTimeout(MAX_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(MAX_WAIT, TimeUnit.SECONDS);
		driver.get(Config.URL.getValue());
	}

	public WebDriver enableEventFiring(WebDriver driver) {
		if(eventFiringDriverEnabled){
			LOGGER.debug("EVENT FIRING ENABLED value : '{}'", eventFiringDriverEnabled);
			EventFiringWebDriver eventFiringDriver = new EventFiringWebDriver(driver);
			driver = eventFiringDriver.register(new TestWebExecution());
			DriverManager.setWebDriver(driver);	
		} else {
			LOGGER.warn("Framework will not support for Event Firing : 'EVENT_FIRING_ENABLED' set to '{}'", eventFiringDriverEnabled);
		}
		return driver;
	}

	public WebDriver createWebDriver() {
		WebDriver driver = null;
		if("grid".equals(Config.RUN_MODE.getValue())){
			driver = RemoteDriverFactory.getDriver();
		}else {
			driver = DriverFactory.getDriver();
		}
		return driver;
	}
}
