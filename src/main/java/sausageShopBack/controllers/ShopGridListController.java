package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sausageShopBack.comporator.Comporator;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class ShopGridListController {

    ProductServiceImpl productService;

    Comporator comporator;

    @Autowired
    public ShopGridListController(ProductServiceImpl productService,
                                  Comporator comporator) {
        this.productService = productService;
        this.comporator = comporator;
    }

    @GetMapping({"/shop"})
    public String home(Model model) {
        model.addAttribute("productList", productService.getAll());
        model.addAttribute("productToSearch", new Product());
        return "shop-grid-list";
    }

    @RequestMapping(value = "/compareProductName", method = RequestMethod.GET)
    public String compareProductName(Model model) {
        model.addAttribute("productList", comporator.compareProductName(productService.getAll()));
        return "shop-grid-list";
    }

    @RequestMapping(value = "/reversedCompareProductName", method = RequestMethod.GET)
    public String reversedCompareProductName(Model model) {
        model.addAttribute("productList", comporator.reversedCompareProductName(productService.getAll()));
        return "shop-grid-list";
    }

    @RequestMapping(value = "/compareProductPrice", method = RequestMethod.GET)
    public String compareProductPrice(Model model) {
        model.addAttribute("productList", comporator.compareProductPrice(productService.getAll()));
        return "shop-grid-list";
    }

    @RequestMapping(value = "/reversedCompareProductPrice", method = RequestMethod.GET)
    public String reversedCompareProductPrice(Model model) {
        model.addAttribute("productList", comporator.reversedCompareProductPrice(productService.getAll()));
        return "shop-grid-list";
    }
}
