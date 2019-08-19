package unipotsdam.gf.mysql;

import ch.vorburger.exec.ManagedProcessException;
import com.mysql.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.IConfig;


import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.sql.*;
import java.util.Date;


public class MysqlConnectImpl implements MysqlConnect {


    @Inject
    IConfig iConfig;

    public  MysqlConnectImpl() {
        //System.out.println("test");
    }

    public  MysqlConnectImpl(IConfig iConfig) {
        //System.out.println("test");
        this.iConfig = iConfig;
    }


    private static final Logger log = LoggerFactory.getLogger(MysqlConnect.class);

    protected Connection conn = null;

    private String createConnectionString() {

        String connString =
                iConfig.getDBURL() + "/" + iConfig.getDBName() + "?user=" + iConfig.getDBUserName() + "&password=" +
                        iConfig.getDBPassword();
        return String.format(connString, iConfig.getDBName());
    }

    @Override
    public void connect() {
        try {
            conn = getConnection();
        } catch (ManagedProcessException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (final SQLException e) {
            log.error(e.toString());
            throw new Error("could not close mysql");
        }
    }

    private PreparedStatement addParameters(final String statement, boolean returnGenerated, final Object[] args) {
        try {
            PreparedStatement ps;

            if (returnGenerated) {
                ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = conn.prepareStatement(statement);
            }
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    final Object arg = args[i];
                    setParam(ps, arg, i + 1);
                }
            }
            return ps;
        } catch (SQLException ex) {
            printErrorMessage(statement, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public VereinfachtesResultSet issueSelectStatement(String statement, final Object... args) {
        //statement = statement.toLowerCase();
        try {
            PreparedStatement ps = addParameters(statement, false, args);
            assert ps != null;
            ResultSet queryResult = ps.executeQuery();
            return new VereinfachtesResultSet(queryResult);
        } catch (SQLException ex) {
            printErrorMessage(statement, ex);
        }
        return null;
    }

    @Override
    public int issueInsertStatementWithAutoincrement(String sql, final Object... args) {
        //sql = sql.toLowerCase();
        PreparedStatement stmt;
        try {
            stmt = addParameters(sql, true, args);
            assert stmt != null;
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            printErrorMessage(sql, e);
        }
        return -1;
    }

    public void otherStatements(final String statement) {
        try {
            this.conn.createStatement().execute(statement);
        } catch (SQLException ex) {
            printErrorMessage(statement, ex);
        }
    }

    @Override
    public Integer issueUpdateStatement(final String statement, final Object... args) {
        PreparedStatement ps = addParameters(statement, false, args);
        try {
            assert ps != null;
            return ps.executeUpdate();
        } catch (SQLException ex) {
            printErrorMessage(statement, ex);
        }
        return null;
    }

    private void printErrorMessage(String statement, SQLException ex) {
        ex.printStackTrace();
        String message = ex.toString() + " for statement \n" + statement;
        log.error(message);
        System.out.println(message);
    }


    /**
     * @param statement equals an SQL query where values for parameters are encoded with a "?"
     * @param args values, that replace every "?" in order
     */
    @Override
    public void issueInsertOrDeleteStatement(final String statement, final Object... args) {
        PreparedStatement ps = addParameters(statement, false, args);
        try {
            assert ps != null;
            ps.execute();
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
        }
    }

    private void setParam(final PreparedStatement ps, final Object arg, final int i) throws SQLException {
     /*   if (arg instanceof ArrayList) {
            String collapsedString = "";
            for (java.lang.Object elem : (ArrayList)arg) {
                collapsedString += elem;
            }
        }*/
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

    @Override
    public Connection getConnection() throws ManagedProcessException, SQLException {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            return DriverManager.getConnection(createConnectionString());

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            log.error(ex.getMessage());
            return null;
        }
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void setiConfig(IConfig iConfig) {
        this.iConfig = iConfig;
    }
}
