package com.beacon.framework.core;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beacon.framework.driver.DriverManager;

public class PageElement {

	private static final Logger LOG = LoggerFactory.getLogger(PageElement.class);
	
	public <T> T createPage(Class<T> className){
		LOG.debug("Page Elements Created for class : " + className);
		return PageFactory.initElements(DriverManager.getWebDriver(), className);
	}
	
	public void sendKeys(WebElement element, String value){
		LOG.debug("Tring to enter value : " + value + " on the web element : " + element.getText() == null ? element.getTagName(): element.getText());
		element.sendKeys(value);
		LOG.debug("Value Entered : " + value + " on the web element : " + element.getText());
	}
	
	public void click(WebElement element){
		LOG.debug("Trying to access the web element : " + element.getText());
		element.click();
		LOG.debug("Clicked on the web element : " + element.getText());
	}
	
	public void select(WebElement element, String value){
		
	}
}
