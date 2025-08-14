package org.example.util;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {


    private static final String url = "jdbc:postgresql://dpg-d2es5m2dbo4c738q12c0-a.oregon-postgres.render.com:5432/akbar_oybek_db";
    private static final String user = "akbar_oybek_db_user";
    private static final String password = "R3Imx62aaUlXiKt7ccWMG2PUD0IEB6aN";

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(url, user, password);
    }

}
