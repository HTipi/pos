/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.miniposbackend.service.security;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.spring.miniposbackend.model.security.ClientApplication;
import com.spring.miniposbackend.repository.security.ClientApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HTipi
 */
@Service
public class ClientApplicationService {

    @Autowired
    private ClientApplicationRepository clientApplicationRepository;

    public ClientApplication getByName(String name) {
        return clientApplicationRepository.findFirstByName(name).orElseThrow(() -> new ResourceNotFoundException("Record does not exist", "01"));
    }
}
