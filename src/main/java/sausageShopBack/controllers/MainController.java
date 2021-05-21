package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.ProductServiceImpl;
import sausageShopBack.services.securityServices.UserServiceImpl;

import java.security.Principal;

@Controller
public class MainController {

    ProductServiceImpl productService;
    UserServiceImpl user;

    @Autowired
    public MainController(ProductServiceImpl productService, UserServiceImpl user) {
        this.productService = productService;
        this.user = user;
    }

    @GetMapping({"/"})
    public String home(Model model) {
        model.addAttribute("productList", productService.getAll());
        model.addAttribute("productToSearch", new Product());
        return "main";
    }

    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    public String searchProduct(@ModelAttribute(value = "productToSearch") Product product,
                                Model model) {
        model.addAttribute("productList", productService.search(product.getName()));
        return "search-product";
    }
}
