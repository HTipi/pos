//package com.spring.miniposbackend.controller.admin;
//
//import com.spring.miniposbackend.model.admin.Category;
//import com.spring.miniposbackend.service.admin.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:9090")
//@RequestMapping("category")
//public class CategoryController {
//
//    @Autowired
//    private CategoryService categoryService;
//    
//    @GetMapping
//    public List<Category> shows() {
//        return this.categoryService.shows();
//    }
//
//    @GetMapping("active")
//    public List<Category> showAllActive() {
//        return this.categoryService.showActiveOnly();
//    }
//
//    @GetMapping("{categoryId}")
//    public Category show(@PathVariable Integer categoryId) {
//        return this.categoryService.show(categoryId);
//    }
//
//    @PostMapping
//    public Category create(@RequestBody Category categoryRequest) {
//        return this.categoryService.create(categoryRequest);
//    }
//
//    @PutMapping
//    public Category update(@RequestParam Integer categoryId, @RequestBody Category categoryRequest) {
//        return this.categoryService.update(categoryId, categoryRequest);
//    }
//
//    @PatchMapping("{categoryId}")
//    public Category updateStatus(@PathVariable Integer categoryId, @RequestBody boolean status) {
//        return this.categoryService.updateStatus(categoryId, status);
//    }
//
//}
