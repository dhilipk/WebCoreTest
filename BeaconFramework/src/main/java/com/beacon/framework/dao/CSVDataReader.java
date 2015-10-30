package com.beacon.framework.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CSVDataReader implements DataReader {

	private static final String cvsSplitBy = ",";
	
	@Override
	public <T extends Object> Object [] [] constructDataObject(Method method, String fileName, Class<T> className) {		
		BufferedReader br = null;
		String line = "";
		Boolean keysPresent = false;
		HashMap<Integer, String> rowKeys = new HashMap<>();
		List<Object> dataInstanceList = new ArrayList<>();
		
		try {
			br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));

			while ((line = br.readLine()) != null) {
				String[] rowValues = line.split(cvsSplitBy);
				if(rowValues == null){
					continue;
				}
				if(rowValues[0].equals(method.getName())){
					for (int j = 0; j < rowValues.length; j++) {
						rowKeys.put(j, rowValues[j]);
					}
					keysPresent = true;
					continue;
				}else if(!keysPresent) {
					continue;
				}
				
				dataInstanceList.add(createDataInstance(className, rowValues, rowKeys));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Object[][] dataInstanceObjects = new Object[] []{dataInstanceList.toArray()};
		return dataInstanceObjects;
	}

	private <T extends Object> T createDataInstance(Class<T> className, String[] rowValues, HashMap<Integer, String> rowKeys) 
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		T dataInstance = null;
		dataInstance = (T) className.newInstance();
		Field[] fields = dataInstance.getClass().getDeclaredFields();
		Boolean flipFlop = false;
		for (Field field : fields) {
			if(!field.isAccessible()){
				flipFlop = true;
				field.setAccessible(true);
			}
		
			Set<Integer> indexs = rowKeys.keySet();
			for (Integer index : indexs) {
				if(rowKeys.get(index).equals(field.getName())){
					field.set(dataInstance, rowValues[index]);
				}
			}
						
			if(flipFlop) {
				flipFlop = false;
				field.setAccessible(false);
			}
		}
		return dataInstance;
	}

}
