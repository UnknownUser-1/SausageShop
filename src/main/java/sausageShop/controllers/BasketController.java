package sausageShop.controllers;

import sausageShop.models.Basket;

public class BasketController {

    private Basket basket;
    private static BasketController instance;

    public static BasketController getInstance() {
        if (instance == null) {
            instance = new BasketController();
        }
        return instance;
    }

    private BasketController() {
        this.basket = new Basket();
    }

    public Basket getBasket() {
        if (basket == null)
            throw new IllegalArgumentException("Корзины не существует");
        return basket;
    }

    public void setBasket(Basket basket) {
        if (basket == null)
            throw new IllegalArgumentException("Корзину которую вы пытаетесь добваить не сущствует");
        this.basket = basket;
    }
}
