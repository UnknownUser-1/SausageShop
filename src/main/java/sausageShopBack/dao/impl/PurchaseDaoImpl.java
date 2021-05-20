package sausageShopBack.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sausageShopBack.dao.PurchaseDao;
import sausageShopBack.dao.PurchaseDao;
import sausageShopBack.models.Purchase;

import java.util.List;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PurchaseDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Purchase save(Purchase purch) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(purch);
        transaction.commit();
        session.close();
        return purch;
    }

    @Override
    public void update(Purchase purch) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(purch);
        transaction.commit();
        session.close();
    }

    @Override
    public Purchase getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Purchase purch = session.get(Purchase.class, id);
        transaction.commit();
        session.close();
        return purch;
    }

    @Override
    public List<Purchase> getAll() {
        Session session = this.sessionFactory.openSession();
        List<Purchase> purchs = session.createQuery("from Purchase").list();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();
        return purchs;
    }

    @Override
    public void delete(Purchase purch) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(purch);
        transaction.commit();
        session.close();
    }
}