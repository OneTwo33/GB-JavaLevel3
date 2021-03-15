package ru.company.onetwo33.homework3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SingletonConnection {

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB = "jdbc:sqlite:homework2.sqlite";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static Connection connection;

    private SingletonConnection() {}

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = initConnection();
        }
        
        return connection;
    }

    private static Connection initConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(DB, USER, PASSWORD);
    }
}
