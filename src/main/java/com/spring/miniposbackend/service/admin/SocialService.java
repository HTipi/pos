package com.spring.miniposbackend.service.admin;


import com.spring.miniposbackend.model.admin.Social;
import com.spring.miniposbackend.repository.admin.SocialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class SocialService {

    @Autowired
    private SocialRepository socialRepository;


    public List<Social> shows() {
        return this.socialRepository.findAll();
    }

    public Social show(int socialId) {
        return socialRepository.findById(socialId)
                .orElseThrow(() -> new ResourceNotFoundException("Social not found with id" + socialId));
    }

    public Social enable(int id) {

        return this.socialRepository.findById(id)
                .map(social -> {

                    social.setEnable(true);
                    return this.socialRepository.save(social);
                }).orElseThrow(() -> new ResourceNotFoundException("Social not found with id" + id));
    }

    public Social disable(int id) {

        return this.socialRepository.findById(id)
                .map(social -> {

                    social.setEnable(false);
                    return this.socialRepository.save(social);
                }).orElseThrow(() -> new ResourceNotFoundException("Social not found with id" + id));
    }

    public Social create(Social data) {
        return this.socialRepository.save(data);
    }

    public Social update(int socialId, Social socialRequest) {
        return socialRepository.findById(socialId)
                .map(social -> {
                    social.setName(socialRequest.getName());
                    social.setImage(socialRequest.getImage());
                    social.setOrder(socialRequest.getOrder());
                    social.setEnable(socialRequest.getEnable());
                    return socialRepository.save(social);
                }).orElseThrow(() -> new ResourceNotFoundException("Social not found with id " + socialId));
    }

}
