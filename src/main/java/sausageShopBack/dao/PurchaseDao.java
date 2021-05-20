package sausageShopBack.dao;

import sausageShopBack.models.Cart;
import sausageShopBack.models.Purchase;

import java.util.List;

public interface PurchaseDao  extends CrudDao<Purchase> {
    List<Purchase> findAllByUserId(Long userId);
}
