package sausageShop.Fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sausageShop.controllers.CategoryController;
import sausageShop.models.Category;
import sausageShop.models.Product;
import serverSide.server.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


public class AdminMenu {

    private static CategoryController categoryController = CategoryController.getInstance();
    private ObjectOutputStream objOutStr;
    private ObjectInputStream in ;
    private static final int WIDTH = 650;
    private static final int HEIGHT = 400;

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
    void initialize(){
        showCategories();
        showProduct();
        deleteOldCategory();
        addNewCategory();
        addNewProduct();
        deleteOldProduct();
        goToUser();
//        saveToXmlFile();
    }

    private void showCategories() {
        showAllCategories.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            for (int k = 0; k < categoryController.size(); k++) {
                Button category = new Button(categoryController.getCategory(k).getTitle());
                int categoryIndex = k;
                category.setOnAction(actionEvent1 -> {
                    showSome.getChildren().clear();
                    for (int i = 0; i < categoryController.getCategory(categoryIndex).getProducts().size(); i++) {
                        Button product = new Button(categoryController.getCategory(categoryIndex).getProduct(i).getName());
                        int productIndex = i;
                        product.setOnAction(actionEvent2 -> {
                            showSome.getChildren().clear();
                            Label name = new Label("Название: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getName());
                            Label price = new Label("Цена: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getPrice());
                            Label description = new Label("Описание: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getDescription());
                            Label composition = new Label("Состав: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getComposition());
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
                    int categoryIndex = i;
                    int productIndex = j;
                    product.setOnAction(actionEvent1 -> {
                        showSome.getChildren().clear();
                        Label name = new Label("Название: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getName());
                        Label price = new Label("Цена: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getPrice());
                        Label description = new Label("Описание: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getDescription());
                        Label composition = new Label("Состав: " + categoryController.getCategory(categoryIndex).getProduct(productIndex).getComposition());
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
                int categoryIndex = i;
                button.setOnAction(actionEvent1 -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Category");
                    alert.setHeaderText("Вы точно хотите удалить данную категорию?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get() == ButtonType.OK) {
                        try {
                            sqlCategorySaveAndGet(categoryController.getCategory(categoryIndex), 1);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        categoryController.deleteCategories(categoryIndex);
                        showSome.getChildren().clear();
                    }
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
                Category category = new Category(name.getText());
                try {
                    categoryController.addCategories(sqlCategorySaveAndGet(category, 0),categoryController.size());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                showSome.getChildren().clear();
            });
            showSome.getChildren().addAll(name, add);
        });
    }

    private void addNewProduct() {
        addProduct.setOnAction(actionEvent -> {
            showSome.getChildren().clear();
            AtomicInteger categoryIndex = new AtomicInteger();
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
                    categoryIndex.set(finalI);
                });
                showSome.getChildren().add(button);
            }
            Label info = new Label("Если уверены что выбрали все правильно, нажмите кнопку добавить");
            Button add = new Button("Добваить");
            showSome.getChildren().addAll(info, add);
            add.setOnAction(actionEvent1 -> {
                if (!name.getText().isEmpty() && !price.getText().isEmpty() && !description.getText().isEmpty() && !composition.getText().isEmpty()) {
                    if (checkPrice(price.getText())) {
                        Product product = Product.productFactory(name.getText(), Double.parseDouble(price.getText()), description.getText(), composition.getText(), categoryController.getCategory(categoryIndex.get()).getId());
                        categoryController.getCategory(categoryIndex.get()).addProduct(sqlProductSaveAndGet(product, 2));
                        showSome.getChildren().clear();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setContentText("Такой товар уже существует");
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
                int categoryIndex = i;
                category.setOnAction(actionEvent1
                        -> {
                    for (int j = 0; j < categoryController.getCategory(categoryIndex).getSize(); j++) {
                        Button button = new Button(categoryController.getCategory(categoryIndex).getProduct(j).getName());
                        int productIndex = j;
                        button.setOnAction(actionEvent2
                                -> {
                            sqlProductSaveAndGet(categoryController.getCategory(categoryIndex).getProduct(productIndex), 3);
                            categoryController.getCategory(categoryIndex).deleteProduct(productIndex);
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
            ((Stage) enterToUser.getScene().getWindow()).close();
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

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    private boolean checkPrice(String priceString) {
        try {
            double price = Double.parseDouble(priceString);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Category sqlCategorySaveAndGet(Category category, int messageType) throws IOException {
        Category cat = null;
        Message message = new Message();
        message.setCategory(category);
        message.setMessageType(messageType);
        try {
            objOutStr.writeObject(message);
            if(messageType ==0) {
                try {
                    cat = (Category) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cat;
    }

    private Product sqlProductSaveAndGet(Product product, int messageType) {
        Product pro = null;
        Message message = new Message();
        message.setProduct(product);
        message.setMessageType(messageType);
        try {
            objOutStr.writeObject(message);
            if(messageType==2) {
                try {
                    pro = (Product) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pro;
    }

    /*private void saveToXmlFile(){
        saveData.setOnAction(actionEvent -> {
            String filePath = "src\\main\\resources\\xml\\AllCategories";
            Serialize.convertObjectToXml(categoryController,filePath);
        });
    }*/
}
