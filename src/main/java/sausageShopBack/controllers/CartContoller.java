package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class CartContoller {

    @Autowired
    ProductServiceImpl productService;

    @RequestMapping({"templates/cart.html"})
    public String home() {
        return "cart";
    }
}
