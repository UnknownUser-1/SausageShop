package sausageShop;

import sausageShop.controllers.CategoryController;
import sausageShop.models.Category;
import sausageShop.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserComparator {

    private static final CategoryController categoryController = CategoryController.getInstance();

    private UserComparator(){}

    public static ArrayList<Product> compareProductPrice(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getPrice);
        ArrayList<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator);
        return sortProduct;
    }

    public static ArrayList<Category> compareCategoryCount(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getSize);
        ArrayList<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator);
        return sortCategory;
    }
    public static ArrayList<Category> compareCategoryName(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getTitle);
        ArrayList<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator);
        return sortCategory;
    }

    public static ArrayList<Product> compareProductName(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        ArrayList<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator);
        return sortProduct;
    }

    public static ArrayList<Product> reversedCompareProductPrice(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getPrice);
        ArrayList<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator.reversed());
        return sortProduct;
    }

    public static ArrayList<Category> reversedCompareCategoryCount(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getSize);
        ArrayList<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator.reversed());
        return sortCategory;
    }

    public static ArrayList<Category> reversedCompareCategoryName(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getTitle);
        ArrayList<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator.reversed());
        return sortCategory;
    }

    public static ArrayList<Product> reversedCompareProductName(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        ArrayList<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator.reversed());
        return sortProduct;
    }
}
