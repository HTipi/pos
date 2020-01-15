//package com.spring.miniposbackend.mutation;
//
//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.spring.miniposbackend.models.Category;
//import com.spring.miniposbackend.models.Corporate;
//import com.spring.miniposbackend.services.CategoryService;
//import com.spring.miniposbackend.services.CorporateService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CorporateMutation implements GraphQLMutationResolver {
//    @Autowired
//    private CorporateService corporateService;
//
//    public Corporate createCorporate(Corporate corporate, int cate_id)  {
//
//
//        return this.corporateService.createCorporate(corporate,cate_id);
//    }
//}
