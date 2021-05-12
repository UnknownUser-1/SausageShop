package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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


    @GetMapping({"/productdelete/{id}"})
    public String deleteToProduct(@PathVariable(value = "id") Long id, Model model) {
        this.productService.delete(productService.getById(id));
        return "redirect:/shopAdmin";
    }

    @GetMapping({"/shopAdmin"})
    public String admin(Model model) {
        model.addAttribute("productList", productService.getAll());
        return "admin-shop-grid-list";
    }
}
