package sausageShopBack.services.securityServices;

import sausageShopBack.models.User;

public interface UserService {

    void save(User user,Long roleId);

    User findByUsername(String username);
}
