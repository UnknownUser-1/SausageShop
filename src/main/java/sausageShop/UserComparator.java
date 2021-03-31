package sausageShop;

import sausageShop.controllers.CategoryController;
import sausageShop.models.Category;
import sausageShop.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserComparator {

    private static final CategoryController categoryController = CategoryController.getInstance();

    private UserComparator(){}

    public static List<Product> compareProductPrice(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getPrice);
        List<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator);
        return sortProduct;
    }

    public static List<Category> compareCategoryCount(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getSize);
        List<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator);
        return sortCategory;
    }
    public static List<Category> compareCategoryName(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getTitle);
        List<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator);
        return sortCategory;
    }

    public static List<Product> compareProductName(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        List<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator);
        return sortProduct;
    }

    public static List<Product> reversedCompareProductPrice(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getPrice);
        List<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator.reversed());
        return sortProduct;
    }

    public static List<Category> reversedCompareCategoryCount(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getSize);
        List<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator.reversed());
        return sortCategory;
    }

    public static List<Category> reversedCompareCategoryName(){
        Comparator<Category> categoryComparator = Comparator.comparing(Category::getTitle);
        List<Category> sortCategory = categoryController.getCategories();
        sortCategory.sort(categoryComparator.reversed());
        return sortCategory;
    }

    public static List<Product> reversedCompareProductName(Category category){
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        List<Product> sortProduct = category.getProducts();
        sortProduct.sort(productComparator.reversed());
        return sortProduct;
    }
}
