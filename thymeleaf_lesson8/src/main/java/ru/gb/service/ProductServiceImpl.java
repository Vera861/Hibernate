package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.persist.Product;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.ProductSpecification;
import ru.gb.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAll(Optional<String> nameFilter) {
        Specification<Product> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        return productRepository.findAll(spec).stream()
                .map(ProductServiceImpl::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductServiceImpl::convertToDto);
    }

    @Override
    public ProductDto save(ProductDto productDto) {

        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice());
        Product saved = productRepository.save(product);
        return convertToDto(saved);

    }

    @Override
    public void deleteById(Long id) {

    }

    private static ProductDto convertToDto(Product prod) {
        return new ProductDto(
                prod.getId(),
                prod.getName(),
                prod.getDescription(),
                prod.getPrice());
    }
}
