package sausegeShop;

import sausegeShop.controllers.CategoryController;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserComparator {

    private static final CategoryController categoryController = CategoryController.getInstance();

    public static ArrayList<Product> compareProductPrice(Category category){
        ArrayList<Product> sortProduct = category.getProducts();
        Collections.sort(sortProduct);
        return sortProduct;
    }

    public static ArrayList<Category> compareCategoryCount(){
        ArrayList<Category> sortCategory = categoryController.getCategories();
        Collections.sort(sortCategory);
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
}
