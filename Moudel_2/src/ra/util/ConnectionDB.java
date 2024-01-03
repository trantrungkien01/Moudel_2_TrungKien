package ra.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/model";
    private static final String USERNAME = "root";
    private static final String PASWORD = "12345678";

    public static Connection openConnection() {
        Connection conn = null;
        // set Driver cho DriverManager
        try {
            Class.forName(DRIVER);
            conn=DriverManager.getConnection(URL,USERNAME,PASWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}


