package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.BasketServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;

@Controller
public class CartController {

    /**
     * Это ужаснейший костыль для того что передавать в корзину айди продукта
     */
    private Long id;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    BasketServiceImpl basketService;

    @GetMapping({"/product/{id}"})
    public String goToProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getById(id);
        this.id = id;
        model.addAttribute("product", product);
        model.addAttribute("productToCart", new Product());
        model.addAttribute("productToSearch", new Product());
        return "product";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("product", basketService.getAllProduct());
        model.addAttribute("productToSearch", new Product());
        return "cart";
    }

    @RequestMapping(value = "/productdeleteInCart/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable(value = "id") Long id, Model model) {
        basketService.deleteProductInBasket(id);
        return "redirect:/cart";
    }

    /**
     * Данный метод нужно повесить на кнопку(которую надо создать)
     *
     * @return
     */
    @RequestMapping(value = "/buyAllProduct", method = RequestMethod.GET)
    public String buyAllProduct() {
        basketService.buyProducts();
        return "redirect:/shop";
    }

    /**
     * Это просто ужасно, но я не успел придумать что-то лучше
     */
    @RequestMapping(value = "/product/addToCart", method = RequestMethod.POST)
    public String addProductToCart(@ModelAttribute(value = "newProduct") Product product,
                                   Model model) {
        Product productToCart = productService.getById(id);
        productToCart.setCount(product.getCount());
        basketService.addProductToBasket(productToCart);
        return "redirect:/cart";
    }
}
