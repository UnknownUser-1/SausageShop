package sausegeShop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import sausegeShop.controllers.BasketController;
import sausegeShop.controllers.CategoryController;
import sausegeShop.controllers.ProductController;
import sausegeShop.models.Basket;
import sausegeShop.models.Category;
import sausegeShop.models.Product;
import java.util.Scanner;

public class Console {

    static CategoryController categoryController = new CategoryController();
    static BasketController basketController = new BasketController(new Basket());
    static ProductController productController = new ProductController();

    public static void main(String[] args) throws IOException {
        predMainMenu();
    }

    public static void firstLaunch() throws FileNotFoundException, IOException {
        categoryController = new CategoryController();
        productController = new ProductController();
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
        productController.addProduct(sausage,0);
        productController.addProduct(cervelat,1);
        productController.addProduct(cervelat2,2);
        productController.addProduct(jerky,3);
        productController.addProduct(jerky2,4);
        productController.addProduct(jerky3,5);
        try (
                FileOutputStream fos = new FileOutputStream("out.bin")) {
            Serialize.serializeDatabase(categoryController, fos);
        }
        selectionMenu();
    }

    public static void selectionMenu(){
        System.out.println("");
        System.out.println("Кем бы вы хотели зайти в систему?");
        System.out.println("1. Администратор");
        System.out.println("2. Пользователь");
        System.out.println("3. Ой а что я тут делаю");
        switch (new Scanner(System.in).nextInt()){
            case (1):
                adminMenu(1);
                break;
            case (2):
                userMenu();
                break;
            case (3):
                System.exit(0);
                break;
        }
    }

    public static void adminMenu(int whatTime){
        if (whatTime==1){
            System.out.println("");
            System.out.println("Введите пароль");
            if (new Scanner(System.in).nextInt()== 1337)
                adminMenu(2);
            else
                selectionMenu();
        }
        System.out.println("");
        System.out.println("Приветсвуем тебя Dungeon Master");
        System.out.println("Что хотите сделать?");
        System.out.println("1. Добавить категорию");
        System.out.println("2. Удалить категорию");
        System.out.println("3. Добавить товар");
        System.out.println("4. Удалить товар");
        System.out.println("5. Показать все товары");
        System.out.println("6. Показать все категории");
        System.out.println("7. Вернуться на меню выше");
        switch (new Scanner(System.in).nextInt()){
            case (1):
                changeCategories(1);
                break;
            case (2):
                changeCategories(2);
                break;
            case (3):
                changeProduct(1);
                break;
            case (4):
                changeProduct(2);
                break;
            case (5):
                showAllProduct();
                break;
            case (6):
                showAllCategories();
                break;
            case (7):
                selectionMenu();
                break;
        }
    }

    public static void changeCategories(int whatAction){
        if(whatAction == 1){
            System.out.println("");
            System.out.println("Введите название категории");
            categoryController.addCategories(new Category(new Scanner(System.in).next()),categoryController.size());
            adminMenu(2);
        } else if(whatAction == 2){
            System.out.println("");
            System.out.println("Введите номер категории которую хотите удалить");
            for(int i = 0; i<categoryController.size();i++){
                System.out.println(i+". "+ categoryController.getCategories(i).getTitle());
            }
            categoryController.deleteCategories(new Scanner(System.in).nextInt());
            adminMenu(2);
        }
    }

    public static void changeProduct(int whatAction){
        if(whatAction == 1){
            System.out.println("");
            System.out.print("Введите название товара:");
            String name = new Scanner(System.in).next();
            System.out.print("Введите цену товара:");
            double price = new Scanner(System.in).nextDouble();
            System.out.print("Введите описание товара:");
            String description = new Scanner(System.in).next();
            System.out.print("Введите состав товара:");
            String composition = new Scanner(System.in).next();
            System.out.println("Выберите к какой категории отнести товар");
            for(int i = 0; i<categoryController.size();i++){
                System.out.println(i+". "+ categoryController.getCategories(i).getTitle());
            }
            Category category = categoryController.getCategories(new Scanner(System.in).nextInt());
            productController.addProduct(Product.productFactory(name,price,description,composition,category), productController.size());
            adminMenu(2);
        }
        else if(whatAction == 2){
            System.out.println("");
            System.out.println("Введите номер товара который хотите удалить");
            for(int i = 0; i<productController.size();i++){
                System.out.println(i+". "+ productController.getProduct(i).getName());
            }
            productController.deleteProduct(new Scanner(System.in).nextInt());
            adminMenu(2);
        }
    }

