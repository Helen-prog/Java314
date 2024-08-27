import java.sql.*;
import java.util.Scanner;

public class Main6 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/users1";
        String username = "root";
        String password = "123456";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String query = "INSERT INTO employees(name, job, salary) VALUES(?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                Scanner input = new Scanner(System.in);
                while (true){
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    System.out.print("Job: ");
                    String job = input.nextLine();
                    System.out.print("Salary: ");
                    double salary = input.nextDouble();
                    input.nextLine();
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, job);
                    preparedStatement.setDouble(3, salary);
                    preparedStatement.addBatch();
                    System.out.print("Добавить следующее значение (Y / N): ");
                    String res = input.nextLine();
                    if(res.toUpperCase().equals("N")){
                        break;
                    }
                }
                preparedStatement.executeBatch();
                connection.commit();
                System.out.println("Пакетная обработка прошла успешно");
            } catch (BatchUpdateException ex) {
                connection.rollback();
                System.out.println("Ошибка пакетной обработки");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
