package ru.gb.dao;

import ru.gb.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findAll();

    Optional<Product> findById(long id);

    void saveOrUpdate(Product product);

    void delete(long id);

}
