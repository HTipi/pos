//package com.spring.miniposbackend.services;
//
//import com.coxautodev.graphql.tools.ResolverError;
//import com.spring.miniposbackend.exception.MessageNotFound;
//import com.spring.miniposbackend.models.CustomerPoint;
//import com.spring.miniposbackend.repositories.CustomerPointRepository;
//import com.spring.miniposbackend.repositories.CustomerRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomerPointService {
//
//    @Autowired
//    private CustomerPointRepository customerPointRepository;
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    public CustomerPoint createCustomerPoint(CustomerPoint data , int cust_id){
//
//        boolean customer = this.customerRepository.existsById(cust_id);
//
//        if(!customer){
//            throw new MessageNotFound("The Customer is not found !!!",cust_id,"cust_id");
//        }
//
//        return this.customerRepository.findById(cust_id).map(post -> {
//            data.setCustomer(post);
//            data.setPoint_create_at(new Date());
//            data.setPoint_update_at(new Date());
//            return this.customerPointRepository.save(data);
//        }).orElseThrow(() -> new ResolverError("Not Found",new Throwable()));
//
//    }
//
//    public CustomerPoint updateCustomerPoint(CustomerPoint data, int cust_id){
//
//        boolean customer = this.customerRepository.existsById(cust_id);
//
//        if(!customer){
//            throw new MessageNotFound("The Customer is not found !!!",cust_id,"cust_id");
//        }
//
//        return this.customerRepository.findById(cust_id).map(post -> {
//
//            data.setCustomer(post);
//            data.setPoint_update_at(new Date());
//            return this.customerPointRepository.save(data);
//
//        }).orElseThrow(() -> new ResolverError("Not Found",new Throwable()));
//
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<CustomerPoint> getCustomerPoint(int id){
//
//        boolean customerPoint = this.customerPointRepository.existsById(id);
//
//        if(!customerPoint){
//            throw new MessageNotFound("The Customer Point is not found !!!",id,"id");
//        }
//
//        return this.customerPointRepository.findById(id);
//    }
//}
