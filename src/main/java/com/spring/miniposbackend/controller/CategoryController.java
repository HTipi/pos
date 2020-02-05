package com.spring.miniposbackend.controller;

import com.spring.miniposbackend.model.admin.Category;
import com.spring.miniposbackend.repository.admin.CategoryRepository;
import com.spring.miniposbackend.service.admin.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> shows() {
        return this.categoryService.shows();
    }

    @GetMapping("/findByCategoryId")
    public Category show(@RequestParam Integer categoryId) {
        return this.categoryService.show(categoryId);
    }

    @PostMapping("/createCategory")
    public Category create(@RequestBody Category categoryRequest) {
        return this.categoryService.create(categoryRequest);
    }

    @PostMapping("/updateCategory")
    public Category update(@RequestBody Category categoryRequest) {
        return this.categoryService.update(categoryRequest.getId(), categoryRequest);
    }

    @GetMapping("/enableCategoryById")
    public Category enable(@RequestParam Integer categoryId) {
        return this.categoryService.enable(categoryId);
    }

    @GetMapping("/disableCategoryById")
    public Category disable(@RequestParam Integer categoryId) {
        return this.categoryService.disable(categoryId);
    }

}
