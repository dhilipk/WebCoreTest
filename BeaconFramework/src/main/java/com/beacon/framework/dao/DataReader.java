package com.beacon.framework.dao;

import java.lang.reflect.Method;

public interface DataReader {
	
	public abstract <T extends Object> Object [] [] constructDataObject(Method method, String fileName, Class<T> className);

}
