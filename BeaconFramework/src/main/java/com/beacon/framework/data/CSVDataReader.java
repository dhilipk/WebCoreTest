package com.beacon.framework.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CSVDataReader implements DataReader {

	@Override
	public <T extends Object> Object [] [] constructDataObject(Method method, String fileName, Class<T> className) {
		T instance = null;
		Set<String> keySet = new HashSet<String>(); //buildSet(fileName);
		keySet.add("userName");
		keySet.add("password");
		try {
			instance = (T) className.newInstance();
			Field[] fields = instance.getClass().getDeclaredFields();
			Boolean flipFlop = false;
			for (Field field : fields) {
				if(!field.isAccessible()){
					flipFlop = true;
					field.setAccessible(true);
				}
				
				if(keySet.contains(field.getName())){
					field.set(instance, "CSV Value");
				}
				
				if(flipFlop)
				{
					flipFlop = false;
					field.setAccessible(false);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new Object [] [] {
				{instance}
		};
	}

}
