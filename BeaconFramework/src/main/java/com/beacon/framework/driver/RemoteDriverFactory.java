package com.beacon.framework.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beacon.framework.config.Config;

public class RemoteDriverFactory {
	
	private static String hubUrl = Config.HUB_URL.getValue();
	
	private static final String browser = Config.BROWSER.getValue().toLowerCase();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteWebDriver.class);
	
	public static WebDriver getDriver(){
		RemoteWebDriver driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		LOGGER.info("Provided property values for capabilities for Hub : Browser ['" + browser + "'], HubUrl ['" + hubUrl + "']");
		capabilities.setBrowserName(browser);
		capabilities.setJavascriptEnabled(true);
		capabilities.setPlatform(Platform.WINDOWS);
		try {
			driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
			DriverManager.setWebDriver(driver);
		} catch (MalformedURLException e) {
			LOGGER.error("Not able to create Remote Web Driver");
		}
		return driver;
	}

}
