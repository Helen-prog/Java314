package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args) {
        DatagramSocket datagramSocket;
        Scanner input = new Scanner(System.in);
        try{
            datagramSocket = new DatagramSocket();
            while (true) {
                System.out.print("Введите сообщение серверу: ");
                String s = input.nextLine();
                byte[] b = s.getBytes();
                DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("localhost"), 4567);
                datagramSocket.send(dp);

                byte[] buf = new byte[1024];
                DatagramPacket d = new DatagramPacket(buf, b.length); // b.length
                datagramSocket.receive(d);
                byte[] data = d.getData();
                s = new String(data, 0, d.getLength());

                System.out.println("Сервер: " + d.getAddress().getHostAddress() + " порт " + d.getPort() + " получено: " + s);
            }
        } catch (IOException e){
            System.err.println(e);
        }

    }
}
