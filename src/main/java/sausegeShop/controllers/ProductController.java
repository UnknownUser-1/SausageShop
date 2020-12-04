package sausegeShop.controllers;

import sausegeShop.models.Product;
import java.util.ArrayList;

public class ProductController {

    private ArrayList<Product> product = new ArrayList<>();


    public ProductController(ArrayList<Product> product){
        if(product == null)
            throw new IllegalArgumentException("Список пуст");
        this.product = product;
    }

    public ProductController(){}

    public Product getProduct(int number) {
        if(number>product.size() || number<0)
            throw new IllegalArgumentException("Такого продукта нету");
        return product.get(number);
    }

    public void setProduct(Product product,int number) {
        if(product == null || number<0)
            throw new IllegalArgumentException("Что-то пошло не так");
        this.product.set(number,product);
    }

    public void addProduct(Product product,int number) {
        if (product==null||number<0)
            throw new IllegalArgumentException("Что-то погло не так");
        this.product.add(number,product);
    }

    public int size(){
        return product.size();
    }

}
