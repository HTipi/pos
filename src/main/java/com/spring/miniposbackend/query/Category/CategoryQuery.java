package com.spring.miniposbackend.query.Category;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.models.Category;
import com.spring.miniposbackend.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryQuery implements GraphQLQueryResolver {

    @Autowired
    private CategoryService categoryService;


    public List<Category> categories()   {

        return this.categoryService.shows(); 

    }



}
