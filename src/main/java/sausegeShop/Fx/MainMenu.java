package sausegeShop.Fx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sausegeShop.Serialize;
import sausegeShop.controllers.BasketController;
import sausegeShop.controllers.CategoryController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu {

    @FXML
    private Button adminMenu;

    @FXML
    private Button exit;

    @FXML
    private Button categories;

    @FXML
    private VBox showSome;

    private final CategoryController categoryController = new CategoryController();
    private final BasketController basketController = new BasketController();

    @FXML
    void initialize() {
        goToAdminMenu();
        exitWindow();
        showAllCategories();

    }

    private void exitWindow() {
        exit.setOnAction(e -> {
            Platform.exit();
        });
    }

    private void goToAdminMenu() {
        adminMenu.setOnAction(e -> {
            adminMenu.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PasswordCheck.fxml"));

            try {
                loader.load();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        exitWindow();
    }

    private void showAllCategories() {
        categories.setOnAction(e -> {
            for (int i = 0; i < categoryController.size(); i++) {
                Button button = new Button();
                int finalI = i;
                button.setOnAction(event -> {
                    showProducts(categoryController.getCategory(finalI));
                });
                button.setId(String.valueOf(i));
                button.setText(categoryController.getCategory(i).getTitle());
                button.setMinSize(375, 50);
                showSome.getChildren().add(i, button);
            }
        });
    }

    private void showProducts(Category category) {
        showSome.getChildren().remove(0,categoryController.size());
        for (int i = 0; i < category.getProducts().size(); i++) {
            Button button = new Button();
            int finalI = i;
            button.setOnAction(actionEvent -> {
                showOneProduct(category.getProduct(finalI));
            });
            button.setId(String.valueOf(i));
            button.setText(category.getProduct(i).getName() + "       " + category.getProduct(i).getPrice());
            button.setMinSize(375, 50);
            showSome.getChildren().add(i, button);
        }
    }

    private void showOneProduct(Product product){
        showSome.getChildren().remove(0,product.getCategory().getSize());
        Label name = new Label("Название: "+product.getName());
        Label price = new Label("Цена: "+ product.getPrice());
        Label description = new Label("Описание: " + product.getDescription());
        Label composition = new Label("Состав: " + product.getComposition());
        TextField count = new TextField();
        TextField rat = new TextField();
        count.setPromptText("Введите количество");
        rat.setPromptText("Введите рейтинг");
        Button buy = new Button("Купить");
        Button back = new Button("Назад");
        showSome.getChildren().addAll(name,price,description,composition,count,rat,buy,back);
        buy.setOnAction(actionEvent -> {
            basketController.getBasket().add(product,Integer.parseInt(count.getText()),Integer.parseInt(rat.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Спасибо");
            alert.setContentText("Това добавлен в корзину");
            alert.showAndWait();
            showAllCategories();
        });
        back.setOnAction(actionEvent -> {
            showProducts(product.getCategory());
        });
    }
}
