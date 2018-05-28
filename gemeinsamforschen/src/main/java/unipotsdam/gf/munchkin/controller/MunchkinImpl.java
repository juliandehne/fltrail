package unipotsdam.gf.munchkin.controller;

import unipotsdam.gf.interfaces.IMunschkin;
import unipotsdam.gf.munchkin.model.Munschkin;

import java.sql.*;

/**
 * Created by dehne on 24.04.2018.
 */
public class MunchkinImpl implements IMunschkin {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost";

    //  Database credentials
    static final String USER = "root2";
    static final String PASS = "voyager2";

    // Es gibt natürlich auch libraries, die den Datenbankzugriff einfacher gestalten. Ziel soll sein, dass alle
    // die Basics können. Es ist auch sinnvoll, die Datenbankzugriffe in eine eigene Klasse auszulagern!!
    @Override
    public Munschkin getMunschkin(int id) {

        Munschkin munschkin = new Munschkin();

        // JDBC driver name and database URL
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "Use munschkins;";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            sql = "SELECT MunschkinId, LastName, FirstName, BadThings, Strength FROM Munschkins WHERE " +
                    "MunschkinId="+id+";";
            rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                munschkin.setMunschkinId(rs.getInt("MunschkinId"));
                munschkin.setBadThings(rs.getString("BadThings"));
                munschkin.setLastName(rs.getString("LastName"));
                munschkin.setFirstName(rs.getString("FirstName"));
                munschkin.setStrength(rs.getInt("Strength"));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try

        return munschkin;
    }

    @Override
    public void letMunchKinFight(Munschkin otherMunchkin) {
        // TODO was euer Algorithmus sonst noch so kann
    }
}
