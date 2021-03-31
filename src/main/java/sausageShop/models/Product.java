package sausageShop.models;

//import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;


//@XmlRootElement(name = "product")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable,Comparable<Product>{

    /**
     * Product name
     */
    //@XmlElement(name = "name")
    private String name;
    /**
     * Product price
     */
    //@XmlElement(name = "price")
    private double price;
    /**
     * Product description
     */
    //@XmlElement(name = "description")
    private String description;
    /**
     * Product composition
     */
    //@XmlElement(name = "composition")
    private String composition;

    private Integer categoryId = null;

    private Integer id = null;

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
    public Product(){
        this.rating = new ArrayList<>();
    }

    private Product(String name, double price, String description, String composition, int categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.composition = composition;
        this.rating = new ArrayList<>();
        this.id = null;
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * Calculates rating by dividing summ of ratings by number of ratings
     */
   // @XmlElement(name = "rating")
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
     * Add rating
     */
    public void setRating(double rat){
        rating.add(rat);
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
     * @param rat   New product evaluation
     */
    public void purchase(int count, double rat) {
        for (int i = 0; i < count; i++) {
            this.rating.add(rat);
        }
    }

    /**
     * Factory for making a new instance of Product
     * <br>
     * <br> ПОКА ЧТО КОСТЫЛЬ
     */
    public static Product productFactory(String name, double price, String description, String composition, int categoryId) {
        Product pr = new Product(name, price, description, composition,categoryId );
        return pr;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Product o) {
        return (int)(this.getPrice() - o.getPrice());
    }


}
