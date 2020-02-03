package com.spring.miniposbackend.controller;

import com.spring.miniposbackend.model.admin.Social;
import com.spring.miniposbackend.service.admin.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("social")
public class SocialController {

    @Autowired
    private SocialService socialService;

    @GetMapping
    public List<Social> shows() {
        return this.socialService.shows();
    }

    @GetMapping("/findSocialById")
    public Social show(@RequestParam Integer socialId) {
        return this.socialService.show(socialId);
    }

    @PostMapping("/createSocial")
    public Social create(@RequestBody Social socialRequest) {
        return this.socialService.create(socialRequest);
    }

    @PostMapping("/updateSocial")
    public Social update(@RequestBody Social socialRequest) {
        return this.socialService.update(socialRequest.getId(), socialRequest);
    }

    @GetMapping("/enableSocialById")
    public Social enable(@RequestParam Integer socialId) {
        return this.socialService.enable(socialId);
    }

    @GetMapping("/disableSocialById")
    public Social disable(@RequestParam Integer socialId) {
        return this.socialService.enable(socialId);
    }

}
