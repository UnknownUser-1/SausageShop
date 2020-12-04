package sausegeShop.models;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Basket {

/**
 * ArrayList with params
 * <br>{@link  BasketCrutch#p} Product
 * <br>{@link BasketCrutch#count} buying amount
 * <br>{@link BasketCrutch#rating} rating
 * <br>We store all the Basket Information here
 */
    private ArrayList<BasketCrutch> products;

    public Basket() {
        this.products = new ArrayList<>();
    }
    
    /**
     * 
     * We need to create a some sort o a method that can return massive or smth like that.
     */
    public ArrayList<BasketCrutch> getBasket() {
        return products;
    }

    public void delete(int indx) {
        products.remove(indx);
    }

    public void add(Product pr, int count, double rat) {
        products.add(new BasketCrutch(pr, count, rat));
    }

    public void changeCount(int pr, int count) {
        products.get(pr).setCount(count);
    }

    public void changeRating(int pr, double rating) {
        products.get(pr).setRating(rating);
    }

    public void purchase() {
        for (BasketCrutch product : products) {
            product.getProduct().purchase(product.getCount(), product.getRating());
        }
    }

    public double getPrice() {
        double price = 0;
        for (BasketCrutch product : products) {
            for (int i = 0; i < product.getCount(); i++) {
                price += product.getProduct().getPrice();
            }
        }
        return price;
    }

    /**
     * Printing basket information
     */
    public void print() {
        if(products.isEmpty()){
            System.out.println("Корзина пуста");
            return;
        }
        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
        System.out.println("Информация о корзине");
        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
        products.forEach(new Consumer<BasketCrutch>() {
            @Override
            public void accept(BasketCrutch b) {
                System.out.println(b.getProduct().getName() + "  " + b.getProduct().getPrice() + "  Кол-во в заказе: " + b.getCount() + "  Ваша оценка: " + b.getRating());
            }
        });
        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
    }

}


/**
 * WARNING 
 * <br> Чудовищный костыль
 * <br>
 * <br>
 * <br> It need for this {@link products} ArrayList creation 
 */
class BasketCrutch {

    private Product p;
    private int count;
    private double rating;

    public BasketCrutch(Product p, int count, double rating) {
        this.p = p;
        this.count = count;
        this.rating = rating;
    }

    public Product getProduct() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
