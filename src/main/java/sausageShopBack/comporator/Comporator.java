package sausageShopBack.comporator;

import ch.qos.logback.core.read.ListAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sausageShopBack.models.Category;
import sausageShopBack.models.Product;
import sausageShopBack.services.impl.CategoryServiceImpl;

import java.util.Comparator;
import java.util.List;

@Service
public class Comporator {

    private static CategoryServiceImpl categoryService;

    @Autowired
    public Comporator(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    private Comporator() {
    }

    public List<Product> compareProductPrice(List<Product> products) {
        Comparator<Product> productComparator = Comparator.comparing(Product::getPrice);
        products.sort(productComparator);
        return products;
    }


    public List<Product> compareProductName(List<Product> products) {
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        products.sort(productComparator);
        return products;
    }

    public List<Product> reversedCompareProductPrice(List<Product> products) {
        Comparator<Product> productComparator = Comparator.comparing(Product::getPrice);
        products.sort(productComparator.reversed());
        return products;
    }


    public List<Product> reversedCompareProductName(List<Product> products) {
        Comparator<Product> productComparator = Comparator.comparing(Product::getName);
        products.sort(productComparator.reversed());
        return products;
    }
    public List<Product> compareProductRating(List<Product> products) {
        Comparator<Product> productComparator = Comparator.comparing(Product::getRating);
        products.sort(productComparator);
        return products;
    }
    public List<Product> reversedCompareProductRating(List<Product> products) {
        Comparator<Product> productComparator = Comparator.comparing(Product::getRating);
        products.sort(productComparator.reversed());
        return products;
    }
}