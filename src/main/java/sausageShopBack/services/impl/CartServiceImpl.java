package sausageShopBack.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.CartDao;
import sausageShopBack.models.Cart;
import sausageShopBack.services.CartService;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;

    @Autowired
    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

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
