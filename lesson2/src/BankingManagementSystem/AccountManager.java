package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner input;

    public AccountManager(Connection connection, Scanner input) {
        this.connection = connection;
        this.input = input;
    }

    private Double getBalance(long account_number, String security_pin) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_number = ? AND security_pin = ?");
        preparedStatement.setLong(1, account_number);
        preparedStatement.setString(2, security_pin);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            return resultSet.getDouble("balance");
        return null;
    }

    private int updateBalance(double amount, long account_number, String operation) throws SQLException {
        String debit_query = "UPDATE account SET balance = balance " + operation + " ? WHERE account_number = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);
        preparedStatement1.setDouble(1, amount);
        preparedStatement1.setLong(2, account_number);
        return preparedStatement1.executeUpdate();
    }

    public void debit_money(long account_number) throws SQLException {
        input.nextLine();
        System.out.print("Введите PIN-код: ");
        String security_pin = input.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number != 0) {
                Double current_balance = getBalance(account_number, security_pin);
                if (current_balance != null) {
                    System.out.print("Введите сумму: ");
                    double amount = input.nextDouble();

                    if (amount <= current_balance) {
                        int row = updateBalance(amount, account_number, "-");
                        if (row > 0) {
                            System.out.println("РУБЛИ: " + amount + " списано успешно");
                            double balance = getBalance(account_number, security_pin);
                            System.out.println("Ваш баланс: " + balance);
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Транзакция не удалась");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Недостаточный баланс");
                    }
                } else {
                    System.out.println("Неверный PIN-код");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void credit_money(long account_number) throws SQLException {
        input.nextLine();
        System.out.print("Введите PIN-код: ");
        String security_pin = input.nextLine();

        try {
            connection.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement getPreparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_number = ? AND security_pin = ?");
                getPreparedStatement.setLong(1, account_number);
                getPreparedStatement.setString(2, security_pin);
                ResultSet resultSet = getPreparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.print("Введите сумму: ");
                    double amount = input.nextDouble();
                    int row = updateBalance(amount, account_number, "+");
                    if (row > 0) {
                        System.out.println("РУБЛИ: " + amount + " зачислено успешно");
                        double balance = getBalance(account_number, security_pin);
                        System.out.println("Ваш баланс: " + balance);
                        connection.commit();
                        connection.setAutoCommit(true);
                    } else {
                        System.out.println("Транзакция не удалась");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                } else {
                    System.out.println("Неверный PIN-код");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void transfer_money(long sender_account_number) throws SQLException {
        input.nextLine();
        System.out.print("Введите PIN-код: ");
        String security_pin = input.nextLine();
        System.out.print("Введите номер счета получателя: ");
        long receiver_account_number = input.nextLong();

        try {
            connection.setAutoCommit(false);
            if (sender_account_number != 0 && receiver_account_number != 0) {
                System.out.print("Введите сумму: ");
                double amount = input.nextDouble();
                Double current_balance = getBalance(sender_account_number, security_pin);
                if (current_balance != null) {
                    if (amount <= current_balance) {
                        int rowSender = updateBalance(amount, sender_account_number, "-");
                        int rowReceiver = updateBalance(amount, receiver_account_number, "+");
                        if (rowSender > 0 && rowReceiver > 0) {
                            System.out.println("Транзакция прошла успешно!\nРУБЛИ: " + amount + " переведено успешно");
                            double balance = getBalance(sender_account_number, security_pin);
                            System.out.println("Ваш баланс: " + balance);
                            connection.commit();
                            connection.setAutoCommit(true);
                        } else {
                            System.out.println("Транзакция не удалась");
                            System.out.println("Неверный номер счета");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Недостаточно средств");
                    }
                } else {
                    System.out.println("Неверный PIN-код");
                }
            }
        } catch (SQLException e) {
            System.out.println("Транзакция не удалась");
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void getGeneralBalance(long account_number) {
        input.nextLine();
        System.out.print("Введите PIN-код: ");
        String security_pin = input.nextLine();
        try {
            Double current_balance = getBalance(account_number, security_pin);
            if (current_balance != null) {
                System.out.println("Баланс: " + current_balance);
            } else {
                System.out.println("Неверный PIN-код");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
