/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausegeShop.Fx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sausegeShop.controllers.CategoryController;
import sausegeShop.controllers.ProductController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;


public class FXClient extends Application {

    private Stage primaryStage;
    private static CategoryController categoryController = CategoryController.getInstance();
    private static ProductController productController = ProductController.getInstance();


    public static void main(String[] args) {
        Category kolbasky = new Category("Колбаски");
        Category meat = new Category("Мяско");
        categoryController.addCategories(kolbasky, 0);
        categoryController.addCategories(meat, 1);
        Product sausage = Product.productFactory("Сосиски", 100, "Небольшие вкусные штучки", "100% курица", kolbasky);
        Product cervelat = Product.productFactory("Сервелат", 500, "Классная копченая колбаска", "Кто-то умер, чтобы попасть туда", kolbasky);
        Product cervelat2 = Product.productFactory("Останки финна", 280, "Откопанный из вечной мерзлоты солдат после Советско-Финской", "Чистокровный финн", kolbasky);
        Product jerky = Product.productFactory("Вяленое мясо", 800, "Оно вкусное", "200% вяленого мяса", meat);
        Product jerky2 = Product.productFactory("Копченное мясо", 683, "Оно стоит 683 рубля", "Его ингридиенты стоили 683 рубля", meat);
        Product jerky3 = Product.productFactory("Мяско для шашлычка", 550, "Шашлычка для пивка", "Лотерея: говядина или свинина или сюрприз", meat);
        productController.addProduct(sausage, 0);
        productController.addProduct(cervelat, 1);
        productController.addProduct(cervelat2, 2);
        productController.addProduct(jerky, 3);
        productController.addProduct(jerky2, 4);
        productController.addProduct(jerky3, 5);
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Интернет магазин мясных изделий");
        showMainView();
    }
    
    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainMenu.fxml"));
        Parent mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
