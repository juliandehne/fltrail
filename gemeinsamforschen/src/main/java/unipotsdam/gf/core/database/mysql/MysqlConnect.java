package unipotsdam.gf.core.database.mysql;

import unipotsdam.gf.GFDatabaseConfig;

import java.sql.*;
import java.util.Date;

public class MysqlConnect {

	public Connection conn = null;

	private static String createConnectionString() {

		String connString = "jdbc:mysql://" + "localhost" +
				"/" + GFDatabaseConfig.DB_NAME +
				"?user=" + GFDatabaseConfig.USER +
				"&password=" + GFDatabaseConfig.PASS;
		return String.format(connString, GFDatabaseConfig.DB_NAME);
	}

	public void connect() {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException ex) {
				System.out.println(ex); //logger?
			}
			conn = DriverManager.getConnection(createConnectionString());
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw new Error("could not connect to mysql");
		}
	}


	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (final SQLException e) {
			throw new Error("could not close mysql");
		}
	}

	private PreparedStatement addParameters(final String statement, final Object[] args) {
		try {
			final PreparedStatement ps = conn.prepareStatement(statement);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					final Object arg = args[i];
					setParam(ps, arg, i + 1);
				}
			}
			return ps;
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}


	public VereinfachtesResultSet issueSelectStatement(final String statement, final Object... args) {
		try {
			PreparedStatement ps = addParameters(statement, args);
			ResultSet queryResult = ps.executeQuery();
			return new VereinfachtesResultSet(queryResult);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}


	public void otherStatements(final String statement) {
		try {
			this.conn.createStatement().execute(statement);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}


	public Integer issueUpdateStatement(final String statement, final Object... args) {
		PreparedStatement ps = addParameters(statement, args);
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return null;
	}


	public void issueInsertOrDeleteStatement(final String statement, final Object... args) {
		PreparedStatement ps = addParameters(statement, args);
		try {
			ps.execute();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	private void setParam(final PreparedStatement ps, final Object arg, final int i) throws SQLException {
		if (arg instanceof String) {
			ps.setString(i, (String) arg);
		} else if (arg instanceof Integer) {
			ps.setInt(i, (Integer) arg);
		} else if (arg instanceof Double) {
			ps.setDouble(i, (Double) arg);
		} else if (arg instanceof Boolean) {
			ps.setBoolean(i, (Boolean) arg);
		} else if (arg instanceof Float) {
			ps.setFloat(i, (Float) arg);
		} else if (arg instanceof Short) {
			ps.setShort(i, (Short) arg);
		} else if (arg instanceof Long) {
			ps.setLong(i, (Long) arg);
		} else if (arg instanceof Byte) {
			ps.setByte(i, (Byte) arg);
		} else if (arg instanceof Character) {
			ps.setString(i, arg.toString());
		} else if (arg instanceof Date) {
			final java.sql.Date d = new java.sql.Date(((Date) arg).getTime());
			ps.setDate(i, d);
		} else if (arg == null) {
			ps.setNull(i, Types.NULL);
		} else {
			ps.setString(i, arg.toString());
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
}
