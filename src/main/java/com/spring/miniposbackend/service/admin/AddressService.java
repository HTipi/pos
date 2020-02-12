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
                .orElseThrow(() -> new ResourceNotFoundException("Not Found" + addressId));
    }

    public List<Address> showsByPartnerId(Integer parentId) {
        return this.addressRepository.findAllAddressByParentId(parentId);
    }

    public Address create(Integer parentId, Address addressRequest) {

        if (parentId > 0) {
            return this.addressRepository.findById(parentId)
                    .map(partnerAddress -> {
                        addressRequest.setAddress(partnerAddress);
                        return this.addressRepository.save(addressRequest);
                    }).orElseThrow(() -> new ResourceNotFoundException("Not found with parent address id " + parentId));
        } else {
            return this.addressRepository.save(addressRequest);
        }

    }

    public Address update(Integer addressId, Integer parentId, Address addressRequest) {

        if (parentId > 0) {

            if (!this.addressRepository.existsById(parentId))
                throw new ResourceNotFoundException("The Parent Address Id is not found!" + parentId);

            return this.addressRepository.findById(addressId)
                    .map(address -> {

                        return this.addressRepository.findById(parentId)
                                .map(parentAddress -> {

                                    address.setAddress(parentAddress);
                                    address.setCode(addressRequest.getCode());
                                    address.setName(addressRequest.getName());
                                    address.setNameKh(addressRequest.getNameKh());

                                    return this.addressRepository.save(address);

                                }).orElseThrow(() -> new ResourceNotFoundException("Parent Address is not exist" + parentId));

                    }).orElseThrow(() -> new ResourceNotFoundException("Address is not exist" + addressId));


        } else {
            return this.addressRepository.findById(addressId)
                    .map(address -> {
                        address.setCode(addressRequest.getCode());
                        address.setName(addressRequest.getName());
                        address.setNameKh(addressRequest.getNameKh());
                        return this.addressRepository.save(address);

                    }).orElseThrow(() -> new ResourceNotFoundException("Address is not exist" + addressId));
        }

    }

    public Address delete(Integer addressId) {
        return this.addressRepository.findById(addressId)
                .map(address -> {
                    this.addressRepository.deleteById(addressId);
                    return address;
                }).orElseThrow(() -> new ResourceNotFoundException("Address is not exist" + addressId));
    }

}
