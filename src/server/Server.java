package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class Server extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[6];
    private RobotController controller;

    public Server(RobotController controller) {
        try {
            socket = new DatagramSocket(3000);
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.controller = controller;
    }

    public void run() {
        System.out.println("Server aberto");
        while (!Thread.currentThread().isInterrupted()) {
            try {

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                controller.receiveInput(packet.getData());
                System.out.println(Arrays.toString(packet.getData()));
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server fechado");
    }

}
