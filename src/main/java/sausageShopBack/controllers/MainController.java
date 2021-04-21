package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class MainController {

    ProductServiceImpl productService;
    CategoryServiceImpl categoryService;

    @Autowired
    public MainController(ProductServiceImpl productService, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/"})
    public String home(Model model) {
        model.addAttribute("productList", productService.getAll());
        return "main";
    }


    @GetMapping({"/product/{id}"})
    public String goToProduct(@PathVariable(value = "id") Long id, Model model){
        Product product = productService.getById(id);
        String category = categoryService.getById(product.getCategoryId()).getTitle();
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        return "product";
    }

}
