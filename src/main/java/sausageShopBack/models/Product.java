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

    @NotNull
    @Column(name = "rating")
    private Double rating;

    public Product() {
        this.id = null;
    }


    public Product(String name, double price, String description, String composition, Category categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.composition = composition;
        this.id = null;
        this.categoryId = categoryId;
    }
}
