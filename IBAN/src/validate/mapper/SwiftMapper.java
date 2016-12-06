package validate.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SwiftMapper implements RowMapper<IBANEntry<String, String>> {

	@Override
	public IBANEntry<String, String> mapRow(ResultSet rs, int numRow) throws SQLException {
		IBANEntry<String, String> swift;
		swift = new IBANEntry<>(rs.getString("swift_code"),
								rs.getString("bank_name"));
		return swift;
	}
}
