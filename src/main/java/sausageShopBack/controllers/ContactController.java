package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sausageShopBack.models.Product;

@Controller
public class ContactController {


    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("productToSearch", new Product());
        return "contact";
    }
}
