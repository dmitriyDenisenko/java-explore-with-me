package ru.practicum.main_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_server.dto.CategoryDto;
import ru.practicum.main_server.dto.NewCategoryDto;
import ru.practicum.main_server.exception.ObjectNotFoundException;
import ru.practicum.main_server.service.CategoryService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
public class CategoryAdminController {
    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        log.info("update category");
        return categoryService.updateCategory(categoryDto);
    }

    @PostMapping
    public CategoryDto createCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("create category");
        return categoryService.createCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.info("deleteCategory {}", catId);
        categoryService.deleteCategory(catId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, String>> handleIncorrectParameterException(ObjectNotFoundException e) {
        log.warn(e.getMessage());
        Map<String, String> resp = new HashMap<>();
        resp.put("error", e.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }
}
