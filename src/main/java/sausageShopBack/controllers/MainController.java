package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class MainController {


    ProductServiceImpl productService;

    @Autowired
    public MainController(ProductServiceImpl productService){
        this.productService = productService;
    }

    @RequestMapping({"/","templates/main.html"})
    public String home() {
       /* Product product = productService.getById((long) 1);
        System.out.println(product.getPrice());*/
        return "main";
    }

}
