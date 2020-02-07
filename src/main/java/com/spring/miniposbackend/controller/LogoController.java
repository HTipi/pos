package com.spring.miniposbackend.controller;

import com.spring.miniposbackend.model.admin.Logo;
import com.spring.miniposbackend.service.admin.LogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logo")
public class LogoController {

    @Autowired
    private LogoService logoService;


    @GetMapping
    public List<Logo> shows() {
        return this.logoService.shows();
    }

    @GetMapping("/{logoId}/findLogo")
    public Logo show(@PathVariable Integer logoId) {
        return this.logoService.show(logoId);
    }

    @PostMapping("/createLogo")
    public Logo createLogo(@RequestBody Logo logoRequest) {
        return this.logoService.create(logoRequest.getBranch().getId(),
                logoRequest.getCorporate().getId(),
                logoRequest);
    }

    @PostMapping("/updateLogo")
    public Logo updateLogo(@RequestBody Logo logoRequest){
        return this.logoService.create(logoRequest.getBranch().getId(),
                logoRequest.getCorporate().getId(),
                logoRequest);
    }

}
