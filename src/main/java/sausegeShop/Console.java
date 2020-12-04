
package sausegeShop;;

import sausegeShop.controllers.BasketController;
import sausegeShop.controllers.CategoryController;
import sausegeShop.controllers.ProductController;
import sausegeShop.models.Basket;
import sausegeShop.models.Category;
import sausegeShop.models.Product;
import java.util.Scanner;

public class Console {

    static CategoryController categories = new CategoryController();
    static BasketController basket = new BasketController(new Basket());
    static ProductController product = new ProductController();

    public static void main(String[] args) {
        Category kolbasky = new Category("Колбаски");
        Category meat = new Category("Мяско");
        categories.addCategories(kolbasky,0);
        categories.addCategories(meat,1);
        Product sausage = Product.productFactory("Сосиски", 100, "Небольшие вкусные штучки", "100% курица", kolbasky);
        Product cervelat = Product.productFactory("Сервелат", 500, "Классная копченая колбаска", "Кто-то умер, чтобы попасть туда", kolbasky);
        Product jerky = Product.productFactory("Вяленое мясо", 800, "Оно вкусное", "200% вяленого мяса", meat);
        product.addProduct(sausage,0);
        product.addProduct(cervelat,1);
        product.addProduct(jerky,2);
        mainMenu();

    }

    public static void mainMenu() {
        System.out.println("");
        System.out.println("Добро пожаловать в мясной уровень интернета!!!");
        System.out.println("Введите число");
        System.out.println("1. Категории");
        System.out.println("2. Корзина");
        System.out.println("3. Выход");
        switch (new Scanner(System.in).nextInt()) {
            case (1):
                categoryMenu();
                break;
            case (2):
                basketMenu();
                break;
            case (3):
                System.exit(0);
                break;
        }
    }

    public static void productMenu(int numberProduct, int numberCategory) {
        System.out.println("");
        product.getProduct(numberProduct).print();
        System.out.println("");
        System.out.println("1. Назад");
        System.out.println("2. Добавить в корзину");
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                realCategoryMenu(numberCategory);
                break;
            case (2):
                System.out.println("Введит количество");
                int count = new Scanner(System.in).nextInt();
                System.out.println("Введите вашу оценку");
                double rat = new Scanner(System.in).nextDouble();
                basket.getBasket().add(product.getProduct(numberProduct), count, rat);
                basketMenu();
                break;
        }
    }

    public static void categoryMenu() {
        System.out.println("");
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < categories.size(); i++) {
            g++;
            System.out.println(g + ". " + categories.getCategories(i).getTitle());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            mainMenu();
        } else {
            // cat.get(key - 2).getTitle();//.print();
            realCategoryMenu(key - 2);
        }
    }

    public static void realCategoryMenu(int category) {
        System.out.println("");
        System.out.println(categories.getCategories(category).getTitle());
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < categories.getCategories(category).getProducts().size(); i++) {
            g++;
            System.out.println(g + ". " + categories.getCategories(category).getProducts().get(i).getName());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            mainMenu();
        } else {
            //cat.getProducts().get(key - 2).print();
            productMenu(key - 2,category);
        }
    }

    public static void basketMenu() {
        System.out.println("");
        basket.getBasket().print();
        System.out.println("Общая цена:                                   " + basket.getBasket().getPrice());
        System.out.println("1. Главное меню");
        System.out.println("2. Купить");
        System.out.println("Изменить что-то:");
        int g = 2;
        for (int i = 0; i < basket.getBasket().getBasket().size(); i++) {
            g++;
            //System.out.println(g + ". " + basket.getBasket().getBasket().get(i).getProduct().getName()); У меня здесь баг не видит доступ к методу getProduct
        }
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                mainMenu();
                break;
            case (2):
                basket.getBasket().purchase();
                System.out.println("Пасиба!!");
                mainMenu();
                basket.setBasket(new Basket());
                break;
            default:
                productBasket(key - 3);
        }
    }

    public static void productBasket(int indx) {
        System.out.println("");
        System.out.println("1. Назад");
        System.out.println("2. Изменить количество");
        System.out.println("3. Изменить оценку");
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                basketMenu();
                break;
            case (2):
                System.out.println("Введите количество");
                basket.getBasket().changeCount(indx, new Scanner(System.in).nextInt());
                basketMenu();
                break;
            case (3):
                System.out.println("Введите оценку");
                basket.getBasket().changeRating(indx, new Scanner(System.in).nextDouble());
                basketMenu();
                break;
        }
    }
}
