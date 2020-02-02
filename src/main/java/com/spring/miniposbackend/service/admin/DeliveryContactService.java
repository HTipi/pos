package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.DeliveryContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryContactService {

    @Autowired
    private DeliveryContactRepository deliveryContactRepository;
    @Autowired
    private BranchRepository branchRepository;

    public List<DeliveryContact> shows() {
        return this.deliveryContactRepository.findAll();
    }

    public DeliveryContact show(Long id) {
        return this.deliveryContactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Contact not found with id " + id));
    }

    public DeliveryContact enable(Long id) {
        return this.deliveryContactRepository.findById(id)
                .map(deliveryContactData -> {

                    deliveryContactData.setEnable(true);
                    return this.deliveryContactRepository.save(deliveryContactData);

                }).orElseThrow(() -> new ResourceNotFoundException("Delivery Contact not found with id " + id));
    }

    public DeliveryContact disable(Long id) {
        return this.deliveryContactRepository.findById(id)
                .map(deliveryContactData -> {

                    deliveryContactData.setEnable(false);
                    return this.deliveryContactRepository.save(deliveryContactData);

                }).orElseThrow(() -> new ResourceNotFoundException("Delivery Contact not found with id " + id));
    }

    public DeliveryContact create(Integer branchId, DeliveryContact deliveryContactRequest) {

        boolean branch = this.branchRepository.existsById(branchId);

        if (!branch)
            throw new ResourceNotFoundException("The Branch Id is not found!"+ branchId);

        return this.branchRepository.findById(branchId)
                .map(branchData -> {

                    deliveryContactRequest.setBranch(branchData);
                    return this.deliveryContactRepository.save(deliveryContactRequest);

                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));

    }

}
