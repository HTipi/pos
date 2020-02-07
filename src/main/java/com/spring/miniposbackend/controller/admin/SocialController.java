package com.spring.miniposbackend.controller.admin;

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

    @GetMapping("active")
    public List<Social> showAllActive() {
        return this.socialService.showAllActive();
    }

    @GetMapping("{socialId}")
    public Social show(@PathVariable Integer socialId) {
        return this.socialService.show(socialId);
    }

    @PostMapping
    public Social create(@RequestBody Social socialRequest) {
        return this.socialService.create(socialRequest);
    }

    @PutMapping
    public Social update(@RequestBody Social socialRequest) {
        return this.socialService.update(socialRequest.getId(), socialRequest);
    }

    @PatchMapping("{socialId}")
    public Social updateStatus(@PathVariable Integer socialId, @RequestBody Boolean status) {
        return this.socialService.updateStatus(socialId, status);
    }

}
