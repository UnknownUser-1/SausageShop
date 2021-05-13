package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("productToSearch", new Product());
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

    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    public String searchProduct(@ModelAttribute(value = "productToSearch") Product product,
                                Model model) {
        model.addAttribute("productList", productService.search(product.getName()));
        return "shop-grid-list";
    }
}
