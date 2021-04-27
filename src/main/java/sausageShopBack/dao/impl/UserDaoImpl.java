package sausageShopBack.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sausageShopBack.dao.UserDao;
import sausageShopBack.models.Role;
import sausageShopBack.models.User;
import sausageShopBack.validator.UserValidator;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findByUsername(String username) {
        Session session = this.sessionFactory.openSession();
        List<User> users = session.createQuery("from User ").list();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        session.close();
        for (int i = 0; i<users.size();i++){
            if (username.equals(users.get(i).getUsername()))
                return users.get(i);
        }
        return null;
    }

    @Override
    public User save(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void update(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user= session.get(User.class, id);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("from User ").list();
        //Transaction transaction = session.beginTransaction();
        //transaction.commit();
        session.close();
        return  users;
    }

    @Override
    public void delete(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
