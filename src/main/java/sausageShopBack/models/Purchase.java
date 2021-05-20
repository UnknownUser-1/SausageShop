package sausageShopBack.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "purchaseslist", schema = "sausageshop")
public class Purchase {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "userid"))
    private User userId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productid", foreignKey = @ForeignKey(name = "productid"))
    private Product productId;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    public Purchase(){
        userId = null;
    }
}
