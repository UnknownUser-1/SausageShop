package sausageShopBack.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@Data
@Entity
@Table(name = "sausageshop.product")
public class Product implements Comparable<Product>{

    @NotNull
    @Column(name = "nameproduct")
    private String name;

    @NotNull
    @Column(name = "price")
    private double price;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "composition")
    private String composition;

    @NotNull
    @Column(name = "categoryId")
    private Integer categoryId = null;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category")
    private Category category;


    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id = null;

    @Transient
    private ArrayList<Double> rating;

    public Product(){
        this.rating = new ArrayList<Double>();
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

    @NotNull
    @Column(name = "rating")
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

    public void setRating(double rat){
        rating.add(rat);
    }

    public static Product productFactory(String name, double price, String description, String composition, int categoryId) {
        Product pr = new Product(name, price, description, composition,categoryId );
        return pr;
    }

    @Override
    public int compareTo(Product o) {
        return (int)(this.getPrice() - o.getPrice());
    }


}
