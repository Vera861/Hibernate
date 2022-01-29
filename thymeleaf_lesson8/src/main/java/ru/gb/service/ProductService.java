package ru.gb.service;

import ru.gb.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> findAll(Optional<String> nameFilter);

    Optional<ProductDto> findById(Long id);

    ProductDto save(ProductDto productDto);

    void deleteById(Long id);
}
