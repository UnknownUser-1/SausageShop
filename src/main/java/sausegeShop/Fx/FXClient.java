/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausegeShop.Fx;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sausegeShop.controllers.CategoryController;
import sausegeShop.models.Category;
import serverShit.Message;

public class FXClient extends Application {

    private Stage primaryStage;
    private static CategoryController categoryController = CategoryController.getInstance();
    static ArrayList<Category> data;
    private ObjectInputStream OIS;
    private ObjectOutputStream OOS;
    private Socket socket;

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
        mm.setOutputStream(OOS);
        mm.setInputStream(OIS);
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
        OIS.close();
        OOS.close();
        socket.close();
    }

    @Override
    public void init() throws IOException, ClassNotFoundException {
        socket = new Socket("localhost", 5000);
        OIS = new ObjectInputStream(socket.getInputStream());
        OOS = new ObjectOutputStream(socket.getOutputStream());
        data = ((Message) (OIS.readObject())).getData();
        categoryController.setCategories(data);
    }

}
