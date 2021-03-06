package com.beacon.framework.core;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
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
		LOG.debug("Clearing the existing value : " + element.getText() + " on the web element : " + element.getTagName());
		element.clear();
		LOG.debug("Tring to enter value : " + value + " on the web element : " + element.getTagName());
		element.sendKeys(value);
		LOG.debug("Value Entered : " + value + " on the web element : " + element.getTagName());
	}
	
	public void click(WebElement element){
		LOG.debug("Trying to click access the web element : " + element.getText());
		element.click();
		LOG.debug("Clicked on the web element : " + element.getText());
	}
	
	public void submit(WebElement element){
		LOG.debug("Trying to submit access the web element : " + element.getText());
		element.submit();
		LOG.debug("Submitted the web element : " + element.getText());
	}
	
	public void selectByValue(WebElement element, String value){
		LOG.debug("Trying to access the Select Element : " + element.getTagName());
		Select select = new Select(element);
		LOG.debug("Select Object Obtained");
		select.selectByValue(value);
		LOG.debug("Value Altered for Select Element : " + value);
	}
}
