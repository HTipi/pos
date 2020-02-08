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

    @PostMapping
    public Address create(@RequestBody Address address){
        return this.addressService.create(address);
    }

    @PutMapping("{addressId}")
    public Address update(@PathVariable Integer addressId, @RequestBody Address address){
        return this.addressService.update(addressId, address);
    }


}
