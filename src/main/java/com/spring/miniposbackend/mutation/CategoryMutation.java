package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.Category;
import com.spring.miniposbackend.service.admin.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMutation implements GraphQLMutationResolver {
    @Autowired
    private CategoryService categoryService;

    public Category createCategory(Category category){
        return this.categoryService.create(category);
    }
}
