package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Category;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;
import sausageShopBack.services.securityServices.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
    CategoryServiceImpl categoryService;

    @Autowired
    public AdminCategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/categories"})
    public String admin(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin-category-grid-list";
    }

    @GetMapping({"/categoryDelete/{id}"})
    public String deleteToProduct(@PathVariable(value = "id") Long id, Model model) {
        this.categoryService.delete(categoryService.getById(id));
        return "redirect:/admin/categories";
    }

    @GetMapping({"/categoryUpdate/{id}"})
    public String updateProduct(@PathVariable(value = "id") Long id, Model model) {
        Category category = categoryService.getById(id);
        model.addAttribute("category", category);
        return "admin-category-edit";
    }

    @GetMapping({"/addCategory"})
    public String addProduct(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin-category-new";
    }

    @PostMapping({"/saveCategory"})
    public String saveProduct(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @PostMapping({"/editCategory"})
    public String editProduct(@ModelAttribute("category") Category category) {
        categoryService.update(category);
        return "redirect:/admin/categories";
    }
}
