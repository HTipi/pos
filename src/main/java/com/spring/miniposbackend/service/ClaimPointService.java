package com.spring.miniposbackend.service;
//package com.spring.miniposbackend.services;
//
//import com.coxautodev.graphql.tools.ResolverError;
//import com.spring.miniposbackend.exception.MessageNotFound;
//import com.spring.miniposbackend.models.ClaimPoint;
//import com.spring.miniposbackend.repositories.ClaimPointRepository;
//import com.spring.miniposbackend.repositories.CustomerPointRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.Optional;
//
//@Service
//public class ClaimPointService {
//
//    @Autowired
//    private ClaimPointRepository claimPointRepository;
//    @Autowired
//    private CustomerPointRepository customerPointRepository;
//
//    @Transactional(readOnly = true)
//    public Optional<ClaimPoint> getClaimPoint(int id){
//
//        boolean point = this.claimPointRepository.existsById(id);
//
//        if(!point)
//            throw new MessageNotFound("This Customer Point is not found !", id, "point_id");
//
//
//        return this.claimPointRepository.findById(id);
//    }
//
//    public ClaimPoint createClaimPoint(ClaimPoint data, int point_id) {
//        boolean customerPoint = this.customerPointRepository.existsById(point_id);
//
//        if (!customerPoint)
//            throw new MessageNotFound("This Customer Point is not found !", point_id, "point_id");
//
//        return this.customerPointRepository.findById(point_id).map(post -> {
//            data.setCustomerPoint(post);
//            data.setClaim_create_at(new Date());
//            return this.claimPointRepository.save(data);
//        }).orElseThrow(() -> new ResolverError("Not Found",new Throwable()));
//
//    }
//
//    public ClaimPoint updateClaimPoint(ClaimPoint data, int point_id) {
//
//        boolean customerPoint = this.customerPointRepository.existsById(point_id);
//
//        if (!customerPoint)
//            throw new MessageNotFound("This Customer Point is not found !", point_id, "point_id");
//
//        return this.customerPointRepository.findById(point_id).map(post -> {
//            data.setCustomerPoint(post);
//            data.setClaim_create_at(new Date());
//            return this.claimPointRepository.save(data);
//        }).orElseThrow(() -> new ResolverError("Not Found", new Throwable()));
//
//    }
//
//}
