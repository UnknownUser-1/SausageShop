package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyAccaountController {



    @RequestMapping({"templates/myaccount.html"})
    public String home(Model model) {
        return "myaccount";
    }
}
