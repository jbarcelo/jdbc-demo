package org.example;

import java.sql.*;

public class Main {
    private static Connection connection;
    public static void main(String[] args) throws SQLException {
        System.out.println("Database application!");
        try {
            openDatabaseConnection();
            deleteData("%");
            readData();
            createData("Java",10);
            createData("Javascript",9);
            createData("C++",8);
            readData();
            updateData("C++",7);
            readData();
            deleteData("C++");
            readData();
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

    private static void createData(String name, int rating) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
    INSERT INTO programming_language(name, rating)
    VALUES (?,?)
    """)){
            statement.setString(1, name);
            statement.setInt(2, rating);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        }
    }

    private static void readData() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
SELECT name, rating
FROM programming_language
ORDER BY rating DESC
""")){
            try (ResultSet resultSet = statement.executeQuery()){
                boolean empty = true;
                while (resultSet.next()){
                    empty = false;
                    String name = resultSet.getString("name");
                    int rating = resultSet.getInt("rating");
                    System.out.println("\t>" + name + ": " + rating);
                }
                if (empty){
                    System.out.println("\t (no data)");
                }
            }
        }
    }

    private static void updateData(String name, int newRating) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
UPDATE programming_language
SET rating = ?
WHERE name = ?
""")) {
            statement.setInt(1, newRating);
            statement.setString(2, name);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated" + rowsUpdated);
        }
    }

    private static void deleteData(String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
DELETE FROM programming_language
WHERE name LIKE ?
""")){
            statement.setString(1, name);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted" + rowsDeleted);
        }
    }
}