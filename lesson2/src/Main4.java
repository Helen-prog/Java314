import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main4 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/users1";
        String username = "root";
        String password = "123456";

        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number1 = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            try {
                PreparedStatement withDrawStatement = connection.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = connection.prepareStatement(depositQuery);
                withDrawStatement.setDouble(1, 500.00);
                withDrawStatement.setString(2, "account3");
                depositStatement.setDouble(1, 500.00);
                depositStatement.setString(2, "account8");
                int rowWithdraw = withDrawStatement.executeUpdate();
                int rowDeposit = depositStatement.executeUpdate();
                if (rowDeposit > 0 && rowWithdraw > 0){
                    connection.commit();
                    System.out.println("Транзакция успешна");
                } else {
                    connection.rollback();
                    System.out.println("Транзакция не удалась");
                }

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("1 - " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
