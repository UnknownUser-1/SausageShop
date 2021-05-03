package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sausageShopBack.models.User;
import sausageShopBack.services.securityServices.SecurityServiceImpl;
import sausageShopBack.services.securityServices.UserService;
import sausageShopBack.services.securityServices.UserServiceImpl;
import sausageShopBack.validator.UserValidator;

@Controller
public class MyAccountController {

    UserServiceImpl userService;

    SecurityServiceImpl securityService;

    UserValidator userValidator;

    @Autowired
    public MyAccountController(UserServiceImpl userService,
                               SecurityServiceImpl securityService,
                               UserValidator userValidator){
        this.securityService = securityService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @RequestMapping(value = {"/myaccount"},method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("userName",securityService.findLoggedInUsername().getUsername());
        model.addAttribute("updateUser", new User());
        return "myaccount";
    }

    @RequestMapping(value = {"/myaccount/update"},method = RequestMethod.POST)
    public String updateUser(@ModelAttribute(value = "updateUser")User user,
                             BindingResult bindingResult,
                             Model model){
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/myaccount";
        }
        user.setId(securityService.findLoggedInUsername().getId());
        user.setRoles(securityService.findLoggedInUsername().getRoles());

        userService.update(user);

        securityService.updateLoggedUser(user.getUsername(),user.getPassword());

        return "redirect:/shop";
    }
}
