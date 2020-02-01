package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.NotFoundException;
import com.spring.miniposbackend.model.admin.Address;
import com.spring.miniposbackend.repository.admin.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public List<Address> shows() {
        return this.addressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Address show(Integer addressId) {
        return this.addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Not Found", addressId));
    }

    public Address create(Address address) {
        return this.addressRepository.save(address);
    }

    public Address update(Integer addressId, Address addressRequest) {
        return this.addressRepository.findById(addressId)
                .map(address -> {
                    addressRequest.setId(addressId);
                    return this.addressRepository.save(addressRequest);
                }).orElseThrow(() -> new NotFoundException("Address is not exist", addressId));
    }

    public Address delete(Integer addressId) {
        return this.addressRepository.findById(addressId)
                .map(address -> {
                    this.addressRepository.deleteById(addressId);
                    return address;
                }).orElseThrow(() -> new NotFoundException("Address is not exist", addressId));
    }

}
