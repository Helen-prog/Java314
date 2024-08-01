package clientServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final int serverPort = 5555;
    private static final String localhost = "127.0.0.1";

    public static void main(String[] args) {
        Socket socket = null;
        try{
            try{
                System.out.println("Добро пожаловать на клиентскую строну\nПодключение к серверу\n\t(IP адрес: " + localhost + ", порт: " + serverPort + ")");
                InetAddress ipAddress = InetAddress.getByName(localhost);
                socket = new Socket(ipAddress, serverPort);
                System.out.println("Соединение установлено");
                System.out.println("\tАдрес хоста = " + socket.getInetAddress().getHostAddress() + "\tРазмер буфера = " + socket.getReceiveBufferSize());

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader keyboard = new BufferedReader(isr);

                String line;
                while (true){
                    System.out.print("Введите что-нибудь и нажмите Enter: ");
                    line = keyboard.readLine();
                    out.writeUTF(line);
                    out.flush();
                    line = in.readUTF();

                    if(line.endsWith("quit")){
                        break;
                    } else{
                        System.out.println("\nСервер отправил мне эту строку:\n\t" + line);
                        System.out.println();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } finally {
            try{
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
