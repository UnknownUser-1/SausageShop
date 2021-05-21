package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sausageShopBack.models.Product;
import sausageShopBack.models.User;
import sausageShopBack.services.impl.PurchaseServiceImpl;
import sausageShopBack.services.securityServices.SecurityServiceImpl;
import sausageShopBack.services.securityServices.UserService;
import sausageShopBack.services.securityServices.UserServiceImpl;
import sausageShopBack.validator.UserValidator;

@Controller
public class MyAccountController {

    UserServiceImpl userService;

    SecurityServiceImpl securityService;

    UserValidator userValidator;

    PurchaseServiceImpl purchaseService;

    @Autowired
    public MyAccountController(UserServiceImpl userService,
                               SecurityServiceImpl securityService,
                               UserValidator userValidator,
                               PurchaseServiceImpl purchaseService) {
        this.securityService = securityService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.purchaseService = purchaseService;
    }

    @RequestMapping(value = {"/myaccount"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("userName", securityService.findLoggedInUsername().getUsername());
        model.addAttribute("updateUser", new User());
        model.addAttribute("productToSearch", new Product());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("purchs", purchaseService.getAllByUserId(userService.findByUsername(auth.getName()).getId()));
        return "myaccount";
    }

    @RequestMapping(value = {"/myaccount/update"}, method = RequestMethod.POST)
    public String updateUser(@ModelAttribute(value = "updateUser") User user,
                             BindingResult bindingResult,
                             Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/myaccount";
        }
        user.setId(securityService.findLoggedInUsername().getId());
        user.setRoles(securityService.findLoggedInUsername().getRoles());

        userService.update(user);

        securityService.updateLoggedUser(user.getUsername(), user.getPassword());

        return "redirect:/shop";
    }

    @RequestMapping(value = {"/admin/myaccount"}, method = RequestMethod.GET)
    public String homeAdmin(Model model) {
        model.addAttribute("userName", securityService.findLoggedInUsername().getUsername());
        model.addAttribute("updateUser", new User());
        model.addAttribute("productToSearch", new Product());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("purchs", purchaseService.getAllByUserId(userService.findByUsername(auth.getName()).getId()));
        return "admin-myaccount";
    }

    @RequestMapping(value = {"/admin/myaccount/update"}, method = RequestMethod.POST)
    public String updateUserAdmin(@ModelAttribute(value = "updateUser") User user,
                                  BindingResult bindingResult,
                                  Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/myaccount";
        }
        user.setId(securityService.findLoggedInUsername().getId());
        user.setRoles(securityService.findLoggedInUsername().getRoles());

        userService.update(user);

        securityService.updateLoggedUser(user.getUsername(), user.getPassword());

        return "redirect:/shop";
    }
}
