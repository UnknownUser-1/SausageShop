package sausageShopBack.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sausageShopBack.models.User;

public interface UserDao extends CrudDao<User> {
    User findByUsername(String username);
}
