package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends  Thread {
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String outputString = input.readLine();
                if (outputString == null || outputString.equals("exit")) {
                    break;
                }
                printToAllClients(outputString);
                System.out.println("Server received: " + outputString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                threadList.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printToAllClients(String outputString) {
        for (ServerThread sT : threadList) {
            sT.output.println(outputString);
        }
    }
}
