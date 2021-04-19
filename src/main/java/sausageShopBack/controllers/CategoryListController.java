package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sausageShopBack.services.impl.CategoryServiceImpl;

@Controller
public class CategoryListController {

    @Autowired
    CategoryServiceImpl categoryService;

    @RequestMapping({"templates/categoryList.html"})
    public String home() {
        return "categoryList";
    }
}
