package sausageShop.controllers;

import sausageShop.models.Category;
import sausageShop.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
public class CategoryController implements Serializable {


    private ArrayList<Category> categories = new ArrayList<>();

    private static CategoryController instance;

    private CategoryController() {
    }

    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Category getCategory(int number) {
        if (number < 0 || number > categories.size()) {
            throw new IllegalArgumentException("Такой категории не существует");
        }
        return categories.get(number);
    }

    public void setCategory(Category category, int number) {
        if (category == null || number < 0) {
            throw new IllegalArgumentException("Что-то пошло не так");
        }
        this.categories.set(number, category);
    }

    public void addCategories(Category category, int number) {
        if (category == null || number < 0) {
            throw new IllegalArgumentException("Что-то пошло не так");
        }
        this.categories.add(number, category);
    }

    public void deleteCategories(int number) {
        if (number < 0 || number > size()) {
            throw new IllegalArgumentException("Что-то пошло не так");
        }
        this.categories.remove(number);
    }

    public int size() {
        return categories.size();
    }

    public List<Product> search(String str) {
        String pro = str;
        ArrayList<Product> productArrayList = new ArrayList<>();
        if (pro.contains("?")) {
            String predQue = pro.substring(0, pro.indexOf("?"));
            String postQue = pro.substring(pro.indexOf("?"), pro.length());
            postQue = postQue.replace("?", "");
            if (!postQue.isEmpty()) {
                predQue = predQue + ".";
                pro = predQue;
                pro = pro + postQue;
            } else {
                predQue = predQue + ".+";
                pro = predQue;
            }
        }
        String finalPro = pro;
        categories.forEach(category -> category.getProducts()
                .stream().filter(product ->
                        Pattern.matches(finalPro, product.getName())).forEach(productArrayList::add));

        return productArrayList;
    }
}
