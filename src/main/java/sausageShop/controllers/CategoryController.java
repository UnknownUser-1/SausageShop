package sausageShop.controllers;

import java.io.Serializable;
import sausageShop.models.Category;

import java.util.ArrayList;
import java.util.regex.Pattern;
import sausageShop.models.Product;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "categories")
public class CategoryController implements Serializable {

    //@XmlElement(name = "category")
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

    public ArrayList<Product> search(String str) {
        ArrayList<Product> result = new ArrayList<>();
        String product = str;
        if (product.contains("?")) {
            String predQue = product.substring(0, product.indexOf("?"));
            String postQue = product.substring(product.indexOf("?"), product.length());
            postQue = postQue.replace("?", "");
            if (!postQue.isEmpty()) {
                predQue = predQue + ".";
                product = predQue;
                product = product + postQue;
            } else {
                predQue = predQue + ".+";
                product = predQue;
            }
            product = "С.с";
            for (int j = 0; j < this.size(); j++) {
                for (int k = 0; k < this.getCategory(j).getSize(); k++) {
                    if (Pattern.matches(product, this.getCategory(j).getProduct(k).getName())) {
                        result.add(this.getCategory(j).getProduct(k));
                    }
                }
            }
        } else {
            for (int j = 0; j < this.size(); j++) {
                for (int k = 0; k < this.getCategory(j).getSize(); k++) {
                    if (this.getCategory(j).getProduct(k).getName().toLowerCase().contains(product.toLowerCase())) {
                        result.add(this.getCategory(j).getProduct(k));
                    }
                }
            }
        }
        return result;
    }
}
