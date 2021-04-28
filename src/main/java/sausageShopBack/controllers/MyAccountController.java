package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sausageShopBack.services.securityServices.SecurityServiceImpl;
import sausageShopBack.services.securityServices.UserService;
import sausageShopBack.services.securityServices.UserServiceImpl;
import sausageShopBack.validator.UserValidator;

@Controller
public class MyAccountController {

    UserServiceImpl userService;

    SecurityServiceImpl securityService;

    @Autowired
    public MyAccountController(UserServiceImpl userService, SecurityServiceImpl securityService){
        this.securityService = securityService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/myaccount"},method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("user",securityService.findLoggedInUsername());
        return "myaccount";
    }
}
