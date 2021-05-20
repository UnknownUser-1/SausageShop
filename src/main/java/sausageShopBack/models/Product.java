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
public class Product {


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
    @ManyToOne
    @JoinColumn(name = "categoryid", foreignKey = @ForeignKey(name = "categoryid"))
    private Category categoryId;


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Transient
    private ArrayList<Double> allValuesInTheRating;

    @NotNull
    @Column(name = "rating")
    private Double rating;

    public Product() {
        this.allValuesInTheRating = new ArrayList<Double>();
        this.id = null;
    }


    public Product(String name, double price, String description, String composition, Category categoryId) {
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
        if (allValuesInTheRating.isEmpty()) {//Зачем нужна эта проверка, если мы в этом методе итак добавляем значения
            this.rating = rat;
        }
        double rt = 0;
        for (Double rating1 : this.allValuesInTheRating) {
            rt += rating1;
        }
        rt = rt + rat;
        rating = rt / this.allValuesInTheRating.size() + 1;
    }


}
