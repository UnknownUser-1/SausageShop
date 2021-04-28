package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class ShopGridListController {

    ProductServiceImpl productService;

    @Autowired
    public ShopGridListController(ProductServiceImpl productService){
        this.productService = productService;
    }

    @GetMapping({"/shop"})
    public String home(Model model) {
        model.addAttribute("productList", productService.getAll());
        return "shop-grid-list";
    }
}
