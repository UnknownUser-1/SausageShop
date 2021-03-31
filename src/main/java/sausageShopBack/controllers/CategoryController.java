package sausageShopBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sausageShopBack.models.Category;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;
import sausageShopBack.services.impl.ProductServiceImpl;

import java.io.Serializable;
import java.util.List;

@RestController
public class CategoryController implements Serializable {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/category/allcategories")
    public List<Category> getAllCategories(){ return categoryService.getAll(); }

    //Получение одной категории
    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long category_id) {
        return categoryService.getById(category_id);
    }

    //Обновление категории
    @PutMapping("/category/newCategory")
    public void updateCategory(Category category) { categoryService.update(category);}

    //Удаление категории
   /* @DeleteMapping()
    public void  deleteCategory(Category category){ categoryService.delete(category);}
*/
    //Получение продукта по его id
    @GetMapping("/category/product/{id}")
    public Product getProduct(@PathVariable(value = "id") Long product_id) {
        return productService.getById(product_id);
    }

    //Удаление продукта
    @DeleteMapping()
    public void deleteProduct(Product product){productService.delete(product);}

    //Обновление продукта
    @PutMapping("/category/updateProduct")
    public void uptadeProduct(Product product){ productService.update(product);}

    @PostMapping("/category/newProduct")
    public void createProduct(Product product){ productService.save(product);}

    @PostMapping("/category/newCategory")
    public void createCategory(Category category){ categoryService.save(category);}
}
