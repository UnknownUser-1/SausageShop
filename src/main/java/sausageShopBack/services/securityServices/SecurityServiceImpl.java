package sausageShopBack.services.securityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SecurityServiceImpl{

    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public SecurityServiceImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager){
      this.userDetailsService = userDetailsService;
      this.authenticationManager = authenticationManager;
    }

    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

}
