package sausageShopBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    @RequestMapping("/")
    public String home() {
        return "main";
    }

    @RequestMapping("/RegisterHtml.html")
    public String register(){
        return "registerHtml";
    }

    @RequestMapping("/main.html")
    public String login() {
        return "main";
    }
}
