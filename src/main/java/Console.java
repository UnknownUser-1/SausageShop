
package java;;

import java.util.ArrayList;
import java.util.Scanner;

public class Console {

    static ArrayList<Category> cat = new ArrayList<>();
    static Basket bsk = new Basket();

    public static void main(String[] args) {
        Category kolbasky = new Category("Колбаски");
        Category meat = new Category("Мяско");
        cat.add(kolbasky);
        cat.add(meat);
        Product sausage = Product.productFactory("Сосиски", 100, "Небольшие вкусные штучки", "100% курица", kolbasky);
        Product cervelat = Product.productFactory("Сервелат", 500, "Классная копченая колбаска", "Кто-то умер, чтобы попасть туда", kolbasky);
        Product jerky = Product.productFactory("Вяленое мясо", 800, "Оно вкусное", "200% вяленого мяса", meat);
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

    public static void productMenu(Product pr) {
        System.out.println("");
        pr.print();
        System.out.println("");
        System.out.println("1. Назад");
        System.out.println("2. Добавить в корзину");
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                realCategoryMenu(pr.getCategory());
                break;
            case (2):
                System.out.println("Введит количество");
                int count = new Scanner(System.in).nextInt();
                System.out.println("Введите вашу оценку");
                double rat = new Scanner(System.in).nextDouble();
                bsk.add(pr, count, rat);
                basketMenu();
                break;
        }
    }

    public static void categoryMenu() {
        System.out.println("");
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < cat.size(); i++) {
            g++;
            System.out.println(g + ". " + cat.get(i).getTitle());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            mainMenu();
        } else {
            // cat.get(key - 2).getTitle();//.print();
            realCategoryMenu(cat.get(key - 2));
        }
    }

    public static void realCategoryMenu(Category cat) {
        System.out.println("");
        System.out.println(cat.getTitle());
        System.out.println("1. Назад");
        int g = 1;
        for (int i = 0; i < cat.getProducts().size(); i++) {
            g++;
            System.out.println(g + ". " + cat.getProducts().get(i).getName());
        }
        int key = new Scanner(System.in).nextInt();
        if (key == 1) {
            mainMenu();
        } else {
            //cat.getProducts().get(key - 2).print();
            productMenu(cat.getProducts().get(key - 2));
        }
    }

    public static void basketMenu() {
        System.out.println("");
        bsk.print();
        System.out.println("Общая цена:                                   " + bsk.getPrice());
        System.out.println("1. Главное меню");
        System.out.println("2. Купить");
        System.out.println("Изменить что-то:");
        int g = 2;
        for (int i = 0; i < bsk.getBasket().size(); i++) {
            g++;
            System.out.println(g + ". " + bsk.getBasket().get(i).getP().getName());
        }
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                mainMenu();
                break;
            case (2):
                bsk.purchase();
                System.out.println("Пасиба!!");
                mainMenu();
                bsk = new Basket();
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
                bsk.changeCount(indx, new Scanner(System.in).nextInt());
                basketMenu();
                break;
            case (3):
                System.out.println("Введите оценку");
                bsk.changeRating(indx, new Scanner(System.in).nextDouble());
                basketMenu();
                break;
        }
    }
}
