package org.example.redisspring.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.example.redisspring.domain.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{category}")
    public ResponseEntity<String> findCategoryInfo(@PathVariable String category) {
        return ResponseEntity.ok(categoryService.findCategoryInfo(category));
    }
}
