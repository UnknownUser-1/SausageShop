/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausegeShop.Fx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import sausegeShop.Serialize;
import sausegeShop.models.Category;
import serverShit.ConnectionUser;

/**
 *
 * @author pro56
 */
public class FXClient extends Application {

    private ConnectionUser connection = new ConnectionUser();

    @Override
    public void start(Stage stage) throws IOException, Exception {
//        FXMLLoader loader = new FXMLLoader();
//        URL xmlUrl = getClass().getResource("/fxml/source.fxml");
//        loader.setLocation(xmlUrl);
//        Parent root = loader.load();
//        stage.setTitle("Мясной уровень интернета");
//        InputStream iconStream = getClass().getResourceAsStream("/pct/sos.png");
//        Image image = new Image(iconStream);
//        stage.getIcons().add(image);
//        stage.setScene(new Scene(root));
//        stage.show();
        ArrayList<String> str = null;
        ArrayList<Category> tmp = Serialize.deserializeDatabase(connection.acquire());
        for (int i = 0; i < tmp.size(); i++) {
            str.add(tmp.get(i).getTitle());
        }
        ObservableList<String> langs = FXCollections.observableArrayList(str);
        ListView<String> langsListView = new ListView<String>(langs);

        FlowPane root = new FlowPane(langsListView);
        Scene scene = new Scene(root, 250, 200);

        stage.setScene(scene);
        stage.setTitle("ListView in JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws IOException, Exception {
        connection.startConnection();
    }

    @Override
    public void stop() throws Exception {
        connection.closeConnection();
    }

}
