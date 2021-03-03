package sausegeShop.Fx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sausegeShop.UserComparator;
import sausegeShop.controllers.BasketController;
import sausegeShop.controllers.CategoryController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import serverSide.Message;

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

    @FXML
    private MenuButton filterMenu;

    @FXML
    private Label whatSortShow;

    private ObjectOutputStream objOutStr;
    private ObjectInputStream objInStr;
    private final CategoryController categoryController = CategoryController.getInstance();
    private final BasketController basketController = BasketController.getInstance();
    ArrayList<Category> data = categoryController.getCategories();
    private static final int length = 375;
    private static final int width = 50;

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

    private void findProduct() {
        find.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            String product = textToFind.getText();
            ArrayList<Product> sProd;
            sProd = categoryController.search(product);
            for (int j = 0; j < sProd.size(); j++) {
                Button button = new Button(sProd.get(j).getName() + "    " + sProd.get(j).getPrice());
                Product prod = sProd.get(j);
                button.setMinSize(length, width);
                button.setOnAction(actionEvent1 -> {
                    showSome.getChildren().clear();
                    showOneProduct(prod);
                });
                showSome.getChildren().add(button);
            }
        });
    }

    private void showBasket() {
        cart.setOnAction(e -> {
            showSome.getChildren().clear();
            if (basketController.getBasket().size() == 0) {
                Label label = new Label("В коризине пусто");
                showSome.getChildren().add(label);
            } else {
                for (int i = 0; i < basketController.getBasket().size(); i++) {
                    VBox product = new VBox();
                    Label name = new Label("Название: " + basketController.getBasket().getProducts(i).getName());
                    Label price = new Label("Цена: " + basketController.getBasket().getProducts(i).getPrice());
                    Label description = new Label("Описание: " + basketController.getBasket().getProducts(i).getDescription());
                    Label composition = new Label("Состав: " + basketController.getBasket().getProducts(i).getComposition());
                    Label count = new Label("Количество: " + basketController.getBasket().getCount(i));
                    Label rat = new Label("Рейтинг: " + basketController.getBasket().getRat(i));
                    Button remove = new Button("Удалить");
                    product.getChildren().addAll(name, price, description, composition, count, rat, remove);
                    remove.setOnAction(actionEvent -> {
                        if (basketController.getBasket().size() == 1) {
                            showSome.getChildren().clear();
                        }
                        for (int j = 0; j < basketController.getBasket().size(); j++) {
                            if (name.equals(basketController.getBasket().getProducts(j).getName())) {
                                basketController.getBasket().delete(j);
                            }
                        }
                        product.getChildren().clear();
                    });
                    showSome.getChildren().add(product);
                }
                Button buyAll = new Button("Купить все");
                buyAll.setOnAction(actionEvent -> {
                    try {
                        objOutStr.writeObject(new Message(1));
                        objOutStr.flush();
                        if (((Message) objInStr.readObject()).getMessageType() == 0) {
                            for (int i = 0; i < basketController.getBasket().size(); i++) {
                                basketController.getBasket().getProducts(i).setRating(basketController.getBasket().getRat(i));
                            }
                            objOutStr.writeObject(new Message(0));
                            objOutStr.flush();
                            objOutStr.writeObject(new Message(data, 0));
                            objOutStr.flush();
                            alertWindow("Спасибо", "Спасибо за покупку");
                        } else {
                            alertWindow("Не спасибо", "Вы не успели купить наше мясо, валите");
                            objOutStr.writeObject(new Message(1));
                            objOutStr.flush();
                            data = ((Message) (objInStr.readObject())).getData();
                            categoryController.setCategories(data);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    basketController.getBasket().deleteAll();
                    showSome.getChildren().clear();
                });
                showSome.getChildren().add(buyAll);
            }
        });
    }

    private void goToAdminMenu() {
        adminMenu.setOnAction(e -> {
            ((Stage) adminMenu.getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PasswordCheck.fxml"));

            try {
                loader.load();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            Parent root = loader.getRoot();
            PasswordCheck pc = loader.getController();
            pc.setStream(objOutStr);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private void showAllCategories() {
        categories.setOnAction(e -> {
            filterCategory();
            showSome.getChildren().clear();
            for (int i = 0; i < categoryController.size(); i++) {
                Button category = new Button();
                int categoryIndex = i;
                category.setOnAction(event -> {
                    showSome.getChildren().clear();
                    showProducts(categoryController.getCategory(categoryIndex));
                });
                category.setId(String.valueOf(i));
                category.setText(categoryController.getCategory(i).getTitle() + "     количество товаров: " + categoryController.getCategory(i).getSize());
                category.setMinSize(length, width);
                showSome.getChildren().add(i, category);
            }
        });
    }

    private void showProducts(Category category) {
        showSome.getChildren().clear();
        filterProduct(category);
        for (int i = 0; i < category.getProducts().size(); i++) {
            Button product = new Button();
            int productIndex = i;
            product.setOnAction(actionEvent -> {
                showSome.getChildren().clear();
                showOneProduct(category.getProduct(productIndex));
            });
            product.setId(String.valueOf(i));
            product.setText(category.getProduct(i).getName() + "       " + category.getProduct(i).getPrice());
            product.setMinSize(length, width);
            showSome.getChildren().add(i, product);
        }
    }

    private void showOneProduct(Product product) {
        showSome.getChildren().clear();
        whatSortShow.setText(" ");
        Label name = new Label("Название: " + product.getName());
        Label price = new Label("Цена: " + product.getPrice());
        Label description = new Label("Описание: " + product.getDescription());
        Label composition = new Label("Состав: " + product.getComposition());
        Label rating = new Label("Рейтинг: " + product.getRating());
        TextField count = new TextField();
        TextField rat = new TextField();
        count.setPromptText("Введите количество");
        rat.setPromptText("Введите рейтинг");
        Button buy = new Button("Купить");
        Button back = new Button("Назад");
        showSome.getChildren().addAll(name, price, description, composition, rating, count, rat, buy, back);
        buy.setOnAction(actionEvent -> {
            if (!count.getText().isEmpty() && !rat.getText().isEmpty() && checkInt(rat.getText()) && checkInt(count.getText())) {
                basketController.getBasket().add(product, Integer.parseInt(count.getText()), Integer.parseInt(rat.getText()));
                alertWindow("Спасибо", "Товар добавлен в корзину");
                showSome.getChildren().remove(0, 9);
            } else {
                alertWindow("Внимание", "Добавьте рейтинг или же количество");
            }
        });
        back.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            showProducts(product.getCategory());
        });
    }

    private void filterCategory() {
        whatSortShow.setText(" ");
        MenuItem filterName = new MenuItem("По названию от А до Я");
        MenuItem filterNameReversed = new MenuItem("По названию от Я до А");
        MenuItem filterCount = new MenuItem("По количеству от 0 до 100");
        MenuItem filterCountReversed = new MenuItem("По количеству от 100 до 0");
        filterMenu.getItems().clear();
        filterMenu.getItems().addAll(filterName, filterNameReversed, filterCount, filterCountReversed);
        filterName.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по названию категорий от А до Я");
            showAllCategories(UserComparator.compareCategoryName());
        });
        filterCount.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по количеству продуктов в категории от 0 до 100");
            showAllCategories(UserComparator.compareCategoryCount());
        });
        filterCountReversed.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по количеству продуктов в категории от 100 до 0");
            showAllCategories(UserComparator.reversedCompareCategoryCount());
        });
        filterNameReversed.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по названию категорий от Я до А");
            showAllCategories(UserComparator.reversedCompareCategoryName());
        });
    }

    private void filterProduct(Category category) {
        whatSortShow.setText(" ");
        MenuItem filterName = new MenuItem("По названию от А до Я");
        MenuItem filterNameReversed = new MenuItem("По названию от Я до А");
        MenuItem filterCount = new MenuItem("По цене от 0 до 100");
        MenuItem filterCountReversed = new MenuItem("По цене от 100 до 0");
        filterMenu.getItems().clear();
        filterMenu.getItems().addAll(filterName, filterNameReversed, filterCount, filterCountReversed);
        filterName.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по названию продуктов от А до Я");
            showProducts(UserComparator.compareProductName(category));
        });
        filterCount.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по цене товаров от 0 до 100");
            showProducts(UserComparator.compareProductPrice(category));
        });
        filterCountReversed.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по цене товаров от 100 до 0");
            showProducts(UserComparator.reversedCompareProductPrice(category));
        });
        filterNameReversed.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            whatSortShow.setText("Сортировка по названию продуктов от Я до А");
            showProducts(UserComparator.reversedCompareProductName(category));
        });
    }

    private void showAllCategories(ArrayList<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            Button category = new Button();
            int categoryIndex = i;
            category.setOnAction(event -> {
                showSome.getChildren().clear();
                showProducts(categories.get(categoryIndex));
            });
            category.setId(String.valueOf(i));
            category.setText(categories.get(categoryIndex).getTitle() + "     количество товаров: " + categories.get(i).getSize());
            category.setMinSize(length, width);
            showSome.getChildren().add(i, category);
        }
    }

    private void showProducts(ArrayList<Product> products) {
        showSome.getChildren().clear();
        for (int i = 0; i < products.size(); i++) {
            Button product = new Button();
            int productIndex = i;
            product.setOnAction(actionEvent -> {
                showSome.getChildren().clear();
                showOneProduct(products.get(productIndex));
            });
            product.setId(String.valueOf(i));
            product.setText(products.get(i).getName() + "       " + products.get(i).getPrice());
            product.setMinSize(length, width);
            showSome.getChildren().add(i, product);
        }
    }

    public void setOutputStream(ObjectOutputStream OOS) {
        this.objOutStr = OOS;
    }

    public void setInputStream(ObjectInputStream OIS) {
        this.objInStr = OIS;
    }

    private void alertWindow(String title, String contextTitle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contextTitle);
        alert.showAndWait();
    }

    private boolean checkInt(String doubleString) {
        try {
            int parseInt = Integer.parseInt(doubleString);
            return parseInt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
