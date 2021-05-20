package sausageShopBack.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sausageShopBack.dao.ProductDao;
import sausageShopBack.models.Product;
import sausageShopBack.services.ProductService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
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

    public List<Product> search(String str) {
        String pro = str;
        ArrayList<Product> productArrayList = new ArrayList<>();
        if (pro.contains("?")) {
            String predQue = pro.substring(0, pro.indexOf("?"));
            String postQue = pro.substring(pro.indexOf("?"), pro.length());
            postQue = postQue.replace("?", "");
            if (!postQue.isEmpty()) {
                predQue = predQue + ".";
                pro = predQue;
                pro = pro + postQue;
            } else {
                predQue = predQue + ".+";
                pro = predQue;
            }
        }
        String finalPro = pro;
        productDao.getAll().stream().filter(product ->
                Pattern.matches(finalPro, product.getName())).forEach(productArrayList::add);
        return productArrayList;
    }

    public List<Product> productCertainCategoryId(Long id) {
        List<Product> products = productDao.getAll();
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategoryId().getId().equals(id)) {
                productList.add(product);
            }
        }
        return productList;
    }
}
