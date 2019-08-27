package unipotsdam.gf.mysql;

import ch.vorburger.exec.ManagedProcessException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GeneralConfig;
import unipotsdam.gf.config.IConfig;

import javax.inject.Inject;
import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class PoolingMysqlConnectImpl implements MysqlConnect {

    public static final AtomicInteger counter = new AtomicInteger();
    private Boolean saneState;

    @Inject
    IConfig iConfig;


    @Inject
    IConnectionPoolUtility connectionPoolUtility;


    public PoolingMysqlConnectImpl() {
        //System.out.println("test");
        saneState = true;
    }

    public PoolingMysqlConnectImpl(IConnectionPoolUtility connectionPoolUtility, IConfig iConfig) {
        //System.out.println("test");
        saneState = true;
        this.connectionPoolUtility = connectionPoolUtility;
        this.iConfig = iConfig;
    }

 /*   public PoolingMysqlConnectImpl(IConfig iConfig, ConnectionPoolUtility connectionPoolUtility) {
        //System.out.println("test");
        this.iConfig = iConfig;
        this.connectionPoolUtility = connectionPoolUtility;
    }
*/

    private static final Logger log = LoggerFactory.getLogger(MysqlConnect.class);

    protected Connection conn = null;

/*    private String createConnectionString() {

        String connString = iConfig.getDBURL() + "/" + iConfig.getDBName() + "?user=" + iConfig
                .getDBUserName() + "&password=" + iConfig.getDBPassword();
        return String.format(connString, iConfig.getDBName());
    }*/

    @Override
    public void connect() {
        if (saneState) {
            saneState = false;
        } else {
            log.error("starting second connect but connection exists");
        }
     /*   try {
            log.trace("opening connection" + this);
            conn = getConnection();
        } catch (ManagedProcessException | SQLException e) {
            e.printStackTrace();
        }*/
        try {
            conn = connectionPoolUtility.getDataSource().getConnection();
            counter.addAndGet(1);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (saneState) {
            throw new Error("closing connection but connection is already closed");
        } else {
            saneState = true;
        }
        try {
            if (conn != null) {
                log.trace("closing connection" + this);
                counter.addAndGet(-1);
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
                ps = getConnection().prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = getConnection().prepareStatement(statement);
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
            //assert ps != null;
            if (ps == null) {
                log.error("data for statement is corrupted: " + statement);
            }
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
            getConnection().createStatement().execute(statement);
        } catch (SQLException ex) {
            printErrorMessage(statement, ex);
        } catch (ManagedProcessException e) {
            e.printStackTrace();
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
     * @param args      values, that replace every "?" in order
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
        return conn;
    }

    public void setiConfig(IConfig iConfig) {
        this.iConfig = iConfig;
    }
}
