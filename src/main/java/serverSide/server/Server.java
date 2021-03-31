/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;




import sausageShop.Serialize;
import serverSide.dao.PersistException;

/**
 * @author pro56
 */
public class Server implements Runnable {

    private int port;
    private Context context;

    public static void main(String[] args) throws Exception {
        Server ser = new Server();
        ser.run();
    }


    public Server() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, PersistException {
        this.port = 4000;
        try (FileInputStream fis = new FileInputStream("out.bin")) {
            this.context = new Context(Serialize.deserializeDatabase());
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);
            //Цикл ожидания подключений
            while (!this.context.stopFlag) {
                System.out.println("Waiting connection on port:" + this.port);
                //Момент ухода в ожидание подключения
                Socket clientSocket = ss.accept();
                System.out.println("New client connected to server");
                //Создается клиентская сессия
                ClientSession clientSession = new ClientSession(clientSocket, this.context);
                this.context.getSessionsManger().addSession(clientSession);
                //Запуск логики работы с клиентом
                clientSession.start();
            }
            ss.close();
            getDBConnection().close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?useUnicode=yes&characterEncoding=UTF-8", "postgres", "postgres");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
