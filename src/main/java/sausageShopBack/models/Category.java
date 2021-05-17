package sausageShopBack.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "sausageshop")
public class Category implements Serializable, Comparable<Category> {


    @OneToMany
    @JoinColumn(name = "categoryid")
    private List<Product> products;

    @NotNull
    @Column(name = "title")
    private String title;

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Long id = null;

    public Category(@NotNull String title) {
        this.products = new ArrayList<>();
        this.title = title;
    }

    public Category() {
        this.products = new ArrayList<>();
    }

    public int getSize() {
        return this.products.size();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void deleteProduct(int i) {
        this.products.remove(i);
    }

    public Product getProduct(int number) {
        return this.products.get(number);
    }

    @Override
    public int compareTo(Category o) {
        return (this.getSize() - o.getSize());
    }
}
