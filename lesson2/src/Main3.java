import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class Main3 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/users1";
        String username = "root";
        String password = "123456";
//        String image_path = "D:\\Java314\\OIP.jpg";
//        String query = "INSERT INTO image_table(image_data) VALUES(?)";
        String folder_path = "D:\\Java314\\SavedPictures\\";
        String query = "SELECT image_data FROM image_table WHERE image_id = (?)";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
//            FileInputStream fileInputStream = new FileInputStream(image_path);
//            byte[] imageData = new byte[fileInputStream.available()];
//            fileInputStream.read(imageData);
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setBytes(1, imageData);
//
//            int row = preparedStatement.executeUpdate();
//            if(row > 0){
//                System.out.println("Изображение добавлено успешно");
//            } else {
//                System.out.println("Изображение не добавлено");
//            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] image_data = resultSet.getBytes("image_data");
                String image_path = folder_path + "extractedImage.jpg";
                OutputStream outputStream = new FileOutputStream(image_path);
                outputStream.write(image_data);
            } else {
                System.out.println("Изображение не найдено");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
