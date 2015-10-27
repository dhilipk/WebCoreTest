package com.beacon.framework.data;

import java.lang.reflect.Method;

public interface DataReader {
	
	Object [] [] getDataObject(Method method, FileType fileType, String fileName);

}
