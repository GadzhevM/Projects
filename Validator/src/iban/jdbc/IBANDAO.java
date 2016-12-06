package iban.jdbc;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public interface IBANDAO {
	
	void setDataSource(DriverManagerDataSource dataSource);
	
	void loadFromDB(Map<String, String> swiftMap, 
					Map<String, Integer> lengthMap);
	
	boolean updateFromDB(Map<String, String> swiftMap, Timestamp timestamp);
}
