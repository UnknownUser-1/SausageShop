package sausegeShop.Fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sausegeShop.controllers.CategoryController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import serverShit.Message;

public class AdminMenu {

    private static CategoryController categoryController = CategoryController.getInstance();
    ArrayList<Category> data = categoryController.getCategories();
    private ObjectOutputStream OOS;

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
    private Button saveData;

    @FXML
    void initialize() {
        goToUser();
        addNewProduct();
        deleteOldProduct();
        addNewCategory();
        deleteOldCategory();
        showProduct();
        showCategories();
        saveDataInFile();
    }

    private void showCategories() {
        showAllCategories.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
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
            showSome.getChildren().clear();
            for (int i = 0; i < categoryController.size(); i++) {
                for (int j = 0; j < categoryController.getCategory(i).getSize(); j++) {
                    Button product = new Button(categoryController.getCategory(i).getProduct(j).getName());
                    int finalI = i;
                    int finalJ = j;
                    product.setOnAction(actionEvent1 -> {
                        showSome.getChildren().clear();
                        Label name = new Label("Название: " + categoryController.getCategory(finalI).getProduct(finalJ).getName());
                        Label price = new Label("Цена: " + categoryController.getCategory(finalI).getProduct(finalJ).getPrice());
                        Label description = new Label("Описание: " + categoryController.getCategory(finalI).getProduct(finalJ).getDescription());
                        Label composition = new Label("Состав: " + categoryController.getCategory(finalI).getProduct(finalJ).getComposition());
                        Button back = new Button("Выход");
                        back.setOnAction(actionEvent2 -> {
                            showSome.getChildren().clear();
                        });
                        showSome.getChildren().addAll(name, price, description, composition, back);
                    });
                    showSome.getChildren().add(product);
                }
            }
        });
    }

    private void deleteOldCategory() {
        deleteCategory.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
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
            showSome.getChildren().clear();
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
            showSome.getChildren().clear();
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
            Button add = new Button("Добваить");
            showSome.getChildren().add(add);
            add.setOnAction(actionEvent1 -> {
                if (!name.getText().equals("") && !price.getText().equals("") && !description.getText().equals("") && !composition.getText().equals("")) {
                    categoryController.getCategory(Integer.parseInt(String.valueOf(finalJ))).addProduct(Product.productFactory(name.getText(), Double.parseDouble(price.getText()), description.getText(), composition.getText(), categoryController.getCategory(Integer.parseInt(String.valueOf(finalJ)))));
                    showSome.getChildren().clear();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setContentText("Такой товар уже сущестсвует");
                    alert.showAndWait();
                }
            });
        });
    }

    private void deleteOldProduct() {
        deleteProduct.setOnAction(actionEvent
                -> {
            showSome.getChildren().clear();
            Label label = new Label("Выберите от куда надо удалить");
            showSome.getChildren().add(label);
            for (int i = 0; i < categoryController.size(); i++) {

                Button category = new Button(categoryController.getCategory(i).getTitle());
                int finalI = i;
                category.setOnAction(actionEvent1
                        -> {
                    for (int j = 0; j < categoryController.getCategory(finalI).getSize(); j++) {
                        Button button = new Button(categoryController.getCategory(finalI).getProduct(j).getName());
                        int finalJ = j;
                        button.setOnAction(actionEvent2
                                -> {
                            categoryController.getCategory(finalI).deleteProduct(finalJ);
                            showSome.getChildren().clear();
                        });
                        showSome.getChildren().add(button);
                    }
                });
                showSome.getChildren().add(category);
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
            MainMenu mm = loader.getController();
            mm.setOutputStream(OOS);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    public void setStream(ObjectOutputStream OOS) {
        this.OOS = OOS;
    }

    private void saveDataInFile() {
        saveData.setOnAction(actionEvent -> {
            try {
                OOS.writeObject(new Message(data, 0));
                OOS.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
