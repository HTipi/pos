package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.DeliveryContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryContactService {

    @Autowired
    private DeliveryContactRepository deliveryContactRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Transactional(readOnly = true)
    public List<DeliveryContact> shows() {
        return this.deliveryContactRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<DeliveryContact> showAllActive() {
        return this.deliveryContactRepository.findAllActive();
    }

    @Transactional(readOnly = true)
    public List<DeliveryContact> showAllActiveByBranchId(Integer branchId){
        return this.deliveryContactRepository.findAllActiveByBranchId(branchId);
    }

    @Transactional(readOnly = true)
    public DeliveryContact show(Integer id) {
        return this.deliveryContactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery Contact not found with id " + id));
    }

    public DeliveryContact enable(Integer id) {
        return this.deliveryContactRepository.findById(id)
                .map(deliveryContactData -> {

                    deliveryContactData.setEnable(true);
                    return this.deliveryContactRepository.save(deliveryContactData);

                }).orElseThrow(() -> new ResourceNotFoundException("Delivery Contact not found with id " + id));
    }

    public DeliveryContact disable(Integer id) {
        return this.deliveryContactRepository.findById(id)
                .map(deliveryContactData -> {

                    deliveryContactData.setEnable(false);
                    return this.deliveryContactRepository.save(deliveryContactData);

                }).orElseThrow(() -> new ResourceNotFoundException("Delivery Contact not found with id " + id));
    }

    public DeliveryContact create(Integer branchId, DeliveryContact deliveryContactRequest) {


        if (!this.branchRepository.existsById(branchId))
            throw new ResourceNotFoundException("The Branch Id is not found!" + branchId);

        return this.branchRepository.findById(branchId)
                .map(branchData -> {

                    deliveryContactRequest.setBranch(branchData);
                    return this.deliveryContactRepository.save(deliveryContactRequest);

                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));

    }

    public DeliveryContact update(Integer delivId, Integer branchId, DeliveryContact deliveryContactRequest) {

        if (!this.branchRepository.existsById(branchId))
            throw new ResourceNotFoundException("The Branch Id is not found!" + branchId);

        if (!this.deliveryContactRepository.existsById(delivId))
            throw new ResourceNotFoundException("The Delivery Contact Id is not found!" + delivId);

        return this.deliveryContactRepository.findById(delivId)
                .map(deliveryContact -> {

                    return this.branchRepository.findById(branchId)
                            .map(branch -> {

                                deliveryContact.setBranch(branch);
                                deliveryContact.setTelephone(deliveryContactRequest.getTelephone());
                                return this.deliveryContactRepository.save(deliveryContact);

                            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

    }

    public DeliveryContact updateStatus(Integer deliveryContactId, Boolean status) {
        return this.deliveryContactRepository.findById(deliveryContactId)
                .map(deliveryContact -> {
                    deliveryContact.setEnable(status);
                    return this.deliveryContactRepository.save(deliveryContact);
                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + deliveryContactId));
    }

}
