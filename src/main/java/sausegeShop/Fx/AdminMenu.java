package sausegeShop.Fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sausegeShop.controllers.CategoryController;
import sausegeShop.controllers.ProductController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminMenu {

    private static CategoryController categoryController = CategoryController.getInstance();
    private static ProductController productController = ProductController.getInstance();
    private static final int length = 375;
    private static final int width = 50;

    @FXML
    private Button enterToUser;

    @FXML
    private VBox showSome;

    @FXML
    private Button addProduct;

    @FXML
    private Button deleteProduct;

    @FXML
    private Button addCategory;

    @FXML
    private Button deleteCategory;

    @FXML
    private Button showAllProducts;

    @FXML
    private Button showAllCategories;

    @FXML
    void initialize() {
        goToUser();
        addNewProduct();
        deleteOldProduct();
        addNewCategory();
        deleteOldCategory();
        showProduct();
        showCategories();
    }

    private void showCategories() {
        showAllCategories.setOnAction(actionEvent -> {
            for (int k = 0; k < categoryController.size(); k++) {
                Button category = new Button(categoryController.getCategory(k).getTitle());
                int finalK = k;
                category.setOnAction(actionEvent1 -> {
                    showSome.getChildren().clear();
                    for (int i = 0; i < categoryController.getCategory(finalK).getProducts().size(); i++) {
                        Button product = new Button(categoryController.getCategory(finalK).getProduct(i).getName());
                        int finalI = i;
                        product.setOnAction(actionEvent2 -> {
                            showSome.getChildren().clear();
                            Label name = new Label("Название: " + categoryController.getCategory(finalK).getProduct(finalI).getName());
                            Label price = new Label("Цена: " + categoryController.getCategory(finalK).getProduct(finalI).getPrice());
                            Label description = new Label("Описание: " + categoryController.getCategory(finalK).getProduct(finalI).getDescription());
                            Label composition = new Label("Состав: " + categoryController.getCategory(finalK).getProduct(finalI).getComposition());
                            Button back = new Button("Выход");
                            back.setOnAction(actionEvent3 -> {
                                showSome.getChildren().clear();
                            });
                            showSome.getChildren().addAll(name, price, description, composition, back);
                        });
                        showSome.getChildren().add(product);
                    }
                });
                showSome.getChildren().add(category);
            }
        });
    }

    private void showProduct() {
        showAllProducts.setOnAction(actionEvent -> {
            for (int i = 0; i < productController.size(); i++) {
                Button product = new Button(productController.getProduct(i).getName());
                int finalI = i;
                product.setOnAction(actionEvent1 -> {
                    showSome.getChildren().clear();
                    Label name = new Label("Название: " + productController.getProduct(finalI).getName());
                    Label price = new Label("Цена: " + productController.getProduct(finalI).getPrice());
                    Label description = new Label("Описание: " + productController.getProduct(finalI).getDescription());
                    Label composition = new Label("Состав: " + productController.getProduct(finalI).getComposition());
                    Button back = new Button("Выход");
                    back.setOnAction(actionEvent2 -> {
                        showSome.getChildren().clear();
                    });
                    showSome.getChildren().addAll(name, price, description, composition, back);
                });
                showSome.getChildren().add(product);
            }
        });
    }

    private void deleteOldCategory() {
        deleteCategory.setOnAction(actionEvent -> {
            for (int i = 0; i < categoryController.size(); i++) {
                Button button = new Button(categoryController.getCategory(i).getTitle());
                int finalI = i;
                button.setOnAction(actionEvent1 -> {
                    categoryController.deleteCategories(finalI);
                    showSome.getChildren().clear();
                });
                showSome.getChildren().add(button);
            }
        });
    }

    private void addNewCategory() {
        addCategory.setOnAction(actionEvent -> {
            TextField name = new TextField();
            name.setPromptText("Введите название категории");
            Button add = new Button("Добавить");
            add.setOnAction(actionEvent1 -> {
                categoryController.addCategories(new Category(name.getText()), categoryController.size());
                showSome.getChildren().clear();
            });
            showSome.getChildren().addAll(name, add);
        });
    }

    private void addNewProduct() {
        addProduct.setOnAction(actionEvent -> {
            AtomicInteger finalJ = new AtomicInteger();
            TextField name = new TextField();
            name.setPromptText("Введите название товара");
            TextField price = new TextField();
            price.setPromptText("Введите цену");
            TextField description = new TextField();
            description.setPromptText("Введите описание");
            TextField composition = new TextField();
            composition.setPromptText("Введите состав");
            Label cat = new Label("Выберите к какой категории отнести товар");
            showSome.getChildren().addAll(name, price, description, composition, cat);
            for (int i = 0; i < categoryController.size(); i++) {
                Button button = new Button(categoryController.getCategory(i).getTitle());
                int finalI = i;
                button.setOnAction(actionEvent1 -> {
                    finalJ.set(finalI);
                });
                showSome.getChildren().add(button);
            }
            Button add = new Button("Добавить");
            showSome.getChildren().add(add);
            add.setOnAction(actionEvent1 -> {
                if (!name.getText().equals("") && !price.getText().equals("") && !description.getText().equals("") && !composition.getText().equals("")) {
                    productController.addProduct(Product.productFactory(name.getText(), Double.parseDouble(price.getText()), description.getText(), composition.getText(), categoryController.getCategory(Integer.parseInt(String.valueOf(finalJ)))), productController.size());
                    showSome.getChildren().clear();
                }
            });
        });
    }

    private void deleteOldProduct() {
        deleteProduct.setOnAction(actionEvent -> {
            for (int i = 0; i < productController.size(); i++) {
                Button button = new Button(productController.getProduct(i).getName());
                int finalI = i;
                button.setOnAction(actionEvent1 -> {
                    productController.deleteProduct(finalI);
                    showSome.getChildren().clear();
                });
                showSome.getChildren().add(button);
            }
        });
    }

    private void goToUser() {
        enterToUser.setOnAction(e -> {
            enterToUser.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MainMenu.fxml"));

            try {
                loader.load();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setTitle("Интернет магазин мясных изделий");
        });
    }
}
