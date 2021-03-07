/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.logging.*;

import sausageShop.Serialize;

/**
 * @author pro56
 */
public class Server implements Runnable {

    private int port;
    private Context context;

    public static void main(String[] args) throws Exception {
        createDbUserTable();
        Server ser = new Server();
        ser.run();
    }


    public Server() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDbUserTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;

        String createTableSQL =
                "CREATE TABLE PRODUCT(" +
                        "NAME        VARCHAR," +
                        "PRICE       DOUBLE PRECISION," +
                        "DESCRIPTION VARCHAR," +
                        "COMPOSITION VARCHAR, " +
                        "CATEGORY    VARCHAR," +
                        "RATING      DOUBLE PRECISION " +
                        " )";
        try {
            connection = getDBConnection();
            statement = connection.createStatement();

            // выполнить SQL запрос
            statement.execute(createTableSQL);
            System.out.println("Table \"product\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static Connection getDBConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
