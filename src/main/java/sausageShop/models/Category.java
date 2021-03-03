package sausageShop.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Category implements Serializable,Comparable<Category>{

    private ArrayList<Product> products;
    private String title;

    public Category(String title) {
        this.products = new ArrayList<>();
        this.title = title;
    }

    public Category(){}

    public String getTitle() {
        return title;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return this.products.size();
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void deleteProduct(int i){
        this.products.remove(i);
    }

    public Product getProduct(int number){
        return this.products.get(number);
    }

    /**
     * Just for debug
     */


    @Override
    public int compareTo(Category o) {
        return (this.getSize()-o.getSize());
    }
}
