package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Address;
import com.spring.miniposbackend.repository.admin.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.ArrayList;
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
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"+addressId));
    }

    public Address create(Address address) {

//        List<Address> addresses = new ArrayList<>();
//
//        if (address.getAddresses().size() > 0) {
//            for (Address a : address.getAddresses()) {
//                if (!this.addressRepository.existsById(a.getId()))
//                    throw new ResourceNotFoundException("The Address is not found!" + a.getId());
//
//                addresses.add(this.show(a.getId()));
//            }
//        }

        if (address.getAddress() != null) {
            this.addressRepository.findById(address.getAddress().getId())
                    .map(address1 -> {

                        address.setAddress(address1);
                        return this.addressRepository.save(address);

                    }).orElseThrow(() -> new ResourceNotFoundException("Not Found" + address.getAddress().getId()));
        }

//        address.setAddresses(addresses);

        return this.addressRepository.save(address);
    }

    public Address update(Integer addressId, Address addressRequest) {

        List<Address> addresses = new ArrayList<>();

        if (addressRequest.getAddresses().size() > 0) {
            for (Address a : addressRequest.getAddresses()) {
                if (!this.addressRepository.existsById(a.getId()))
                    throw new ResourceNotFoundException("The Address is not found!" + a.getId());

                addresses.add(this.show(a.getId()));
            }
        }

        addressRequest.setAddresses(addresses);

        return this.addressRepository.findById(addressId)
                .map(address -> {
                    addressRequest.setId(addressId);
                    return this.addressRepository.save(addressRequest);
                }).orElseThrow(() -> new ResourceNotFoundException("Address is not exist" + addressId));
    }

    public Address delete(Integer addressId) {
        return this.addressRepository.findById(addressId)
                .map(address -> {
                    this.addressRepository.deleteById(addressId);
                    return address;
                }).orElseThrow(() -> new ResourceNotFoundException("Address is not exist" + addressId));
    }

}
