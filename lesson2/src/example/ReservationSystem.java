package example;

import java.sql.*;
import java.util.Scanner;

public class ReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hotel";
    private static final String username = "root";
    private static final String password = "123456";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println("\nСИСТЕМА БРОНИРОВАНИЯ ОТЕЛЯ");
                Scanner input = new Scanner(System.in);
                System.out.println("1. Бронирование номера");
                System.out.println("2. Просмотр бронирования");
                System.out.println("3. Уточнить номер комнаты");
                System.out.println("4. Обновить бронирование");
                System.out.println("5. Удалить бронирование");
                System.out.println("0. Выход");
                System.out.print("Выберите опцию: ");
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(connection, input);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, input);
                        break;
                    case 4:
                        updateReservation(connection, input);
                        break;
                    case 5:
                        deleteReservation(connection, input);
                        break;
                    case 0:
                        exit();
                        input.close();
                        connection.close();
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reserveRoom(Connection connection, Scanner input) {
        try {
            System.out.print("Введите имя гостя: ");
            String guestName = input.next();
            input.nextLine();
            System.out.print("Введите количество гостей: ");
            int roomNumber = input.nextInt();
            System.out.print("Введите контактный номер: ");
            String contactNumber = input.next();

            String sql = "INSERT INTO resrevations (guest_name, room_number, contact_number) VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";
            try (Statement statement = connection.createStatement()) {
                int affectsRows = statement.executeUpdate(sql);

                if (affectsRows > 0) {
                    System.out.println("Бронирование успешно!");
                } else {
                    System.out.println("Бронирование не удалось");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewReservations(Connection connection) throws SQLException {
        String sql = "SELECT * FROM resrevations";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            System.out.println("Текущее бронирование:");
            System.out.println("+----------------------------+-----------------+---------------+------------------+-----------------------+");
            System.out.println("| Идентификатор бронирования | Гость           | Кол-во гостей | Контактный номер | Дата бронирования     |");
            System.out.println("+----------------------------+-----------------+---------------+------------------+-----------------------+");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("resrevation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("resrevation_date").toString();
                System.out.printf("| %-26d | %-15s | %-13d | %-16s | %-21s |%n", reservationId, guestName, roomNumber, contactNumber, reservationDate);
                System.out.println("+----------------------------+-----------------+---------------+------------------+-----------------------+");
            }
        }

    }

    public static void getRoomNumber(Connection connection, Scanner input) throws SQLException {
        System.out.print("Введите идентификатор бронирования: ");
        int reservationId = input.nextInt();
        System.out.print("Введите имя гостя: ");
        String guestName = input.next();

        String sql = "SELECT room_number FROM resrevations WHERE resrevation_id = " + reservationId + " AND guest_name = '" + guestName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                System.out.println("Номер комнаты для идентификатора бронирования " + reservationId + " и гостя " + guestName + " является: " + roomNumber);
            } else {
                System.out.println("Бронирование не найдено для данного идентификатора и имени гостя.");
            }
        }
    }

    public static void updateReservation(Connection connection, Scanner input) throws SQLException {
        System.out.print("Введите идентификатор бронирования для обновления: ");
        int reservationId = input.nextInt();
        input.nextLine();

        if (!reservationExists(connection, reservationId)) {
            System.out.println("Бронирование не найдено для данного идентификатора");
            return;
        }

        System.out.print("Введите имя нового гостя: ");
        String newGuestName = input.nextLine();
        System.out.print("Введите новый номер комнаты: ");
        int newRoomNumber = input.nextInt();
        System.out.print("Введите новый контактный номер: ");
        String newContactNumber = input.next();

        String sql = "UPDATE resrevations SET guest_name = '" + newGuestName + "', room_number = " + newRoomNumber + ", contact_number = '" + newContactNumber + "' WHERE resrevation_id = " + reservationId;

        try (Statement statement = connection.createStatement()){
            int row = statement.executeUpdate(sql);
            if(row > 0){
                System.out.println("Бронирование успешно обновлено!");
            } else {
                System.out.println("Обновление резервирования не удалось");
            }
        }
    }

    public static void deleteReservation(Connection connection, Scanner input) throws SQLException {
        System.out.print("Введите идентификатор бронирования для удаления: ");
        int reservationId = input.nextInt();

        if (!reservationExists(connection, reservationId)) {
            System.out.println("Бронирование не найдено для данного идентификатора");
            return;
        }

        String sql = "DELETE FROM resrevations WHERE resrevation_id = " + reservationId;

        try (Statement statement = connection.createStatement()){
            int row = statement.executeUpdate(sql);
            if(row > 0){
                System.out.println("Бронирование успешно удалено!");
            } else {
                System.out.println("Удаление резервирования не удалось");
            }
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {
        try {
            String sql = "SELECT resrevation_id FROM resrevations WHERE resrevation_id = " + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql);) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.println("Выход из системы");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println("\nСпасибо за использование системы бронирования отеля!");
    }
}
