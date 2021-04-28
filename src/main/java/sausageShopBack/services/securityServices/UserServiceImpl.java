package sausageShopBack.services.securityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.impl.RoleDaoImpl;
import sausageShopBack.dao.impl.UserDaoImpl;
import sausageShopBack.models.Role;
import sausageShopBack.models.User;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private UserDaoImpl userDao;

    private RoleDaoImpl roleDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, RoleDaoImpl roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
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

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
