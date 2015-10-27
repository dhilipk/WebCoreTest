package com.beacon.framework.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	
	public static WebDriver getWebDriver(){
		return webDriver.get();
	}

	public static void setWebDriver(WebDriver driver){
		DriverManager.webDriver.set(driver);
	}
}
