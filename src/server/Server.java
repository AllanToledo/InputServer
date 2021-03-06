package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[6];
    private RobotController controller;

    public Server(RobotController controller) {
        try {
            socket = new DatagramSocket(3000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.controller = controller;
    }

    public void run() {
        running = true;

        System.out.println("Server aberto");
        while (running) {
            try {

                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                controller.receiveInput(packet.getData());
                System.out.println(Arrays.toString(packet.getData()));

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (Exception | ThreadDeath e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
                running = false;
            }
        }
        System.out.println("Server fechado");
    }

}
