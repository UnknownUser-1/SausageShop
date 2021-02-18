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
import serverShit.Message;

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

    private ObjectOutputStream OOS;
    private ObjectInputStream OIS;
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
            if (product.contains("?")) {
                String actualSearch = product.substring(0, product.indexOf("?"));
                actualSearch = actualSearch + ".+.";
                for (int j = 0; j < categoryController.size(); j++) {
                    for (int k = 0; k < categoryController.getCategory(j).getSize(); k++) {
                        if (Pattern.matches(actualSearch, categoryController.getCategory(j).getProduct(k).getName())) {
                            Button button = new Button(categoryController.getCategory(j).getProduct(k).getName() + "    " + categoryController.getCategory(j).getProduct(k).getName());
                            button.setMinSize(length, width);
                            int finalI = k;
                            int finalJ = j;
                            button.setOnAction(actionEvent1 -> {
                                showSome.getChildren().clear();
                                showOneProduct(categoryController.getCategory(finalJ).getProduct(finalI));
                            });
                            showSome.getChildren().add(button);
                        }
                    }
                }
            } else {
                for (int j = 0; j < categoryController.size(); j++) {
                    for (int k = 0; k < categoryController.getCategory(j).getSize(); k++) {
                        if (categoryController.getCategory(j).getProduct(k).getName().toLowerCase().contains(product.toLowerCase())) {
                            Button button = new Button(categoryController.getCategory(j).getProduct(k).getName() + "    " + categoryController.getCategory(j).getProduct(k).getPrice());
                            int finalI = k;
                            button.setMinSize(length, width);
                            int finalJ = j;
                            button.setOnAction(actionEvent1 -> {
                                showSome.getChildren().clear();
                                showOneProduct(categoryController.getCategory(finalJ).getProduct(finalI));
                            });
                            showSome.getChildren().add(button);
                        }
                    }

                }
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
                        OOS.writeObject(new Message(1));
                        OOS.flush();
                        if (((Message) OIS.readObject()).getMessageType() == 0) {
                            for (int i = 0; i < basketController.getBasket().size(); i++) {
                                basketController.getBasket().getProducts(i).setRating(basketController.getBasket().getRat(i));
                            }
                            OOS.writeObject(new Message(0));
                            OOS.flush();
                            OOS.writeObject(new Message(data, 0));
                            OOS.flush();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Спасибо");
                            alert.setContentText("Спаибо за покупку");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Не спасибо");
                            alert.setContentText("Вы не успели купить наше мясо, валите");
                            alert.showAndWait();
                            OOS.writeObject(new Message(1));
                            OOS.flush();
                            data = ((Message) (OIS.readObject())).getData();
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
            adminMenu.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PasswordCheck.fxml"));

            try {
                loader.load();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            Parent root = loader.getRoot();
            PasswordCheck pc = loader.getController();
            pc.setStream(OOS);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        exitWindow();
    }

    private void showAllCategories() {
        categories.setOnAction(e -> {
            filterCategory();
            showSome.getChildren().clear();
            for (int i = 0; i < categoryController.size(); i++) {
                Button category = new Button();
                int finalI = i;
                category.setOnAction(event -> {
                    showSome.getChildren().clear();
                    showProducts(categoryController.getCategory(finalI));
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
            int finalI = i;
            product.setOnAction(actionEvent -> {
                showSome.getChildren().clear();
                showOneProduct(category.getProduct(finalI));
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
            if (!count.getText().equals("") && !rat.getText().equals("")) {
                basketController.getBasket().add(product, Integer.parseInt(count.getText()), Integer.parseInt(rat.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Спасибо");
                alert.setContentText("Товар добавлен в корзину");
                alert.showAndWait();
                showSome.getChildren().remove(0, 9);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setContentText("Добавьте рейтинг или же количество");
                alert.showAndWait();
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
            int finalI = i;
            category.setOnAction(event -> {
                showSome.getChildren().clear();
                showProducts(categories.get(finalI));
            });
            category.setId(String.valueOf(i));
            category.setText(categories.get(finalI).getTitle() + "     количество товаров: " + categories.get(i).getSize());
            category.setMinSize(length, width);
            showSome.getChildren().add(i, category);
        }
    }

    private void showProducts(ArrayList<Product> products) {
        showSome.getChildren().clear();
        for (int i = 0; i < products.size(); i++) {
            Button product = new Button();
            int finalI = i;
            product.setOnAction(actionEvent -> {
                showSome.getChildren().clear();
                showOneProduct(products.get(finalI));
            });
            product.setId(String.valueOf(i));
            product.setText(products.get(i).getName() + "       " + products.get(i).getPrice());
            product.setMinSize(length, width);
            showSome.getChildren().add(i, product);
        }
    }

    public void setOutputStream(ObjectOutputStream OOS) {
        this.OOS = OOS;
    }

    public void setInputStream(ObjectInputStream OIS) {
        this.OIS = OIS;
    }
}
