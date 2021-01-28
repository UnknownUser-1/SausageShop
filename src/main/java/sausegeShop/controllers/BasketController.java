package sausegeShop.controllers;

import sausegeShop.models.Basket;

public class BasketController {

    private Basket basket;

    public BasketController(Basket basket){
        if(basket == null)
            throw new IllegalArgumentException("Нельзя добавить несущуствующую корзину");
        this.basket = basket;
    }

    public BasketController(){
        this.basket= new Basket();}

    public Basket getBasket() {
        if(basket == null)
            throw new IllegalArgumentException("Корзины не существует");
        return basket;
    }

    public void setBasket(Basket basket) {
        if(basket == null)
            throw new IllegalArgumentException("Корзину которую вы пытаетесь добваить не сущствует");
        this.basket = basket;
    }
}
