package sausageShopBack.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.ProductDao;
import sausageShopBack.models.Product;
import sausageShopBack.services.ProductService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public Product getById(Long id) {
        return productDao.getById(id);
    }

    @Override
    @Transactional
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }
}
