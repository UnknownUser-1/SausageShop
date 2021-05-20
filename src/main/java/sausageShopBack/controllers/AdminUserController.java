package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Product;
import sausageShopBack.models.Role;
import sausageShopBack.models.User;
import sausageShopBack.services.impl.ProductServiceImpl;
import sausageShopBack.services.securityServices.UserServiceImpl;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    UserServiceImpl UserServiceImpl;


    @Autowired
    public AdminUserController(UserServiceImpl user) {
        this.UserServiceImpl = user;
    }

    @GetMapping({"/userDelete/{id}"})
    public String deleteToProduct(@PathVariable(value = "id") Long id, Model model) {
        this.UserServiceImpl.delete(UserServiceImpl.getById(id));
        return "redirect:/admin/userPage";
    }

    @GetMapping({"/userPage"})
    public String admin(Model model) {
        model.addAttribute("users", UserServiceImpl.getAll());
        return "admin-user-grid-list";
    }



    //Слишком сильнго надо лезть в реализацию юзера. Мне страшно
//    @GetMapping({"/userUpdate/{id}"})
//    public String updateProduct(@PathVariable(value = "id") Long id, Model model) {
//        User user = UserServiceImpl.getById(id);
//        model.addAttribute("product", product);
//        if(user.getRoles()
//        model.addAttribute("category", user.getRoles().contains().getId()));
//        return "admin-product-edit";
//    }

//    @PostMapping({"/editUser"})
//    public String editProduct(@ModelAttribute("user") User user, @ModelAttribute("role") CatId category) {
//        user.setRoles((Set<Role>) user.getRoles().toArray()[category.aaaa]);
//        UserServiceImpl.update(user);
//        return "redirect:/admin/userPage";
//    }

//    private class CatId {
//        private Integer aaaa;
//
//        public CatId(Integer aaaa) {
//            this.aaaa = aaaa;
//        }
//
//        public Integer getAaaa() {
//            return aaaa;
//        }
//
//        public void setAaaa(Integer aaaa) {
//            this.aaaa = aaaa;
//        }
//    }
}
