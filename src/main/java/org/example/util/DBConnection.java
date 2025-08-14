package org.example.util;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";

    private static final String user = "root";

    private static final String password = "password";


    @SneakyThrows
    public static Connection getConnection() {

        return DriverManager.getConnection(url, user, password);



    }

}
