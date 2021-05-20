package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Cart;
import sausageShopBack.models.Product;
import sausageShopBack.models.Purchase;
import sausageShopBack.services.impl.CartServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;
import sausageShopBack.services.impl.PurchaseServiceImpl;
import sausageShopBack.services.securityServices.UserServiceImpl;

import java.util.List;

@Controller
public class CartController {


    ProductServiceImpl productService;
    CartServiceImpl cartService;
    UserServiceImpl userService;
    PurchaseServiceImpl purchaseService;

    @Autowired
    public CartController(ProductServiceImpl productService, CartServiceImpl cartService, UserServiceImpl userService,
                          PurchaseServiceImpl purchaseService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.purchaseService = purchaseService;
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

    @RequestMapping(value = "/buyAll", method = RequestMethod.GET)
    public String buyAllProduct() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Cart> cartList = cartService.getAllByUserId(userService.findByUsername(auth.getName()).getId());
        for (Cart cart : cartList) {
            Purchase purchase = new Purchase();
            purchase.setProductId(cart.getProductId());
            purchase.setUserId(cart.getUserId());
            purchase.setQuantity(cart.getQuantity());
            cartService.delete(cart);
            purchaseService.save(purchase);
        }
        return "redirect:/shop";
    }

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.GET)
    public String buyProduct(@PathVariable(value = "id") Long id) {
        Cart cart = cartService.getById(id);
        Purchase purchase = new Purchase();
        purchase.setProductId(cart.getProductId());
        purchase.setUserId(cart.getUserId());
        purchase.setQuantity(cart.getQuantity());
        cartService.delete(cart);
        purchaseService.save(purchase);
        return "redirect:/shop";
    }

    @RequestMapping(value = "/evaluate/{id}", method = RequestMethod.GET)
    public String evaluate(@PathVariable(value = "id") Long id, @ModelAttribute(value = "evaluation") Purchase p) {
        purchaseService.update(p);
        return "redirect:/myaccount";
    }

    //_____________________________________________________________


}
