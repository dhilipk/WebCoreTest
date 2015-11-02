package com.beacon.framework.core;


public class AbstractPage implements Page{
	
	private PageElement pageElement  = new PageElement();

	@Override
	public PageElement getPageElement() {
		return pageElement;
	}
	
	
	
}
