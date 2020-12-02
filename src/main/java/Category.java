package java;


import java.util.ArrayList;

public class Category {

    private ArrayList<Product> products;
    private String title;

    public Category(ArrayList<Product> products, String title){
        this.products = products;
        this.title = title;
    }

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
}
