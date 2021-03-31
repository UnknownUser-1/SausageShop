package sausageShopBack.services;

import java.util.List;

public interface CrudService<T> {
    T save(T t);
    void update(T t);
    T getById(Long id);
    List<T> getAll();
    void delete(T t);
}
