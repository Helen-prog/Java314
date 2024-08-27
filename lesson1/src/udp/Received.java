package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Received {
    public static void main(String[] args) {
        try {
            DatagramSocket dSocket = new DatagramSocket(4567);
            byte[] bytes = new byte[1024];  // 1024
            DatagramPacket dPacket = new DatagramPacket(bytes, bytes.length);
            System.out.println("Ожидаем данные");

            while (true){
                dSocket.receive(dPacket);
                byte[] data = dPacket.getData();
                String s = new String(data, 0, dPacket.getLength());
                System.out.println("Сервер получил: " + s);
                DatagramPacket dp = new DatagramPacket(data, data.length, dPacket.getAddress(), dPacket.getPort());
                dSocket.send(dp);
            }
        } catch (IOException e){
            System.err.println(e);
        }
    }
}
