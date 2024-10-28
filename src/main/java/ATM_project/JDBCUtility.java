package ATM_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {
	public static Connection getConnection() throws SQLException {
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/advancejava", "root", "Srinivas@2326");
		return conn;
	}
}
