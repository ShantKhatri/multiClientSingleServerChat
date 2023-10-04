package server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        ArrayList<ServerThread> threadList = new ArrayList<>();

        try {
            InetAddress serverAddress = InetAddress.getByName("172.23.16.1"); // Replace with the actual server IP
            int port = 5000;
            ServerSocket serverSocket = new ServerSocket(port, 0, serverAddress);

            System.out.println("Server started on " + serverAddress.getHostAddress() + ":" + port);

            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, threadList);
                threadList.add(serverThread);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
