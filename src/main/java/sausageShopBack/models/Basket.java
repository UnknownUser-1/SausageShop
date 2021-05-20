package sausageShopBack.models;

import java.util.ArrayList;


public class Basket {

    /**
     * ArrayList with params
     * <br>We store all the Basket Information here
     */
    private ArrayList<Product> products;
    private ArrayList<Integer> count;
    private ArrayList<Double> rat;

    public Basket() {
        this.products = new ArrayList<>();
        this.rat = new ArrayList<>();
        this.count = new ArrayList<>();
    }

    public void delete(Long i) {
        i=i-1;
        products.remove(i);
        count.remove(i);
        rat.remove(i);
    }

    public void deleteAll() {
        products.removeAll(products);
        count.removeAll(count);
        rat.removeAll(rat);
    }

//    public void add(Product pr) {
//        products.add(pr);
//        count.add(pr.getCount());
//
//    }

    public ArrayList<Product> getAllProducts(){
        return this.products;
    }

    public ArrayList<Integer> getCount(){return this.count;}

    public void changeCount(int pr, int c) {
        count.set(pr, c);
    }

    public void changeRating(int pr, double r) {
        rat.set(pr, r);
    }

    public int size() {
        return products.size();
    }

    public Product getProducts(int i) {
        return products.get(i);
    }

    public Double getRat(int i) {
        return rat.get(i);
    }

    public Integer getCount(int i) {
        return count.get(i);
    }
}
