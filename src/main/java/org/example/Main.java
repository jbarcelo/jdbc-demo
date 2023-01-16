package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Main {
    private static Connection connection;
    public static void main(String[] args) throws SQLException {
        System.out.println("Database application!");
        try {
            openDatabaseConnection();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void openDatabaseConnection() throws SQLException{
        System.out.println("Opening database connection...");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://test-db00008950.mdb0002865.db.skysql.net:5007/jdbc_demo?useSsl=true&serverSslCert=/home/jbarcelo/Downloads/skysql_chain.pem",
                "DB00008950","Wa4Pz2UIeyoKAjAZ01P6*I|mca");
                System.out.println("Connection valid: " + connection.isValid(0));
    }

    private static void closeDatabaseConnection() throws SQLException {
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(0));
    }
}