package java;

import java.util.ArrayList;

public class Basket {

    private ArrayList<Product> products;

    public Basket(ArrayList<Product> products){
        this.products = products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
