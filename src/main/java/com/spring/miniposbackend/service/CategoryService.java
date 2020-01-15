package com.spring.miniposbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.NotFoundException;
import com.spring.miniposbackend.model.admin.Category;
import com.spring.miniposbackend.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Transactional(readOnly = true)
    public List<Category> shows(){
        return categoryRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Category> showActiveOnly(){
        return categoryRepository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    public Category show(Integer categoryId){
        return categoryRepository.findById(categoryId)
        		.orElseThrow(() -> new NotFoundException("Category is not exist",categoryId));
    }

    public Category create(Category category) {
        return  categoryRepository.save(category);
    }
    
    public Category update(Integer categoryId,Category categoryRequest) {
        return  categoryRepository.findById(categoryId)
				.map(category -> {
					category.setName(categoryRequest.getName());
					category.setNameKh(categoryRequest.getNameKh());
					category.setEnable(categoryRequest.isEnable());
					return categoryRepository.save(category);
				}).orElseThrow(() -> new NotFoundException("Category is not exist" , categoryId));
    }
    
    public Category enable(Integer categoryId) {
        return  categoryRepository.findById(categoryId)
				.map(category -> {
					category.setEnable(true);
					return categoryRepository.save(category);
				}).orElseThrow(() -> new NotFoundException("Category is not exist" , categoryId));
    }
    
    public Category disable(Integer categoryId) {
        return  categoryRepository.findById(categoryId)
				.map(category -> {
					category.setEnable(false);
					return categoryRepository.save(category);
				}).orElseThrow(() -> new NotFoundException("Category is not exist" , categoryId));
    }
    
    
    
    
}
