package sausageShopBack.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.CartDao;
import sausageShopBack.dao.CategoryDao;
import sausageShopBack.dao.UserDao;
import sausageShopBack.models.Basket;
import sausageShopBack.models.Cart;
import sausageShopBack.models.Product;
import sausageShopBack.services.CartService;
import sausageShopBack.services.securityServices.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;

    @Autowired
    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }


//    Basket basket = new Basket();
//
//    public void addProductToBasket(Product product) {
//        basket.add(product);
//    }
//
//    public void deleteProductInBasket(Long Id) {
//        basket.delete(Id);
//    }
//
//    public ArrayList<Product> getAllProduct() {
//        return basket.getAllProducts();
//    }
//
//    public ArrayList<Integer> getCountProducts() {
//        return basket.getCount();
//    }
//
//    public void buyProducts() {
//        basket.deleteAll();
//    }

    @Override
    public Cart save(Cart cart) {
        return this.cartDao.save(cart);
    }

    @Override
    public void update(Cart cart) {
        this.cartDao.update(cart);
    }

    @Override
    public Cart getById(Long id) {
        return this.cartDao.getById(id);
    }

    @Override
    public List<Cart> getAll() {
        return this.cartDao.getAll();
    }

    @Override
    public void delete(Cart cart) {
        this.cartDao.delete(cart);
    }

    public List<Cart> getAllByUserId(Long id) {
        return this.cartDao.findAllByUserId(id);
    }
}
