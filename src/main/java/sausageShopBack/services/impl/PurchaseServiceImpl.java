package sausageShopBack.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import sausageShopBack.dao.CartDao;
import sausageShopBack.dao.PurchaseDao;
import sausageShopBack.models.Purchase;
import sausageShopBack.services.PurchaseService;

import java.util.List;

public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseDao purchaseDao;

    @Autowired
    public PurchaseServiceImpl(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }

    @Override
    public Purchase save(Purchase purchase) {
        return this.purchaseDao.save(purchase);
    }

    @Override
    public void update(Purchase purchase) {
        this.purchaseDao.update(purchase);
    }

    @Override
    public Purchase getById(Long id) {
        return this.purchaseDao.getById(id);
    }

    @Override
    public List<Purchase> getAll() {
        return this.purchaseDao.getAll();
    }

    @Override
    public void delete(Purchase purchase) {
        this.purchaseDao.delete(purchase);
    }
}
