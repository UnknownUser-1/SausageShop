package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.User;
import sausageShopBack.services.securityServices.SecurityServiceImpl;
import sausageShopBack.services.securityServices.UserServiceImpl;
import sausageShopBack.validator.UserValidator;

@Controller
public class RegisterController {

    UserServiceImpl userService;

    SecurityServiceImpl securityService;

    UserValidator userValidator;

    @Autowired
    public RegisterController(UserServiceImpl userService,
                              SecurityServiceImpl securityService,
                              UserValidator userValidator){
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    /**
     * Если пароль ввденный пользователем равен 777777777, значит это админ
     * Мы передаем id роли чтобы она присвоилась пользователю
     */
    @RequestMapping(value = "/register/add", method = RequestMethod.POST)
    public String registerForm(@ModelAttribute(value = "user") User user,
                               BindingResult bindingResult,
                               Model model){
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/register";
        }
        if(user.getPassword().equals("777777777")) {
            userService.save(user,2L);
            return "redirect:/shopAdmin";
        } else{
            userService.save(user,1L);
        }

        return "redirect:/login";
    }


}