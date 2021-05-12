package sausageShopBack.services.impl;


import org.springframework.stereotype.Service;
import sausageShopBack.models.Basket;
import sausageShopBack.models.Product;

import java.util.ArrayList;

@Service
public class BasketServiceImpl {

    Basket basket = new Basket();

    public void addProductToBasket(Product product){
        basket.add(product);
    }

    public void deleteProductInBasket(Long Id){
        basket.delete(Id);
    }

    public ArrayList<Product> getAllProduct(){
        return basket.getAllProducts();
    }

    public ArrayList<Integer> getCountProducts(){return basket.getCount();}

    /**
     * Данный метод должен срадатывать при покупке продуктов
     * Так как нам неоткуда заказывать продукты он просто удаляет их из корзины
     */
    public void buyProducts(){
        basket.deleteAll();
    }
}
