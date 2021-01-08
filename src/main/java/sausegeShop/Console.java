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
        Category kolbasky = new Category("��������");
        Category meat = new Category("�����");
        categoryController.addCategories(kolbasky, 0);
        categoryController.addCategories(meat, 1);
        Product sausage = Product.productFactory("�������", 100, "��������� ������� ������", "100% ������", kolbasky);
        Product cervelat = Product.productFactory("��������", 500, "�������� �������� ��������", "���-�� ����, ����� ������� ����", kolbasky);
        Product cervelat2 = Product.productFactory("������� �����", 280, "���������� �� ������ �������� ������ ����� ��������-�������", "������������ ����", kolbasky);
        Product jerky = Product.productFactory("������� ����", 800, "��� �������", "200% �������� ����", meat);
        Product jerky2 = Product.productFactory("��������� ����", 683, "��� ����� 683 �����", "��� ����������� ������ 683 �����", meat);
        Product jerky3 = Product.productFactory("����� ��� ��������", 550, "�������� ��� �����", "�������: �������� ��� ������� ��� �������", meat);
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
        System.out.println("��� �� �� ������ ����� � �������?");
        System.out.println("1. �������������");
        System.out.println("2. ������������");
        System.out.println("3. �� � ��� � ��� �����");
        switch (new Scanner(System.in).nextInt()) {
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
                System.out.println("������� ����� ");
                selectionMenu();
                break;
        }
    }

    public static void adminMenu(int whatTime) throws FileNotFoundException, IOException {
        if (whatTime == 1) {
            System.out.println("");
            System.out.println("������� ������");
            if (new Scanner(System.in).nextInt() == 1337) {
                adminMenu(2);
            } else {
                selectionMenu();
            }
        }
        System.out.println("");
        System.out.println("����������� ���� Dungeon Master");
        System.out.println("��� ������ �������?");
        System.out.println("1. �������� ���������");
        System.out.println("2. ������� ���������");
        System.out.println("3. �������� �����");
        System.out.println("4. ������� �����");
        System.out.println("5. �������� ��� ������");
        System.out.println("6. �������� ��� ���������");
        System.out.println("7. ��������� ������ ����� ������� � ���������");
        System.out.println("8. ��������� ��������� ����� ������� � ���������");
        System.out.println("9. ��������� �� ���� ����");
        switch (new Scanner(System.in).nextInt()) {
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
                System.out.println("������� �����");
                adminMenu(2);
                break;
        }
    }

    public static void changeCategories(int whatAction) throws FileNotFoundException, IOException {
        if (whatAction == 1) {
            System.out.println("");
            System.out.println("������� �������� ���������");
            categoryController.addCategories(new Category(new Scanner(System.in).nextLine()), categoryController.size());
            adminMenu(2);
        } else if (whatAction == 2) {
            System.out.println("");
            System.out.println("������� ����� ��������� ������� ������ �������");
            for (int i = 0; i < categoryController.size(); i++) {
                System.out.println(i + ". " + categoryController.get�ategory(i).getTitle());
            }
            categoryController.deleteCategories(new Scanner(System.in).nextInt());
            adminMenu(2);
        }
    }

    public static void changeProduct(int whatAction) throws IOException {
        if (whatAction == 1) {
            System.out.println("");
            System.out.print("������� �������� ������:");
            String name = new Scanner(System.in).nextLine();
            System.out.print("������� ���� ������:");
            double price = new Scanner(System.in).nextDouble();
            System.out.print("������� �������� ������:");
            String description = new Scanner(System.in).nextLine();
            System.out.print("������� ������ ������:");
            String composition = new Scanner(System.in).nextLine();
            System.out.println("�������� � ����� ��������� ������� �����");
            for (int i = 0; i < categoryController.size(); i++) {
                System.out.println(i + ". " + categoryController.get�ategory(i).getTitle());
            }
            Category category = categoryController.get�ategory(new Scanner(System.in).nextInt());
            productController.addProduct(Product.productFactory(name, price, description, composition, category), productController.size());
            adminMenu(2);
        } else if (whatAction == 2) {
            System.out.println("");
            System.out.println("������� ����� ������ ������� ������ �������");
            for (int i = 0; i < productController.size(); i++) {
                System.out.println(i + ". " + productController.getProduct(i).getName());
            }
            productController.deleteProduct(new Scanner(System.in).nextInt());
            adminMenu(2);
        }
    }

    public static void showAllProduct() throws IOException {
        System.out.println("");
        System.out.println("��� ������");
        for (int i = 0; i < productController.size(); i++) {
            System.out.println(i + ". " + productController.getProduct(i).getName() + " " + productController.getProduct(i).getPrice()
                    + " " + productController.getProduct(i).getDescription() + " " + productController.getProduct(i).getComposition()
                    + " " + productController.getProduct(i).getCategoryTitle());
        }
        adminMenu(2);
    }

    public static void showAllCategories() throws IOException {
        System.out.println("");
        System.out.println("��� ���������");
        for (int k = 0; k < categoryController.size(); k++) {
            System.out.println(k + ". " + categoryController.get�ategory(k).getTitle());
        }
        System.out.println("");
        System.out.println("��������� � ����������");
        for (int i = 0; i < categoryController.size(); i++) {
            System.out.println(i + ". " + categoryController.get�ategory(i).getTitle());
            if (categoryController.get�ategory(i).getSize() == 0) {
                System.out.println("� ������ ��������� �� ���������� �������");
            } else {
                System.out.println("� ������ ��������� ����������");
                for (int j = 0; j < categoryController.get�ategory(i).getSize(); j++) {
                    System.out.println(j + ". " + categoryController.get�ategory(i).getProduct(j).getName());
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
        System.out.println("��� ������ ��!!!");
        System.out.println("������� �����");
        System.out.println("1. ������ ������");
        System.out.println("2. ���������� ����");
        switch (new Scanner(System.in).nextInt()) {
            case (1):
                firstLaunch();
                break;
            case (2):
                secondLaunch();
                break;
            default:
                System.out.println("������� �����");
                predMainMenu();
                break;
        }
    }

    public static void userMenu() throws IOException {
        System.out.println("");
        System.out.println("����� ���������� � ������ ������� ���������!!!");
        System.out.println("������� �����");
        System.out.println("1. ���������");
        System.out.println("2. �������");
        System.out.println("3. ����� �� �������");
        System.out.println("4. �����");
        System.out.println("5. ��������� �� ������� ����");
        switch (new Scanner(System.in).nextInt()) {
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
                System.out.println("������� �����");
                userMenu();
                break;
        }
    }

    public static void searchProductMenu() throws IOException {
        System.out.println("");
        System.out.print("������� �������� �������� ������� ������ �����: ");
        String productToSearch = new Scanner(System.in).next();
        for (int i = 0; i < productController.size(); i++) {
            if (productController.getProduct(i).getName().toLowerCase().contains(productToSearch.toLowerCase())) {
                System.out.println(i + ". " + productController.getProduct(i).getName());
            }
        }
        System.out.println("0. ��������� �����");
        System.out.print("������� ����� ����������� ������: ");
        int numberProduct = new Scanner(System.in).nextInt();
        if (numberProduct == 0) {
            userMenu();
        }
        productMenu(numberProduct);
    }

    public static void productMenu(int numberProduct) throws IOException {
        System.out.println("");
        productController.getProduct(numberProduct).print();
        // product.getProduct(numberProduct).print();
        System.out.println("");
        System.out.println("1. � ����");
        System.out.println("2. �������� � �������");
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                userMenu();
                break;
            case (2):
                System.out.println("������ ����������");
                int count = new Scanner(System.in).nextInt();
                System.out.println("������� ���� ������");
                double rat = new Scanner(System.in).nextDouble();
                // basket.getBasket().add(product.getProduct(numberProduct), count, rat);
                //�������, ����� �� ��������. �� ������ ��� ��� ������� � �������������
                basketController.getBasket().add(productController.getProduct(numberProduct), count, rat);
                basketMenu();
                break;
            default:
                System.out.println("������� �����");
                productMenu(numberProduct);
                break;
        }
    }

    public static void productMenu(int numberProduct, int numberCategory) throws IOException {
        System.out.println("");
        categoryController.get�ategory(numberCategory).getProducts().get(numberProduct).print();
        // product.getProduct(numberProduct).print();
        System.out.println("");
        System.out.println("1. �����");
        System.out.println("2. �������� � �������");
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                realCategoryMenu(numberCategory);
                break;
            case (2):
                System.out.println("������ ����������");
                int count = new Scanner(System.in).nextInt();
                System.out.println("������� ���� ������");
                double rat = new Scanner(System.in).nextDouble();
                // basket.getBasket().add(product.getProduct(numberProduct), count, rat);
//�������, ����� �� ��������. �� ������ ��� ��� ������� � �������������
                basketController.getBasket().add(categoryController.get�ategory(numberCategory).getProducts().get(numberProduct), count, rat);
                basketMenu();
                break;
            default:
                System.out.println("������� �����");
                productMenu(numberProduct, numberCategory);
                break;
        }
    }

    public static void categoryMenu() throws IOException {
        System.out.println("");
        System.out.println("1. �����");
        int g = 1;
        for (int i = 0; i < categoryController.size(); i++) {
            g++;
            System.out.println(g + ". " + categoryController.get�ategory(i).getTitle());
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
        System.out.println(categoryController.get�ategory(category).getTitle());
        System.out.println("1. �����");
        int g = 1;
        for (int i = 0; i < categoryController.get�ategory(category).getProducts().size(); i++) {
            g++;
            System.out.println(g + ". " + categoryController.get�ategory(category).getProducts().get(i).getName());
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
        System.out.println("����� ����:                                   " + basketController.getBasket().getPrice());
        System.out.println("1. ������� ����");
        System.out.println("2. ������");
        System.out.println("�������� ���-��:");
        basketController.getBasket().printUser();
        int key = new Scanner(System.in).nextInt();
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
        System.out.println("1. �����");
        System.out.println("2. �������� ����������");
        System.out.println("3. �������� ������");
        int key = new Scanner(System.in).nextInt();
        switch (key) {
            case (1):
                basketMenu();
                break;
            case (2):
                System.out.println("������� ����������");
                basketController.getBasket().changeCount(indx, new Scanner(System.in).nextInt());
                basketMenu();
                break;
            case (3):
                System.out.println("������� ������");
                basketController.getBasket().changeRating(indx, new Scanner(System.in).nextDouble());
                basketMenu();
                break;
            default:
                System.out.println("������� ����� ");
                productBasket(indx);
                break;
        }
    }
}
