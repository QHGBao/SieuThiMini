package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectManager {
    private Connection connect;
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SieuThiMini;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa"; 
    private static final String PASSWORD = "123456789";

    public Connection getConnection() {
        return connect;
    }

    public void openConnection() {
        try {
            this.connect = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
