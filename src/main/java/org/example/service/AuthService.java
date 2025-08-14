package org.example.service;

import lombok.SneakyThrows;
import org.example.entity.Account;
import org.example.util.DBConnection;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.util.Tools.*;

public class AuthService {
    Connection connection = DBConnection.getConnection();
    AccountService accountService = new AccountService();
    public void service() {

        while (true) {
            System.out.println("""
                1.Sign up
                2.Sign in
                
                0.Exit
                """);
            switch (scannerInt.nextInt()) {
                case 1 -> {
                    signUp();
                }
                case 2 -> {
                    signIn();
                }
                case 0 -> {
                    return;
                }
                default -> {
                    System.out.println("invalid input");
                }
            }

        }
    }




    @SneakyThrows
    private void signIn() {
        System.out.println("Enter your username:");
        String username = scannerStr.nextLine();
        System.out.println("Enter your password:");
        String password = scannerStr.nextLine();



        String sql = "SELECT * FROM account WHERE username = '%s' AND password = '%s' ".formatted(username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Account user = new Account();

        if (resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        }else{
            System.out.println("Username or password is incorrect");
            return;
        }
        statement.close();
        currentUser = user;
        accountService.service();

    }



    @SneakyThrows
    public Boolean checkUser(String username, String password) {
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM account WHERE username = '%s' AND password = '%s' ".formatted(username, password);
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            return true;
        }
        return false;
    }



    @SneakyThrows
    private void signUp() {

        System.out.println("Enter your username");
        String username = scannerStr.nextLine();
        System.out.println("Enter your password");
        String password = scannerStr.nextLine();

        if (checkUser(username, password)) {
            System.out.println("Username is already exits");
            return;
        }

        String sql = "insert into account(username, password) values('%s',  '%s') ".formatted(username, password);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        System.out.println("User created");
        statement.close();
    }

}
