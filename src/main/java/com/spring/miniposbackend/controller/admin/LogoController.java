//package com.spring.miniposbackend.controller.admin;
//
//import com.spring.miniposbackend.model.admin.Logo;
//import com.spring.miniposbackend.service.admin.LogoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("logo")
//public class LogoController {
//
//    @Autowired
//    private LogoService logoService;
//
//
//    @GetMapping
//    public List<Logo> shows() {
//        return this.logoService.shows();
//    }
//
//    @GetMapping("{logoId}")
//    public Logo show(@PathVariable Integer logoId) {
//        return this.logoService.show(logoId);
//    }
//
//    @PostMapping
//    public Logo createLogo(@RequestParam Integer corporateId,
//                           @RequestParam Integer branchId,
//                           @RequestBody Logo logoRequest) {
//
//        return this.logoService.create(corporateId,
//                branchId,
//                logoRequest);
//
//    }
//
//    @PutMapping
//    public Logo updateLogo(@RequestParam Integer logoId,
//                           @RequestParam Integer corporateId,
//                           @RequestParam Integer branchId,
//                           @RequestBody Logo logoRequest) {
//
//        return this.logoService.update(logoId, corporateId, branchId, logoRequest);
//
//    }
//
//    @DeleteMapping("{logoId}")
//    public Logo delete(@PathVariable Integer logoId) {
//        return this.logoService.delete(logoId);
//    }
//
//}
