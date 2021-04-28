package sausageShopBack.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sausageShopBack.dao.ProductDao;
import sausageShopBack.models.Product;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Product save(Product product) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(product);
        transaction.commit();
        session.close();
        return product;
    }

    @Override
    public void update(Product product) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(product);
        transaction.commit();
        session.close();
    }

    @Override
    public Product getById(Long id) {
        Session session = this.sessionFactory.openSession();
        // Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, id);
//        transaction.commit();
        session.close();
        return product;
    }

    @Override
    public List<Product> getAll() {
        Session session = this.sessionFactory.openSession();
        List<Product> products = session.createQuery("from Product").list();
        //Transaction transaction = session.beginTransaction();
        // transaction.commit();
        session.close();
        return products;
    }

    @Override
    public void delete(Product product) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(product);
        transaction.commit();
        session.close();
    }
}
