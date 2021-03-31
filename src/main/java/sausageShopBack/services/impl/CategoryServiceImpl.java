package sausageShopBack.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.CategoryDao;
import sausageShopBack.models.Category;
import sausageShopBack.services.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

    @Override
    public Category save(Category category) {
        return this.categoryDao.save(category);
    }

    @Override
    public void update(Category category) {
        this.categoryDao.update(category);
    }

    @Override
    public Category getById(Long id) {
        return this.categoryDao.getById(id);
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public void delete(Category category) {
        this.categoryDao.delete(category);
    }
}
