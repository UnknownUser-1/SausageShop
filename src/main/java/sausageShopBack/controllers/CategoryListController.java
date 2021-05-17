package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class CategoryListController {

    CategoryServiceImpl categoryService;

    ProductServiceImpl productService;

    @Autowired
    public CategoryListController(CategoryServiceImpl categoryService,
                                  ProductServiceImpl productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"categoryList"})
    public String home(Model model) {
        model.addAttribute("categoryList", categoryService.getAll());
        model.addAttribute("productToSearch", new Product());
        return "categoryList";
    }

    @RequestMapping(value = "/productInCategory/{id}", method = RequestMethod.GET)
    public String productInCategory(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("productList", productService.productCertainCategoryId(id));
        model.addAttribute("productToSearch", new Product());
        return "shop-grid-list";
    }
}
