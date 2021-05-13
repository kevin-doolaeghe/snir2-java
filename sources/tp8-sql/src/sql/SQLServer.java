package sql;

import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;

public class SQLServer {
	private Connection mysql;
	
	public SQLServer(String ip, String base, String login, String password) {
		try {
			MysqlDataSource mysqlDataSource = new MysqlDataSource();
			mysqlDataSource.setUrl("jdbc:mysql://" + ip + ":3306/" + base + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            mysqlDataSource.setUser(login);
            mysqlDataSource.setPassword(password);
            
			mysql = mysqlDataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void terminate() {
		try {
			mysql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getData(String table) {
		try {
			String request = "SELECT * FROM ?";
			PreparedStatement preparedStatement = mysql.prepareStatement(request);
			preparedStatement.setString(1, table);
			Statement statement = mysql.createStatement();
			ResultSet resultSet = statement.executeQuery(request);
			
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public ResultSet request(String request) {
		try {
			Statement statement = mysql.createStatement();
			ResultSet resultSet = statement.executeQuery(request);
			
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
	}
}
