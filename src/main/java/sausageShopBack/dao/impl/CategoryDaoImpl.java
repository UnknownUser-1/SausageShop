package sausageShopBack.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sausageShopBack.dao.CategoryDao;
import sausageShopBack.models.Category;
import sausageShopBack.models.Product;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public CategoryDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Category save(Category category) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(category);
        transaction.commit();
        session.close();
        return category;
    }

    @Override
    public void update(Category category) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(category);
        transaction.commit();
        session.close();
    }

    @Override
    public Category getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Category category = session.get(Category.class, id);
        transaction.commit();
        session.close();
        return category;
    }

    @Override
    public List<Category> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Category> categories = session.createQuery("from Category ").list();
        //Transaction transaction = session.beginTransaction();
        //transaction.commit();
        //session.close();
        return  categories;
    }

    @Override
    public void delete(Category category) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(category);
        transaction.commit();
        session.close();
    }
}
