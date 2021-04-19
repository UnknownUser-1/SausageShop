package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingleProductController {

    @RequestMapping({"templates/single-product.html"})
    public String home() {
        return "single-product";
    }
}
