package sausageShop.Fx;

import javafx.scene.image.Image;
import sausageShop.Fx.AdminMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PasswordCheck {

    private ObjectOutputStream objOutStr;
    private static final int WIDTH = 650;
    private static final int HEIGHT = 400;

    @FXML
    private Button enter;

    @FXML
    private Button back;

    @FXML
    private PasswordField enterPassword;

    @FXML
    private Label showMessage;

    @FXML
    void initialize() {
        enter.setOnAction(e -> {
            if (enterPassword.getText().equals("1337")) {
                ((Stage)enter.getScene().getWindow()).close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/AdminMenu.fxml"));

                try {
                    loader.load();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                Parent root = loader.getRoot();
                AdminMenu AM = loader.getController();
                AM.setStream(objOutStr);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.getIcons().add(new Image("/pct/sos.png"));
                stage.setTitle("Меню Администратора");
                stage.setResizable(false);
                stage.setHeight(HEIGHT);
                stage.setWidth(WIDTH);
            } else {
                showMessage.setText("Пароль неверный");
            }
        });
        back.setOnAction(e -> {
            ((Stage)back.getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MainMenu.fxml"));

            try {
                loader.load();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            Parent root = loader.getRoot();
            MainMenu mm = loader.getController();
            mm.setOutputStream(objOutStr);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.getIcons().add(new Image("/pct/sos.png"));
            stage.setTitle("Интернет магазин мясных изделий");
            stage.setResizable(false);
            stage.setHeight(HEIGHT);
            stage.setWidth(WIDTH);
        });
    }

    public void setStream(ObjectOutputStream OOS) {
        this.objOutStr = OOS;
    }
}
