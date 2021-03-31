package sausageShopBack.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable, Comparable<Category> {


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    @NotNull
    @Column(name = "title")
    private String title;

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Integer id = null;

    public Category(@NotNull String title) {
        this.products = new ArrayList<>();
        this.title = title;
    }

    public Category(){
        this.products = new ArrayList<>();
    }

    public int getSize() {
        return this.products.size();
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void deleteProduct(int i){
        this.products.remove(i);
    }

    public Product getProduct(int number){
        return this.products.get(number);
    }

    @Override
    public int compareTo(Category o) {
        return (this.getSize()-o.getSize());
    }
}
