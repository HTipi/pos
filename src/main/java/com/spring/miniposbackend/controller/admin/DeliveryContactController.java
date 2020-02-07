package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.service.admin.DeliveryContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("deliveryContact")
public class DeliveryContactController {

    @Autowired
    private DeliveryContactService deliveryContactService;

    @GetMapping
    public List<DeliveryContact> shows() {
        return this.deliveryContactService.shows();
    }

    @GetMapping("active")
    public List<DeliveryContact> showAllActive() {
        return this.deliveryContactService.showAllActive();
    }

    @GetMapping("{deliveryContactId}")
    public DeliveryContact show(@PathVariable Integer deliveryContactId) {
        return this.deliveryContactService.show(deliveryContactId);
    }

    @PostMapping
    public DeliveryContact create(@RequestBody DeliveryContact deliveryContact) {
        return this.deliveryContactService.create(deliveryContact.getBranch().getId(), deliveryContact);
    }


    @PatchMapping("{deliveryContactId}")
    public DeliveryContact updateStatus(@PathVariable Integer deliveryContactId, @RequestBody boolean status) {
        return this.deliveryContactService.updateStatus(deliveryContactId, status);
    }

}
