package sausegeShop.controllers;

import java.io.Serializable;
import sausegeShop.models.Category;
import sausegeShop.models.Product;

import java.util.ArrayList;

public class CategoryController implements Serializable{

    private ArrayList<Category> categories = new ArrayList<>();

    public CategoryController(ArrayList<Category> categories){
        if(categories == null)
            throw new IllegalArgumentException("Нельзя добавить пустые категории");
        this.categories = categories;
    }

    public CategoryController(){
        Category kolbasky = new Category("Колбаски");
        ArrayList<Product> products = new ArrayList<>();
        Product sausage = Product.productFactory("Сосиски", 100, "Небольшие вкусные штучки", "100% курица",kolbasky);
        Product cervelat = Product.productFactory("Сервелат", 500, "Классная копченая колбаска", "Кто-то умер, чтобы попасть туда", kolbasky);
        Product cervelat2 = Product.productFactory("Останки финна", 280, "Откопанный из вечной мерзлоты солдат после Советско-Финской", "Чистокровный финн", kolbasky);
        products.add(0,sausage);
        products.add(1,cervelat);
        products.add(2,cervelat2);
        kolbasky.setProducts(products);
        Category meat = new Category("Мяско");
        Category salt = new Category("Соль");
        Category kisloe = new Category("Кислое");
        Category gorkoy = new Category("Горькое");
        Category syroe = new Category("Сырое");
        Category myagkoe = new Category("Мягкое");
        addCategories(kolbasky, 0);
        addCategories(meat, 1);
        addCategories(salt,2);
        addCategories(kisloe,3);
        addCategories(gorkoy,4);
        addCategories(syroe,5);
        addCategories(myagkoe,6);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
    
    public Category getCategory(int number) {
        if(number<0||number>categories.size())
            throw new IllegalArgumentException("Такой категории не существует");
        return categories.get(number);
    }

    public void setCategory(Category category,int number) {
        if(category == null || number<0)
            throw new IllegalArgumentException("Что-то пошло не так");
        this.categories.set(number,category);
    }

    public void addCategories(Category category, int number){
        if(category == null || number<0)
            throw  new IllegalArgumentException("Что-то пошло не так");
        this.categories.add(number,category);
    }

    public void deleteCategories(int number) {
        if (number<0||number>size())
            throw  new IllegalArgumentException("Что-то пошло не так");
        this.categories.remove(number);
    }

    public int size(){
        return categories.size();
    }

}
