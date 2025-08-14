package org.example;

import org.example.service.AccountService;
import org.example.service.AuthService;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        authService.service();
    }
}