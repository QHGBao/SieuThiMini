package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectManager {
    private Connection connect;
    private static final String URL = "jdbc:sqlserver://localhost;databaseName=SieuThiMini;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa"; 
    private static final String PASSWORD = "123456789";

    public Connection getConnection() {
        return connect;
    }

    public void openConnection() {
        try {
            this.connect = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("Can't connect to database " + URL);
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when closing database");
            e.printStackTrace();
        }
    }
}