    public static void showAllProduct(){
        System.out.println("");
        System.out.println("Все товары");
        for(int i=0;i<productController.size();i++)
            System.out.println(i+". "+ productController.getProduct(i).getName()+" " + productController.getProduct(i).getPrice()
            + " " + productController.getProduct(i).getDescription() + " " + productController.getProduct(i).getComposition()
            + " " + productController.getProduct(i).getCategoryTitle());
        adminMenu(2);
    }

    public static void showAllCategories(){
        System.out.println("");
        System.out.println("Все категории");
        for(int k = 0; k<categoryController.size();k++)
            System.out.println(k+". "+ categoryController.getCategories(k).getTitle());
        System.out.println("");
        System.out.println("Подроднее о категориях");
        for(int i = 0; i<categoryController.size();i++){
            System.out.println(i+". "+ categoryController.getCategories(i).getTitle());
            if(categoryController.getCategories(i).getSize() == 0)
                System.out.println("В данной категории не содержится товаров");
            else {
                System.out.println("В данной категории содержится");
                for (int j = 0; j < categoryController.getCategories(i).getSize(); j++) {
                    System.out.println(j + ". " + categoryController.getCategories(i).getProduct(j).getName());
                }
            }
            System.out.println("");
        }
        adminMenu(2);
    }

    public static void secondLaunch() throws FileNotFoundException, IOException {

        try (FileInputStream fis = new FileInputStream("out.bin")) {
            categoryController = Serialize.deserializeDatabase(fis);
        }
        selectionMenu();
    }

    public static void predMainMenu() throws IOException {
        System.out.println("");
        System.out.println("Что делать то!!!");
        System.out.println("Введите число");
        System.out.println("1. Первый запуск");
        System.out.println("2. Подгрузить базу");
        switch (new Scanner(System.in).nextInt()) {
            case (1):
                firstLaunch();
                break;
            case (2):
                secondLaunch();
                break;
        }
    }

    public static void userMenu() {
        System.out.println("");
        System.out.println("Добро пожаловать в мясной уровень интернета!!!");
        System.out.println("Введите число");
        System.out.println("1. Категории");
        System.out.println("2. Корзина");
        System.out.println("3. Выход");
        System.out.println("4. Вернуться на уровень выше");
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
            case(4):
                selectionMenu();
                break;
        }
    }

    public static void productMenu(int numberProduct, int numberCategory) {
        System.out.println("");
        categoryController.getCategories(numberCategory).getProducts().get(numberProduct).print();
        // product.getProduct(numberProduct).print();
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
                // basket.getBasket().add(product.getProduct(numberProduct), count, rat);
//Изменил, чтобы всё работало. Не уверен как это сделать с контроллерами
                basketController.getBasket().add(categoryController.getCategories(numberCategory).getProducts().get(numberProduct), count, rat);
                basketMenu();
                break;
        }
    }

    public static void categoryMenu() {
        System.out.println("");
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < categoryController.size(); i++) {
            g++;
            System.out.println(g + ". " + categoryController.getCategories(i).getTitle());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            userMenu();
        } else {
            realCategoryMenu(key - 2);
        }
    }

    public static void realCategoryMenu(int category) {
        System.out.println("");
        System.out.println(categoryController.getCategories(category).getTitle());
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < categoryController.getCategories(category).getProducts().size(); i++) {
            g++;
            System.out.println(g + ". " + categoryController.getCategories(category).getProducts().get(i).getName());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            categoryMenu();
        } else {
            productMenu(key - 2, category);
        }
    }

    public static void basketMenu() {
        System.out.println("");
        basketController.getBasket().print();
        System.out.println("Общая цена:                                   " + basketController.getBasket().getPrice());
        System.out.println("1. Главное меню");
        System.out.println("2. Купить");
        System.out.println("Изменить что-то:");
        basketController.getBasket().printUser();
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                userMenu();
                break;
            case (2):
                basketController.getBasket().purchase();
                System.out.println("Пасиба!!");
                basketController.getBasket().deleteAll();
                userMenu();
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
                basketController.getBasket().changeCount(indx, new Scanner(System.in).nextInt());
                basketMenu();
                break;
            case (3):
                System.out.println("Введите оценку");
                basketController.getBasket().changeRating(indx, new Scanner(System.in).nextDouble());
                basketMenu();
                break;
        }
    }
}
