package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;

@Controller
public class CategoryListController {

    CategoryServiceImpl categoryService;

    @Autowired
    public CategoryListController(CategoryServiceImpl categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping({"categoryList"})
    public String home(Model model) {
        model.addAttribute("categoryList", categoryService.getAll());
        model.addAttribute("productToSearch", new Product());
        return "categoryList";
    }
}
