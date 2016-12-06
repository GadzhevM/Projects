package iban.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import iban.mapper.IBANEntry;
import iban.mapper.IBANLengthMapper;
import iban.mapper.SwiftMapper;

public class IBANJDBCTemplate implements IBANDAO {
	
	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbc;
	
	@Override
	@Autowired
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbc = new JdbcTemplate(this.dataSource);
	}

	@Override
	public void loadFromDB(Map<String, String> swiftMap, 
							Map<String, Integer> lengthMap) {
		
		String swift = "SELECT bank_name, swift_code FROM banks.swift_code";
		String length = "SELECT country_code, length FROM banks.ibanLength_by_country";
		
		List<IBANEntry<String, String>> swifts = jdbc.query(swift, 
				new SwiftMapper());
		for(IBANEntry<String, String> swiftEntry : swifts){
			swiftMap.put(swiftEntry.getKey(), swiftEntry.getValue());
		}
		
		List<IBANEntry<String, Integer>> listOfLength = 
				jdbc.query(length, new IBANLengthMapper());
		for(IBANEntry<String, Integer> lengthEntry : listOfLength){
			lengthMap.put(lengthEntry.getKey(), lengthEntry.getValue());
		}
	}

	@Override
	public boolean updateFromDB(Map<String, String> swiftMap, Timestamp timestamp) {
		boolean isUpdated = false;
	
		List<IBANEntry<String, String>> swifts = jdbc.query(new PreparedStatementCreator(){
			String swift = "SELECT swift_code, bank_name FROM"
					+ " banks.swift_code WHERE date_of_update > ?";
			@Override
			public PreparedStatement createPreparedStatement(Connection connect) throws SQLException {
				PreparedStatement ps = connect.prepareStatement(swift);
				ps.setTimestamp(1, timestamp);
				return ps;
			}
		}, new SwiftMapper());
		
		if(swifts.size() != 0){
			isUpdated = true;
			for(IBANEntry<String, String> swiftEntry : swifts){
				swiftMap.put(swiftEntry.getKey(), swiftEntry.getValue());
			}
		}
		return isUpdated;
	}
}
