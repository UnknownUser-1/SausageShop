package sausageShopBack.services.securityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.impl.RoleDaoImpl;
import sausageShopBack.dao.impl.UserDaoImpl;
import sausageShopBack.models.Role;
import sausageShopBack.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;

    private RoleDaoImpl roleDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserDaoImpl userDao,
                           RoleDaoImpl roleDao,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public void save(User user, Long roleId) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(roleId));
        user.setRoles(roles);
        userDao.save(user);
    }

    public void update(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User getById(Long username) {
        return userDao.getById(username);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }
}
