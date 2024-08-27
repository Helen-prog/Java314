package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner input;

    public User(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    public void register(){
        input.nextLine();
        System.out.print("\nИмя: ");
        String full_name = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Пароль: ");
        String password = input.nextLine();

        if(user_exist(email)){
            System.out.println("Пользователь с таким адресом электронной почты уже существует!\n");
            return;
        }

        String register_query = "INSERT INTO user VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int row = preparedStatement.executeUpdate();
            if(row > 0){
                System.out.println("Регистрация прошла успешно!");
            } else {
                System.out.println("Регистрация не удалась");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String login(){
        input.nextLine();
        System.out.print("\nEmail: ");
        String email = input.nextLine();
        System.out.print("Пароль: ");
        String password = input.nextLine();

        String login_query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean user_exist(String email){
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
}
