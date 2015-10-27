package com.beacon.framework.core;

import com.beacon.framework.driver.DriverManager;

public class WebUIDriver {
	
	public static void closeWebBrowser(){
		DriverManager.getWebDriver().quit();
	}

}
