package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConexao() {

		try {
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "admin", "admin");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void fecharConexao(Connection connection) {
		try {
			if (connection != null) {
				connection.close();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void fecharConexao(Connection connection, PreparedStatement stat) {
		try {
			if (stat != null) {
				stat.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fecharConexao(connection);
	}

	public static void fecharConexao(Connection connection, PreparedStatement stat, ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fecharConexao(connection, stat);
	}

}
