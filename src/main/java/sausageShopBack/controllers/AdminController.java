package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    ProductServiceImpl productService;

    @Autowired
    public AdminController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping({""})
    public String home(Model model) {
        model.addAttribute("productList", productService.getAll());
        model.addAttribute("productToSearch", new Product());
        return "admin-main";
    }

    @GetMapping({"/shop"})
    public String admin(Model model) {
        model.addAttribute("productList", productService.getAll());
        return "admin-shop-grid-list";
    }

    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    public String searchProduct(@ModelAttribute(value = "productToSearch") Product product,
                                Model model) {
        model.addAttribute("productList", productService.search(product.getName()));
        return "admin-shop-grid-list";
    }
}
