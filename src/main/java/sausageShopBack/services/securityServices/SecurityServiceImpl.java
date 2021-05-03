package sausageShopBack.services.securityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.impl.UserDaoImpl;
import sausageShopBack.models.User;


@Service
public class SecurityServiceImpl {

    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    private UserDaoImpl userDao;

    @Autowired
    public SecurityServiceImpl(UserDetailsService userDetailsService,
                               AuthenticationManager authenticationManager,
                               UserDaoImpl userDao) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
    }

    public User findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            return userDao.findByUsername(((UserDetails) userDetails).getUsername());
        }
        return null;
    }

    public void updateLoggedUser(String username, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
