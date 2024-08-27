import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/users1";
        String username = "root";
        String password = "123456";
//        String query = "SELECT * FROM employees";
//        String query = "INSERT INTO employees VALUES (3, 'Irina', 'Full Stack Developer', 87000.0)";
//        String query = "DELETE FROM employees WHERE id=3";
        String query = """
                UPDATE employees
                SET job = 'Full Stack Developer', salary = 70000.0
                WHERE id = 2
                """;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
//            statement.executeUpdate("CREATE TABLE employees (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), job VARCHAR(255), salary DOUBLE)");
//            statement.executeUpdate("INSERT INTO employees VALUES (1, 'Roman', 'Java Developer', 75000.0)");
//            statement.executeUpdate("INSERT INTO employees (name, job, salary) VALUES ('Sergey', 'Devops Engineer', 65000.0)");
//            ResultSet rs = statement.executeQuery(query);
//            while (rs.next()){
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String job = rs.getString("job");
//                double salary = rs.getDouble("salary");
//                System.out.println();
//                System.out.println("============================");
//                System.out.println("ID: " + id);
//                System.out.println("Name: " + name);
//                System.out.println("Job: " + job);
//                System.out.println("Salary: " + salary);
//            }
            int row = statement.executeUpdate(query);
            if (row > 0){
                System.out.println("Изменено строк: " + row);
            } else {
                System.out.println("Ошибка изменения строк");
            }
            statement.close();
            connection.close();
//            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}