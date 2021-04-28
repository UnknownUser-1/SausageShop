package sausageShopBack.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sausageShopBack.dao.RoleDao;
import sausageShopBack.models.Category;
import sausageShopBack.models.Role;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoleDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role save(Role role) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(role);
        transaction.commit();
        session.close();
        return role;
    }

    @Override
    public void update(Role role) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(role);
        transaction.commit();
        session.close();
    }

    @Override
    public Role getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Role role= session.get(Role.class, id);
        transaction.commit();
        session.close();
        return role;
    }

    @Override
    public List<Role> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Role> roles = session.createQuery("from Role ").list();
        //Transaction transaction = session.beginTransaction();
        //transaction.commit();
        //session.close();
        return  roles;
    }

    @Override
    public void delete(Role role) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(role);
        transaction.commit();
        session.close();
    }
}
