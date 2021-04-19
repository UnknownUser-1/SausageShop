package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopGridListController {


    @RequestMapping({"templates/shop-grid-list.html"})
    public String home() {
        return "shop-grid-list";
    }
}
