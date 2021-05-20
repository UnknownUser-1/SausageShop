package sausageShopBack.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sausageShopBack.dao.CartDao;
import sausageShopBack.models.Cart;
import sausageShopBack.models.User;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public CartDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cart save(Cart cart) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cart);
        transaction.commit();
        session.close();
        return cart;
    }

    @Override
    public void update(Cart cart) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(cart);
        transaction.commit();
        session.close();
    }

    @Override
    public Cart getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Cart cart = session.get(Cart.class, id);
        transaction.commit();
        session.close();
        return cart;
    }

    @Override
    public List<Cart> getAll() {
        Session session = this.sessionFactory.openSession();
        List<Cart> carts = session.createQuery("from Cart").list();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();
        return carts;
    }

    @Override
    public void delete(Cart cart) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cart);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Cart> findAllByUserId(Long userId) {
        Session session = this.sessionFactory.openSession();
        List<Cart> carts = session.createQuery("from Cart ").list();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();
        for (int i = 0; i < carts.size(); i++) {
            if (!userId.equals(carts.get(i).getUserId().getId()))
                carts.remove(i);
        }
        return carts;
    }

}
