package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping({"templates/login.html"})
    public String home() {
        return "login";
    }
}
