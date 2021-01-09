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
        productController.addProduct(sausage, 0);
        productController.addProduct(cervelat, 1);
        productController.addProduct(cervelat2, 2);
        productController.addProduct(jerky, 3);
        productController.addProduct(jerky2, 4);
        productController.addProduct(jerky3, 5);
        try (
                FileOutputStream fos = new FileOutputStream("out.bin")) {
            Serialize.serializeDatabase(categoryController, fos);
        }
        selectionMenu();
    }

    public static void selectionMenu() throws FileNotFoundException, IOException {
        System.out.println("");
        System.out.println("Кем бы вы хотели зайти в систему?");
        System.out.println("1. Администратор");
        System.out.println("2. Пользователь");
        System.out.println("3. Ой а что я тут делаю");
        switch (checkNumber()) {
            case (1):
                adminMenu(1);
                break;
            case (2):
                userMenu();
                break;
            case (3):
                System.exit(0);
                break;
            default:
                System.out.println("Введите число из списка ");
                selectionMenu();
                break;
        }
    }

    public static void adminMenu(int whatTime) throws FileNotFoundException, IOException {
        if (whatTime == 1) {
            System.out.println("");
            System.out.println("Введите пароль");
            if (new Scanner(System.in).nextInt() == 1337) {
                adminMenu(2);
            } else {
                selectionMenu();
            }
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
        System.out.println("7. Сохранить данный набор товаров и категорий");
        System.out.println("8. Загрузить последний набор товаров и категорий");
        System.out.println("9. Вернуться на меню выше");
        switch (checkNumber()) {
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
                try (FileOutputStream fos = new FileOutputStream("out.bin")) {
                    Serialize.serializeDatabase(categoryController, fos);
                }
                break;
            case (8):
                try (FileInputStream fis = new FileInputStream("out.bin")) {
                    Serialize.deserializeDatabase(categoryController, fis);
                }
                break;
            case (9):
                selectionMenu();
                break;
            default:
                System.out.println("Введите число из списка");
                adminMenu(2);
                break;
        }
    }

    public static void changeCategories(int whatAction) throws FileNotFoundException, IOException {
        if (whatAction == 1) {
            System.out.println("");
            System.out.println("Введите название категории");
            categoryController.addCategories(new Category(new Scanner(System.in).nextLine()), categoryController.size());
            adminMenu(2);
        } else if (whatAction == 2) {
            System.out.println("");
            System.out.println("Введите номер категории которую хотите удалить");
            for (int i = 0; i < categoryController.size(); i++) {
                System.out.println(i + ". " + categoryController.getCategory(i).getTitle());
            }
            categoryController.deleteCategories(new Scanner(System.in).nextInt());
            adminMenu(2);
        }
    }

    public static void changeProduct(int whatAction) throws IOException {
        Scanner sc = new Scanner(System.in);
        if (whatAction == 1) {
            System.out.println("");
            System.out.print("Введите цену товара:");
            double price = new Scanner(System.in).nextDouble();
            System.out.print("Введите название товара:");
            String name = new Scanner(System.in).nextLine();
            System.out.print("Введите описание товара:");
            String description = new Scanner(System.in).nextLine();
            System.out.print("Введите состав товара:");
            String composition = new Scanner(System.in).nextLine();
            System.out.println("Выберите к какой категории отнести товар");
            for (int i = 0; i < categoryController.size(); i++) {
                System.out.println(i + ". " + categoryController.getCategory(i).getTitle());
            }
            if (price < 0) {
                System.out.println("Введите не отрицательную цену");
                changeProduct(1);
            }
            else {
                Category category = categoryController.getCategory(new Scanner(System.in).nextInt());
                productController.addProduct(Product.productFactory(name, price, description, composition, category), productController.size());
                adminMenu(2);
            }
        } else if (whatAction == 2) {
            System.out.println("");
            System.out.println("Введите номер товара который хотите удалить");
            for (int i = 0; i < productController.size(); i++) {
                System.out.println(i + ". " + productController.getProduct(i).getName());
            }
            productController.deleteProduct(new Scanner(System.in).nextInt());
            adminMenu(2);
        }
    }

    public static void showAllProduct() throws IOException {
        System.out.println("");
        System.out.println("Все товары");
        for (int i = 0; i < productController.size(); i++) {
            System.out.println(i + ". " + productController.getProduct(i).getName() + " " + productController.getProduct(i).getPrice()
                    + " " + productController.getProduct(i).getDescription() + " " + productController.getProduct(i).getComposition()
                    + " " + productController.getProduct(i).getCategoryTitle());
        }
        adminMenu(2);
    }

    public static void showAllCategories() throws IOException {
        System.out.println("");
        System.out.println("Все категории");
        for (int k = 0; k < categoryController.size(); k++) {
            System.out.println(k + ". " + categoryController.getCategory(k).getTitle());
        }
        System.out.println("");
        System.out.println("Подроднее о категориях");
        for (int i = 0; i < categoryController.size(); i++) {
            System.out.println(i + ". " + categoryController.getCategory(i).getTitle());
            if (categoryController.getCategory(i).getSize() == 0) {
                System.out.println("В данной категории не содержится товаров");
            } else {
                System.out.println("В данной категории содержится");
                for (int j = 0; j < categoryController.getCategory(i).getSize(); j++) {
                    System.out.println(j + ". " + categoryController.getCategory(i).getProduct(j).getName());
                }
            }
            System.out.println("");
        }
        adminMenu(2);
    }

    public static void secondLaunch() throws IOException {
        try (FileInputStream fis = new FileInputStream("out.bin")) {
            Serialize.deserializeDatabase(categoryController, fis);
        }
        selectionMenu();
    }

    public static void predMainMenu() throws IOException {
        System.out.println("");
        System.out.println("Что делать то!!!");
        System.out.println("Введите число");
        System.out.println("1. Первый запуск");
        System.out.println("2. Подгрузить базу");
        switch (checkNumber()) {
            case (1):
                firstLaunch();
                break;
            case (2):
                secondLaunch();
                break;
            default:
                System.out.println("Введите число из списка");
                predMainMenu();
                break;
        }
    }

    public static void userMenu() throws IOException {
        System.out.println("");
        System.out.println("Добро пожаловать в мясной уровень интернета!!!");
        System.out.println("Введите число");
        System.out.println("1. Категории");
        System.out.println("2. Корзина");
        System.out.println("3. Поиск по товарам");
        System.out.println("4. Выход");
        System.out.println("5. Вернуться на уровень выше");
        switch (checkNumber()) {
            case (1):
                categoryMenu();
                break;
            case (2):
                basketMenu();
                break;
            case (3):
                searchProductMenu();
                break;
            case (4):
                System.exit(0);
                break;
            case (5):
                selectionMenu();
                break;
            default:
                System.out.println("Введите число из списка");
                userMenu();
                break;
        }
    }

    public static void searchProductMenu() throws IOException {
        System.out.println("");
        System.out.print("Введите название продукта который хотите найти: ");
        String productToSearch = new Scanner(System.in).next();
        for (int i = 0; i < productController.size(); i++) {
            if (productController.getProduct(i).getName().toLowerCase().contains(productToSearch.toLowerCase())) {
                System.out.println(i + ". " + productController.getProduct(i).getName());
            }
        }
        System.out.println("0. Вернуться назад");
        System.out.print("Укажите номер конкретного товара: ");
        int numberProduct = new Scanner(System.in).nextInt();
        if (numberProduct == 0) {
            userMenu();
        }
        productMenu(numberProduct);
    }

    public static void productMenu(int numberProduct) throws IOException {
        int count;
        double rat;
        System.out.println("");
        productController.getProduct(numberProduct).print();
        // product.getProduct(numberProduct).print();
        System.out.println("");
        System.out.println("1. В меню");
        System.out.println("2. Добавить в корзину");
        int key = checkNumber();
        switch (key) {
            case (1):
                userMenu();
                break;
            case (2):
                do {
                    System.out.println("Введите количество");
                    count = new Scanner(System.in).nextInt();
                } while (count>0);
                do {
                    System.out.println("Введите вашу оценку");
                    rat = new Scanner(System.in).nextDouble();
                } while(rat>0);
                // basket.getBasket().add(product.getProduct(numberProduct), count, rat);
                //�������, ����� �� ��������. �� ������ ��� ��� ������� � �������������
                basketController.getBasket().add(productController.getProduct(numberProduct), count, rat);
                basketMenu();
                break;
            default:
                System.out.println("Введите число из списка");
                productMenu(numberProduct);
                break;
        }
    }

    public static void productMenu(int numberProduct, int numberCategory) throws IOException {
        System.out.println("");
        categoryController.getCategory(numberCategory).getProducts().get(numberProduct).print();
        // product.getProduct(numberProduct).print();
        System.out.println("");
        System.out.println("1. Назад");
        System.out.println("2. Добавить в корзину");
        int key = checkNumber();
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
                basketController.getBasket().add(categoryController.getCategory(numberCategory).getProducts().get(numberProduct), count, rat);
                basketMenu();
                break;
            default:
                System.out.println("Введите число из списка");
                productMenu(numberProduct, numberCategory);
                break;
        }
    }

    public static void categoryMenu() throws IOException {
        System.out.println("");
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < categoryController.size(); i++) {
            g++;
            System.out.println(g + ". " + categoryController.getCategory(i).getTitle());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            userMenu();
        } else {
            realCategoryMenu(key - 2);
        }
    }

    public static void realCategoryMenu(int category) throws IOException {
        System.out.println("");
        System.out.println(categoryController.getCategory(category).getTitle());
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < categoryController.getCategory(category).getProducts().size(); i++) {
            g++;
            System.out.println(g + ". " + categoryController.getCategory(category).getProducts().get(i).getName());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            categoryMenu();
        } else {
            productMenu(key - 2, category);
        }
    }

    public static void basketMenu() throws IOException {
        System.out.println("");
        basketController.getBasket().print();
        System.out.println("Общая цена:                                   " + basketController.getBasket().getPrice());
        System.out.println("1. Главное меню");
        System.out.println("2. Купить");
        System.out.println("3. Изменить что-то:");
        basketController.getBasket().printUser();
        int key = checkNumber();
        switch (key) {
            case (1):
                userMenu();
                break;
            case (2):
                basketController.getBasket().purchase();
                System.out.println("������!!");
                basketController.getBasket().deleteAll();
                userMenu();
                break;
            default:
                productBasket(key - 3);
        }
    }

    public static void productBasket(int indx) throws IOException {
        System.out.println("");
        System.out.println("1. Назад");
        System.out.println("2. Изменить количество");
        System.out.println("Изменить оценку");
        int key = checkNumber();
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
            default:
                System.out.println("Введите число из списка ");
                productBasket(indx);
                break;
        }
    }

    public static int checkNumber() {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            System.out.println("Вы ввели не целое число");
        }
        return 99999999;
    }
}
