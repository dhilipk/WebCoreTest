package com.beacon.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.beacon.framework.config.Config;

public class DriverFactory {

	public static WebDriver getDriver() {
		WebDriver driver = null;
		if (driver == null) {
			if ("firefox".equals(Config.BROWSER.getValue())) {
				driver = new FirefoxDriver();
				DriverManager.setWebDriver(driver);
			}
		}
		return driver;
	}

}
