package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.models.Basket;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class MainController {

    ProductServiceImpl productService;

    @Autowired
    public MainController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping({"/"})
    public String home(Model model) {
        model.addAttribute("productList", productService.getAll());
        return "main";
    }


    @GetMapping({"/product/{id}"})
    public String goToProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product";
    }

}
