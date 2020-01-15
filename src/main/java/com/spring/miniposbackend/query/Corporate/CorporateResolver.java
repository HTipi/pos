//package com.spring.miniposbackend.query.Corporate;
//
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.Category;
//import com.spring.miniposbackend.models.Corporate;
//import com.spring.miniposbackend.repositories.CategoryRepository;
//import com.spring.miniposbackend.services.CategoryService;
//import com.spring.miniposbackend.services.CorporateService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class CorporateResolver implements GraphQLResolver<Corporate> {
//
//    @Autowired
//    private CorporateService corporateService;
//
//    public Category category(Corporate corporate){
//
//        return this.corporateService.getCategory(corporate);
//    }
//
//}
