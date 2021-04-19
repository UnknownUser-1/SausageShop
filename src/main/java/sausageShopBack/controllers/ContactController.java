package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {


    @RequestMapping({"templates/contact.html"})
    public String home() {
        return "contact";
    }
}
