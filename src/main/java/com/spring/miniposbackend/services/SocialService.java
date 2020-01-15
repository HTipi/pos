//package com.spring.miniposbackend.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.spring.miniposbackend.exception.ResourceNotFoundException;
//import com.spring.miniposbackend.models.Social;
//import com.spring.miniposbackend.repositories.SocialRepository;
//
//@Service
//public class SocialService {
//
//    @Autowired
//    private SocialRepository socialRepository;
//
//
//    public Social show(int socialId) {
//        return socialRepository.findById(socialId)
//                .orElseThrow(() -> new ResourceNotFoundException("Social not found with id" + socialId));
//    }
//
//    public Social create(Social data) {
//        return this.socialRepository.save(data);
//    }
//
//    public Social update(int socialId,Social socialRequest) {
//    return  socialRepository.findById(socialId)
//			.map(social -> {
//				social.setName(socialRequest.getName());
//				social.setImage(socialRequest.getImage());
//				social.setOrder(socialRequest.getOrder());
//				social.setEnable(socialRequest.isEnable());
//				return socialRepository.save(social);
//			}).orElseThrow(() -> new ResourceNotFoundException("Social not found with id " + socialId));
//    }
//
//}
