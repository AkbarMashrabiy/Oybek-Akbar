package org.example.service;

import lombok.SneakyThrows;
import org.example.entity.Category;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.Tools.*;

public class AccountService {

    public static Connection connection = DBConnection.getConnection();

    public void service() {
        while (true) {
            System.out.println("""
                1. add category
                2. show categories
                3. add expense
                4. show expense by category
                5. show overall expense
                
                0.Exit
                """);
            switch (scannerInt.nextInt()) {
                case 1 -> {
                    addCategory();
                }
                case 2 -> {
                    showCategories();
                }
                case 3 -> {
                    addExpense();
                }
                case 4 -> {
                    showExpenseByCategory();
                }
                case 5 -> {
                    showOverallExpense();
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
    private void showOverallExpense() {
        String sql = "SELECT SUM(amount) AS total FROM expense WHERE user_id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, currentUser.getId());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    double total = rs.getDouble("total");
                    System.out.println("Overall expense: " + total);
                } else {
                    System.out.println("No expenses found.");
                }
            }
        }
    }

    @SneakyThrows
    private void showExpenseByCategory() {
        List<Category> categoryList = currentUser.getCategoryList();
        if (categoryList == null || categoryList.isEmpty()) {
            System.out.println("You don't have any category");
            return;
        }
        categoryList.forEach(System.out::println);

        System.out.println("Select category by id");
        int id = scannerInt.nextInt();
        scannerInt.nextLine();

        boolean found = false;
        for (Category cat : categoryList) {
            if (cat.getId() == id) {
                found = true;
                String sql = "SELECT description, amount FROM expense WHERE category_id = ?";
                double total = 0.0;
                try (PreparedStatement pst = connection.prepareStatement(sql)) {
                    pst.setInt(1, id);
                    try (ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            String desc = rs.getString("description");
                            double amount = rs.getDouble("amount");
                            System.out.println(desc + " " + amount);
                            total += amount;
                        }
                    }
                }
                System.out.println("Overall price: " + total);
                break;
            }
        }

        if (!found) {
            System.out.println("Wrong category ID");
        }
    }




    @SneakyThrows
    private void addExpense() {
        List<Category> categoryList = currentUser.getCategoryList();
        if (categoryList == null || categoryList.isEmpty()) {
            System.out.println("You don't have any category");
            return;
        }
        categoryList.forEach(System.out::println);

        System.out.println("Select category by id");
        int id = scannerInt.nextInt();
        scannerInt.nextLine(); // consume newline

        boolean found = categoryList.stream().anyMatch(c -> c.getId() == id);
        if (!found) {
            System.out.println("Wrong category ID");
            return;
        }

        System.out.println("Enter Expense Description: ");
        String description = scannerStr.nextLine();

        System.out.println("Enter Expense amount: ");
        String amountStr = scannerStr.nextLine();
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        String sql = "INSERT INTO expense(description, amount, category_id, account_id) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, description);
            pst.setDouble(2, amount);
            pst.setInt(3, id);
            pst.setInt(4, currentUser.getId());
            pst.executeUpdate();
        }
        System.out.println("Expense added");
    }





    @SneakyThrows
    private void showCategories() {
        String sql = "SELECT * FROM category WHERE user_id = ?";
        List<Category> categoryList = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, currentUser.getId());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setCategoryName(rs.getString("category_name"));
                    c.setUserId(rs.getInt("user_id"));
                    categoryList.add(c);
                }
            }
        }

        currentUser.setCategoryList(categoryList);
        categoryList.forEach(System.out::println);
    }
    @SneakyThrows

    private void addCategory() {
        System.out.println("Enter category name:");
        String name = scannerStr.nextLine();

        Statement statement = connection.createStatement();
        String sql = "INSERT INTO category(category_name, user_id) VALUES('%s', %d)".formatted(name, currentUser.getId());
        statement.executeUpdate(sql);
        System.out.println("Category added");
        statement.close();

        Statement statement1 = connection.createStatement();
        List<Category> categoryList = new ArrayList<>();
        String sql1 = "select all from category where user_id = %d ".formatted(currentUser.getId());
        ResultSet resultSet = statement1.executeQuery(sql1);
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt("id"));
            category.setCategoryName(resultSet.getString("category_name"));
            category.setUserId(resultSet.getInt("user_id"));
            categoryList.add(category);
        }
        currentUser.setCategoryList(categoryList);
        statement1.close();
    }


}
