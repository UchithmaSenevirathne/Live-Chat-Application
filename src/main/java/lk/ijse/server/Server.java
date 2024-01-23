package lk.ijse.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3002);
            System.out.println("Server started!");

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("new Client connected!");

                ClientHandler clientHandler = new ClientHandler(socket, clients);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
