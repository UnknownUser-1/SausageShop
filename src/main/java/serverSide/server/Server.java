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

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;


import sausageShop.Serialize;

/**
 * @author pro56
 */
public class Server implements Runnable {

    private int port;
    private Context context;

    public static void main(String[] args) throws Exception {
        try{
            createDbTables();
        }catch (NullPointerException e){
            System.out.println("Скорее всего мы не можем найти бд, проверьте хост и порт");
        }
        try {
            generateContentInDbTable();
        } catch (NullPointerException e) {
            System.out.println("Некуда добавлять конетент, скорее всего не подключена бд");;
        }
        Server ser = new Server();
        ser.run();
    }


    public Server() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        this.port = 4000;
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

    private static void createDbTables() throws SQLException, FileNotFoundException {
        Connection connection = getDBConnection();
        ScriptRunner sr = new ScriptRunner(connection);
        System.out.println("Инициализируем базу данных PostgreSQL...");
        Reader reader = new BufferedReader(new FileReader
                ("C:\\Users\\Sakura\\IdeaProjects\\SausageShop\\src\\main\\resources\\sqlScripts\\create_tables.sql"));
        try {
            sr.runScript(reader);
        } catch (RuntimeSqlException e){
            System.out.println("нету бд");
            return;
        }
        System.out.println("База данных успешно проинициализированна!");
    }

    private static void generateContentInDbTable() throws FileNotFoundException {
        Connection connection = getDBConnection();
        ScriptRunner sr = new ScriptRunner(connection);
        System.out.println("Заполняем таблицы...");
        Reader reader = new BufferedReader(new FileReader
                ("C:\\Users\\Sakura\\IdeaProjects\\SausageShop\\src\\main\\resources\\sqlScripts\\generate_content.sql"));
        try{
            sr.runScript(reader);
        } catch (RuntimeSqlException e){
            System.out.println("нету бд");
            return;
        }
        System.out.println("Таблицы заполнины исходными данными!");
    }

    private static Connection getDBConnection() {
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
