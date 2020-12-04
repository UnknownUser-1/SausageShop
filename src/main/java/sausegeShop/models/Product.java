package sausegeShop.models;

import java.util.ArrayList;

public class Product {

    /**
     * Product name
     */
    private String name;
    /**
     * Product price
     */
    private double price;
    /**
     * Product description
     */
    private String description;
    /**
     * Product composition
     */
    private String composition;
    /**
     * Product category.
     * <br>
     * <br>WARNING! Maybe it needs to be changed!!!!!
     * <br>
     * <br>You can't just create an instance without adding it to Category
     * after. If you do, then your category won't have this product instance
     */
    private Category cat;
    /**
     * Product rating will show only after calculations and some purchases
     */
    private ArrayList<Double> rating;

    /**
     * Constructor is private, so you have to use from factory.
     * {@link productFactory}
     * <br>
     * <br>It's needed so you are able to make a Product with bound to a categary.
     */
    private Product(String name, double price, String description, String composition, Category cat) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.composition = composition;
        this.cat = cat;
        this.rating = new ArrayList<>();
    }
   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    /**
     * You can get {@link cat} here.
     *
     * <br>
     * <br>WARNINNG
     *
     * @see Product#cat here.
     */
    public Category getCategory() {
        if (this.cat != null) {
            if (this.cat.getProducts().contains(this)) {
                return cat;
            }
            cat.getProducts().add(this);
        }
        return cat;
    }

    /**
     * You can set {@link cat} here.
     *
     * <br>
     * <br>WARNINNG
     *
     * @see Product#cat here.
     */
    public void setCategory(Category cat) {
        if (this.cat != null) {
            if (this.cat.getProducts().contains(this)) {
                this.cat.getProducts().remove(this);
            }
        }
        cat.getProducts().add(this);
        this.cat = cat;

    }

    public String getCategoryTitle() {
        return this.cat.getTitle();
    }

    /**
     * Calculates rating by dividing summ of ratings by number of ratings
     */
    public double getRating() {
        if (rating.isEmpty()) {
            return 0;
        }
        double rt = 0;
        for (Double rating1 : this.rating) {
            rt += rating1;
        }
        return rt / this.rating.size();
    }

    /**
     * @return You can get the number of purchasings throug calculating the size
     * of {@link rating} ArrayList
     */
    public int getPurchaseNumber() {
        return this.rating.size();
    }

    /**
     * Adding another instances to {@link rating} ArrayList
     *
     * @param count Quantity of purchases
     * @param rat New product evaluation
     */
    public void purchase(int count, double rat) {
        for (int i = 0; i < count; i++) {
            this.rating.add(rat);
        }
    }

    public void print() {
        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
        System.out.println("Информация о товаре");
        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
        System.out.println(getName());
        System.out.println(getCategoryTitle());
        System.out.println(getPrice());
        System.out.println(getDescription());
        System.out.println(getComposition());
        System.out.println(getRating());
        System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
    }

    /**
     * Factory for making a new instance of Product
     *<br>
     *<br> ПОКА ЧТО КОСТЫЛЬ
     */
    public static Product productFactory(String name, double price, String description, String composition, Category cat) {
        Product pr = new Product(name, price, description, composition, cat);
        pr.setCategory(cat);
        return pr;
    }
}
