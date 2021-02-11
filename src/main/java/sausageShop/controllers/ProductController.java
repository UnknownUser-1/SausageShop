package sausageShop.controllers;

import sausageShop.models.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductController implements Serializable {

    private ArrayList<Product> product = new ArrayList<>();
    private static ProductController instance;

    private ProductController() {
    }

    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }


    public Product getProduct(int number) {
        if (number > product.size() || number < 0)
            throw new IllegalArgumentException("Такого продукта нету");
        return product.get(number);
    }

    public void setProduct(Product product, int number) {
        if (product == null || number < 0)
            throw new IllegalArgumentException("Что-то пошло не так");
        this.product.set(number, product);
    }

    public void addProduct(Product product, int number) {
        if (product == null || number < 0)
            throw new IllegalArgumentException("Что-то пошло не так");
        this.product.add(number, product);
    }

    public void deleteProduct(int number) {
        if (number < 0 || number > size())
            throw new IllegalArgumentException("Что-то пошло не так");
        this.product.remove(number);
    }

    public int size() {
        return product.size();
    }

}
