package sausageShopBack.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@Getter
@Setter
@Entity
@Table(name = "product", schema = "sausageshop")
public class Product implements Comparable<Product> {


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
    @Column(name = "categoryid")
    private Integer categoryId;

    /*@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category")
    private Category category;*/


    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Transient
    private ArrayList<Double> allValuesInTheRating;

    @NotNull
    @Column(name = "rating")
    private Double rating;

    public Product() {
        this.allValuesInTheRating = new ArrayList<Double>();
    }


    private Product(String name, double price, String description, String composition, int categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.composition = composition;
        this.allValuesInTheRating = new ArrayList<>();
        this.id = null;
        this.categoryId = categoryId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rat) {
        allValuesInTheRating.add(rat);
        if (allValuesInTheRating.isEmpty()) {
            this.rating = rat;
        }
        double rt = 0;
        for (Double rating1 : this.allValuesInTheRating) {
            rt += rating1;
        }
        rt = rt + rat;
        rating = rt / this.allValuesInTheRating.size()+1;
    }

    public static Product productFactory(String name, double price, String description, String composition, int categoryId) {
        Product pr = new Product(name, price, description, composition, categoryId);
        return pr;
    }

    @Override
    public int compareTo(Product o) {
        return (int) (this.getPrice() - o.getPrice());
    }


}
