package sausegeShop.controllers;

import java.io.Serializable;
import sausegeShop.models.Category;
import java.util.ArrayList;

public class CategoryController implements Serializable{

    private ArrayList<Category> categories = new ArrayList<>();

    public CategoryController(ArrayList<Category> categories){
        if(categories == null)
            throw new IllegalArgumentException("Нельзя добавить пустые категории");
        this.categories = categories;
    }

    public CategoryController(){}

    public Category getCategories(int number) {
        if(number<0||number>categories.size())
            throw new IllegalArgumentException("Такой категории не существует");
        return categories.get(number);
    }

    public void setCategories(Category category,int number) {
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
