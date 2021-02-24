/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.miniposbackend.controller.security;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.service.security.ClientApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HTipi
 */
@RestController
@RequestMapping("clientapplication")
public class ClientApplicationController {
    
    @Autowired
    private ClientApplicationService clientApplicationService;
    
    @GetMapping("by-name")
	@PreAuthorize("hasAnyRole('SALE','OWNER','BRANCH')")
	public SuccessResponse getByName(@RequestParam String name) {
		return new SuccessResponse("00", "fetch clientApp", clientApplicationService.getByName(name));
	}
}
