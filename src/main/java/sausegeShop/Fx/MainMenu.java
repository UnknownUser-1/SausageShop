package sausegeShop.Fx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sausegeShop.controllers.BasketController;
import sausegeShop.controllers.CategoryController;
import sausegeShop.controllers.ProductController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.io.IOException;
import java.util.regex.Pattern;

public class MainMenu {

    @FXML
    private Button adminMenu;

    @FXML
    private Button exit;

    @FXML
    private Button categories;

    @FXML
    private VBox showSome;

    @FXML
    private Button cart;

    @FXML
    private Button find;

    @FXML
    private TextField textToFind;

    private final CategoryController categoryController = CategoryController.getInstance();
    private final BasketController basketController = BasketController.getInstance();
    private final ProductController productController = ProductController.getInstance();

    @FXML
    void initialize() {
        goToAdminMenu();
        exitWindow();
        showAllCategories();
        showBasket();
        findProduct();
    }

    private void exitWindow() {
        exit.setOnAction(e -> {
            Platform.exit();
        });
    }

    private void findProduct(){
        find.setOnAction(actionEvent -> {
            String product = textToFind.getText();
            if (product.contains("?")) {
                String actualSearch = product.substring(0, product.indexOf("?"));
                actualSearch = actualSearch + ".+.";
                for (int i = 0; i < productController.size(); i++) {
                    if (Pattern.matches(actualSearch, productController.getProduct(i).getName())) {
                        Button button = new Button(productController.getProduct(i).getName()+ "    "+ productController.getProduct(i).getPrice());
                        button.setMinSize(375, 50);
                        int finalI = i;
                        button.setOnAction(actionEvent1 -> {
                            showOneProduct(productController.getProduct(finalI));
                        });
                        showSome.getChildren().add(button);
                    }
                }
            } else {
                for (int i = 0; i < productController.size(); i++) {
                    if (productController.getProduct(i).getName().toLowerCase().contains(product.toLowerCase())) {
                        Button button = new Button(productController.getProduct(i).getName()+ "    "+ productController.getProduct(i).getPrice());
                        int finalI = i;
                        button.setMinSize(375, 50);
                        button.setOnAction(actionEvent1 -> {
                            showOneProduct(productController.getProduct(finalI));
                        });
                        showSome.getChildren().add(button);
                    }
                }
            }
        });
    }

    private void showBasket() {
        showSome.getChildren().clear();
        cart.setOnAction(e -> {
            for (int i = 0; i < basketController.getBasket().size(); i++) {
                Label name = new Label("Название: " + basketController.getBasket().getProducts(i).getName());
                Label price = new Label("Цена: " + basketController.getBasket().getProducts(i).getPrice());
                Label description = new Label("Описание: " + basketController.getBasket().getProducts(i).getDescription());
                Label composition = new Label("Состав: " + basketController.getBasket().getProducts(i).getComposition());
                Label count = new Label("Количество: " + basketController.getBasket().getCount(i));
                Label rat = new Label("Рейтинг: " + basketController.getBasket().getRat(i));
                Button remove = new Button("Удалить");
                int finalI = i;
                //Здесь происходит удаление товара, неправильно убирает надписи
                remove.setOnAction(actionEvent -> {
                    basketController.getBasket().delete(finalI);
                    if (finalI == 0)
                        showSome.getChildren().remove(0, 7);
                    else
                        showSome.getChildren().remove(7 * finalI, 7 * finalI + 7);
                });
                showSome.getChildren().addAll(name, price, description, composition, count, rat, remove);
            }
            Button buyAll = new Button("Купить все");
            buyAll.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Спасибо");
                alert.setContentText("Спаибо за покупку");
                alert.showAndWait();
                basketController.getBasket().deleteAll();
            });
            showSome.getChildren().add(buyAll);
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
        showSome.getChildren().clear();
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
        showSome.getChildren().clear();
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

    private void showOneProduct(Product product) {
        showSome.getChildren().clear();
        Label name = new Label("Название: " + product.getName());
        Label price = new Label("Цена: " + product.getPrice());
        Label description = new Label("Описание: " + product.getDescription());
        Label composition = new Label("Состав: " + product.getComposition());
        TextField count = new TextField();
        TextField rat = new TextField();
        count.setPromptText("Введите количество");
        rat.setPromptText("Введите рейтинг");
        Button buy = new Button("Купить");
        Button back = new Button("Назад");
        showSome.getChildren().addAll(name, price, description, composition, count, rat, buy, back);
        buy.setOnAction(actionEvent -> {
            if (!count.getText().equals("") && !rat.getText().equals("")) {
                basketController.getBasket().add(product, Integer.parseInt(count.getText()), Integer.parseInt(rat.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Спасибо");
                alert.setContentText("Товар добавлен в корзину");
                alert.showAndWait();
                showSome.getChildren().remove(0, 8);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setContentText("Добавьте рейтинг или же количество");
                alert.showAndWait();
            }
        });
        back.setOnAction(actionEvent -> {
            showProducts(product.getCategory());
        });
    }
}
