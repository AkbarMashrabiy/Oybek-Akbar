package org.example.service;

import org.example.entity.Account;


import static org.example.util.Tools.scannerInt;
import static org.example.util.Tools.scannerStr;

public class AuthService {
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

    private void signIn() {
        System.out.println("Enter your username");
        String username = scannerStr.nextLine();
        System.out.println("Enter your password");
        String password = scannerStr.nextLine();

    }

    private void checkUser(Account account) {


    }

    private void signUp() {

        System.out.println("Enter your username");
        String username = scannerStr.nextLine();
        System.out.println("Enter your password");
        String password = scannerStr.nextLine();
        System.out.println("Enter your phone number");
        String phoneNumber = scannerStr.nextLine();

    }

    private void saveUser(Account account) {

    }
}
