/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverShit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import sausegeShop.Serialize;

/**
 * @author pro56
 */
public class Server implements Runnable {

    private final int port;
    private Context context;

    public static void main(String[] args) throws Exception {
        Server ser = new Server();
        ser.run();
    }

    public Server() throws FileNotFoundException, IOException {
        this.port = 5000;
        try (FileInputStream fis = new FileInputStream("out.bin")) {
            this.context = new Context(Serialize.deserializeDatabase(fis));
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);
            //Цикл ожидания подключений
            while (!this.context.changed) {
                System.out.println("Waiting connection on port:" + this.port);
                //Момент ухода в ожидание подключения
                Socket clientSocket = ss.accept();
                System.out.println("New client connected to server");
                //Создается клиентская сессия
                ClientSession clientSession = new ClientSession(clientSocket, this.context);
                this.context.getSessionsManger().addSession(clientSession);
                //Запуск логики работы с клиентом
                clientSession.run();
            }

            ss.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {

        }
    }
}
