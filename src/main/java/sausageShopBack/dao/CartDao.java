package sausageShopBack.dao;

import sausageShopBack.models.Cart;

import java.util.List;

public interface CartDao  extends CrudDao<Cart> {
    List<Cart> findAllByUserId(Long userId);
}
