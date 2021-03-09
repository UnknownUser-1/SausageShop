/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausageShop.Fx;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sausageShop.controllers.CategoryController;
import sausageShop.models.Category;
import serverSide.Message;

public class FXClient extends Application {

    private Stage primaryStage;
    private static CategoryController categoryController = CategoryController.getInstance();
    static ArrayList<Category> data;
    private ObjectInputStream objInStr;
    private ObjectOutputStream objOutStr;
    private Socket socket;
    private static final int WIDTH = 650;
    private static final int HEIGHT = 400;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Интернет магазин мясных изделий");
        showMainView();

    }

    private void showMainView() throws IOException {
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent mainLayout = loader2.load();
        MainMenu mm = loader2.getController();
        mm.setOutputStream(objOutStr);
        mm.setInputStream(objInStr);
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image("/pct/sos.png"));
        primaryStage.setResizable(false);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setWidth(WIDTH);
    }

    @Override
    public void stop() throws IOException {
        objInStr.close();
        objOutStr.close();
        socket.close();
    }

    @Override
    public void init() throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", 5000);
        objInStr = new ObjectInputStream(socket.getInputStream());
        objOutStr = new ObjectOutputStream(socket.getOutputStream());
        data = ((Message) (objInStr.readObject())).getData();
        categoryController.setCategories(data);
    }

}
