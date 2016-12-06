package iban.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IBANLengthMapper implements RowMapper<IBANEntry<String, Integer>> {

	@Override
	public IBANEntry<String, Integer> mapRow(ResultSet rs, int numRow)
			throws SQLException {
		IBANEntry<String, Integer> ibanLength;
		ibanLength = new IBANEntry<>(rs.getString("country_code"), 
				rs.getInt("length"));
		return ibanLength;
	}

}
