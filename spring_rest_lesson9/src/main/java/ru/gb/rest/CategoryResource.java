package ru.gb.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.controller.NotFoundException;
import ru.gb.persist.Category;
import ru.gb.persist.CategoryRepository;
import ru.gb.service.dto.ProductDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryResource {

    private final CategoryRepository categoryRepository;

    public CategoryResource(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> search(
            @RequestParam("nameFilter") Optional<String> nameFilter,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("sort") Optional<String> sort) {

        return categoryRepository.findAll(
                nameFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sort.filter(s -> !s.isBlank()).orElse("id"));
    }

    @GetMapping("/{id}")
    public Category findOne(@PathVariable("id") Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        if (category.getId() != null) {
            throw new IllegalArgumentException("For productDto creation id have to be null");
        }
        return categoryRepository.save(category);
    }

    @PutMapping
    public Category update(@RequestBody Category category) {
        if (category.getId() == null) {
            throw new IllegalArgumentException("For productDto creation id have to be not null");
        }
        return categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundExceptionHandler(NotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto IllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorDto(ex.getMessage());
    }
}
