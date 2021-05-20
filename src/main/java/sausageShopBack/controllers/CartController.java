package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Cart;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CartServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;
import sausageShopBack.services.securityServices.UserServiceImpl;

@Controller
public class CartController {


    ProductServiceImpl productService;
    CartServiceImpl cartService;
    UserServiceImpl userService;

    @Autowired
    public CartController(ProductServiceImpl productService, CartServiceImpl cartService, UserServiceImpl userService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping({"/product/{id}"})
    public String goToProduct(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("cart", new Cart());
        model.addAttribute("productToSearch", new Product());
        return "product";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String gotoCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("carts", cartService.getAllByUserId(userService.findByUsername(auth.getName()).getId()));
        model.addAttribute("productToSearch", new Product());
        return "cart";
    }


    @RequestMapping(value = "/deleteCart/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable(value = "id") Long id, Model model) {
        cartService.delete(cartService.getById(id));
        return "redirect:/cart";
    }

    @RequestMapping(value = "/product/addToCart", method = RequestMethod.POST)
    public String addProductToCart(@ModelAttribute(value = "cart") Cart cart,
                                   Model model, @ModelAttribute(value = "product") Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        cart.setUserId(userService.findByUsername((auth.getName())));
        cart.setProductId(product);
        cartService.save(cart);
        return "redirect:/cart";
    }

//    @PostMapping({"/editCart"})
//    public String editCart(@ModelAttribute("product") Product product, @ModelAttribute("category") AdminShopGridListController.CatId category) {
//        product.setCategoryId(categoryService.getById(category.aaaa));
//        productService.update(product);
//        return "redirect:/admin/shop";
//    }

    //    @RequestMapping(value = "/buyAllProduct", method = RequestMethod.GET)
//    public String buyAllProduct() {
//        cartService.buyProducts();
//        return "redirect:/shop";
//    }
}
