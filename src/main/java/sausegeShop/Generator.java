/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sausegeShop;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import sausegeShop.controllers.CategoryController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

/**
 *
 * @author pro56
 */

//Эта вещь нужна только для создания файла нужного формата. И ВСЁ!!!
public class Generator {

    static ArrayList<Category> data = new ArrayList<>();
    static CategoryController categoryController = CategoryController.getInstance();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        categoryController.setCategories(data);
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
        try (
                FileOutputStream fos = new FileOutputStream("out.bin")) {
            Serialize.serializeDatabase(data, fos);
        }
    }
}
