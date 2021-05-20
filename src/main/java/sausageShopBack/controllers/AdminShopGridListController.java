package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.comporator.Comporator;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;

@RequestMapping("/admin")
@Controller
public class AdminShopGridListController {

    ProductServiceImpl productService;
    CategoryServiceImpl categoryService;

    Comporator comporator;

    @Autowired
    public AdminShopGridListController(ProductServiceImpl productService,
                                       Comporator comporator, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.comporator = comporator;
        this.categoryService = categoryService;
    }


    @GetMapping({"/productdelete/{id}"})
    public String deleteToProduct(@PathVariable(value = "id") Long id, Model model) {
        this.productService.delete(productService.getById(id));
        return "redirect:/admin/shop";
    }

    @GetMapping({"/productUpdate/{id}"})
    public String updateProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("category", new CatId(product.getCategoryId().getId()));
        return "admin-product-edit";
    }

    @GetMapping({"/addProduct"})
    public String addProduct(Model model) {
        Product product = new Product();
        product.setRating(0);
        model.addAttribute("product", product);
        model.addAttribute("category", new CatId(1L));
        return "admin-product-new";
    }

    @PostMapping({"/saveProduct"})
    public String saveProduct(@ModelAttribute("product") Product product, @ModelAttribute("category") CatId category) {
        product.setCategoryId(categoryService.getById(category.aaaa));
        productService.save(product);
        return "redirect:/admin/shop";
    }

    @PostMapping({"/editProduct"})
    public String editProduct(@ModelAttribute("product") Product product, @ModelAttribute("category") CatId category) {
        product.setCategoryId(categoryService.getById(category.aaaa));
        productService.update(product);
        return "redirect:/admin/shop";
    }

    @RequestMapping(value = "/compareProductName", method = RequestMethod.GET)
    public String compareProductName(Model model) {
        model.addAttribute("productList", comporator.compareProductName(productService.getAll()));
        model.addAttribute("productToSearch", new Product());
        return "admin-shop-grid-list";
    }

    @RequestMapping(value = "/reversedCompareProductName", method = RequestMethod.GET)
    public String reversedCompareProductName(Model model) {
        model.addAttribute("productList", comporator.reversedCompareProductName(productService.getAll()));
        model.addAttribute("productToSearch", new Product());
        return "admin-shop-grid-list";
    }

    @RequestMapping(value = "/compareProductPrice", method = RequestMethod.GET)
    public String compareProductPrice(Model model) {
        model.addAttribute("productList", comporator.compareProductPrice(productService.getAll()));
        model.addAttribute("productToSearch", new Product());
        return "admin-shop-grid-list";
    }

    @RequestMapping(value = "/reversedCompareProductPrice", method = RequestMethod.GET)
    public String reversedCompareProductPrice(Model model) {
        model.addAttribute("productList", comporator.reversedCompareProductPrice(productService.getAll()));
        model.addAttribute("productToSearch", new Product());
        return "admin-shop-grid-list";
    }

    private class CatId {
        private Long aaaa;

        public CatId(Long aaaa) {
            this.aaaa = aaaa;
        }

        public Long getAaaa() {
            return aaaa;
        }

        public void setAaaa(Long aaaa) {
            this.aaaa = aaaa;
        }
    }
}
