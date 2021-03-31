package sausageShopBack.dao;

import java.util.List;

public interface CrudDao<T> {
    T save(T t);
    void update(T t);
    T getById(Long id);
    List<T> getAll();
    void delete(T t);
}
