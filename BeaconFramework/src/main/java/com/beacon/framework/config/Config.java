package com.beacon.framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to access the property values. Property File will have the name
 * value pair, Name being mapped to Enum Name and its value will be retrieved
 * from property file.
 * 
 * @author DHILIPK
 * 
 */
public enum Config {

	BROWSER("browser"), URL("baseUrl"), SS_DIRECTORY("ssDirectory"), SS_FILETYPE(
			"ssFileType"), HUB_URL("hubUrl"), EVENT_FIRING_ENABLED("eventFiringEnabled"), FULL_SCREEN("fullscreen"),
			RUN_MODE("runMode"), MAX_WAIT("maxWait");

	private static final String BROWSER_NOT_IMPLEMENTED_EXCEPTION = "Given browser in property file is not Implementated";
	
	private static final String URL_NOT_PROVIDED_PROPERLY = "Not a proper URL provided from properties file : ";
	
	/**
	 * LOG - LOGGER for Config Class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Config.class);

	private String FRAMEWORK_PROPERTIES = "framework.properties";
	private Properties property;

	private Config(String name) {
		this.name = name;
	}

	private String name;
	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		LOG.trace("Trying to get Value for Name : " + getName());
		if (value == null) {
			LOG.debug("Value is null, Looking for value in Property file");
			initialize();
			value = property.getProperty(this.getName());
			validate(value);
			LOG.debug("Property Validated for " + name + " and its value " + value);
		}
		return value;
	}

	private void validate(String value) {
		switch (this) {
		case BROWSER:
			if(!value.equals("firefox")){
				LOG.error(BROWSER_NOT_IMPLEMENTED_EXCEPTION + value);
				throw new SkipException(BROWSER_NOT_IMPLEMENTED_EXCEPTION + value);
			}
			break;
		case URL:
			try {
				URI uri = new URI(value);
			} catch (URISyntaxException e) {
				LOG.error(URL_NOT_PROVIDED_PROPERLY + " " + value);
				throw new SkipException(URL_NOT_PROVIDED_PROPERLY + " " + e.getMessage(), e.getCause());
			}
			break;
		case SS_DIRECTORY:
			break;
		case SS_FILETYPE:
			break;
		case EVENT_FIRING_ENABLED:
			if(!"true".equals(value) && !"false".equals(value)){
				LOG.error("EVENT FIRING Value is not set correct, either true or false needs to be set");
			}
			break;
		default:
			break;
		}
	}

	private void initialize() {

		property = new Properties();
		try {
			LOG.debug("Loading property file from rootpath : "
					+ FRAMEWORK_PROPERTIES);
			property.load(new FileInputStream(FRAMEWORK_PROPERTIES));
		} catch (IOException e) {
			LOG.error("Failed to load the properties : " + e.getMessage());
			e.printStackTrace();
		}
		LOG.debug("Property Loaded successfully : " + FRAMEWORK_PROPERTIES);
	}
}
