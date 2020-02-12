package com.spring.miniposbackend.controller.admin;

import com.spring.miniposbackend.model.admin.Address;
import com.spring.miniposbackend.service.admin.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @GetMapping
    public List<Address> shows() {
        return this.addressService.shows();
    }

    @GetMapping("{addressId}")
    public Address show(@PathVariable Integer addressId) {
        return this.addressService.show(addressId);
    }

    @GetMapping("{parentId}")
    public List<Address> getAddressByParentId(@PathVariable Integer parentId) {
        return this.addressService.showsByPartnerId(parentId);
    }

    @PostMapping
    public Address create(@RequestParam Integer parentId, @RequestBody Address address) {
        return this.addressService.create(parentId, address);
    }

    @PutMapping
    public Address update(@RequestParam Integer addressId, @RequestParam Integer parentId, @RequestBody Address address) {
        return this.addressService.update(addressId, parentId, address);
    }

}
