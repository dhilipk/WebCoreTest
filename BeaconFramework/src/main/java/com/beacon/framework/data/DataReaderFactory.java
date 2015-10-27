package com.beacon.framework.data;

public class DataReaderFactory {
	
	private DataReaderFactory(){}
	
	private static DataReaderFactory instance = new DataReaderFactory();

	public static DataReaderFactory getInstance(){
		return instance;
	}
	
	public DataReader getDataReader(FileType fileType){
		DataReader dataReader = null;
		
		if(fileType == FileType.CSV){
			dataReader = new CSVDataReader();
		}else if(fileType == FileType.XLS){
			throw new IllegalArgumentException("Illegal Argument XLS is not implemented");
		}else {
			throw new IllegalArgumentException("Only CSV is implemented, XLS development is in-progress");
		}
		
		return dataReader;
	}

}
