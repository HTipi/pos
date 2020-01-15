//package com.spring.miniposbackend.query.Corporate;
//
//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.coxautodev.graphql.tools.GraphQLResolver;
//import com.spring.miniposbackend.models.Category;
//import com.spring.miniposbackend.models.Corporate;
//import com.spring.miniposbackend.repositories.CategoryRepository;
//import com.spring.miniposbackend.services.CorporateService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class CorporateQuery implements GraphQLQueryResolver {
//
//    @Autowired
//    private CorporateService corporateService;
//
//    public List<Corporate> corporates()   {
//
//        return this.corporateService.getAllCorporates();
//
//    }
//
//
//}
