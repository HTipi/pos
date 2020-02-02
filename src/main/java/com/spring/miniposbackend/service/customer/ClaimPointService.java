package com.spring.miniposbackend.service.customer;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.customer.ClaimPoint;
import com.spring.miniposbackend.repository.customer.ClaimPointRepository;
import com.spring.miniposbackend.repository.customer.CustomerPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimPointService {

    @Autowired
    private ClaimPointRepository claimPointRepository;
    @Autowired
    private CustomerPointRepository customerPointRepository;


    public Optional<ClaimPoint> show(int id) {

        boolean point = this.claimPointRepository.existsById(id);

        if (!point)
            throw new ResourceNotFoundException("This Customer Point is not found !"+ id);


        return this.claimPointRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ClaimPoint> shows() {
        return this.claimPointRepository.findAll();
    }

    public ClaimPoint create(ClaimPoint data, int point_id) {
        boolean customerPoint = this.customerPointRepository.existsById(point_id);

        if (!customerPoint)
            throw new ResourceNotFoundException("This Customer Point is not found !"+ point_id);

        return this.customerPointRepository.findById(point_id).map(post -> {
            data.setCustomerPoint(post);
            return this.claimPointRepository.save(data);
        }).orElseThrow(() -> new ResourceNotFoundException("Not Found" + new Throwable()));

    }

    public ClaimPoint update(ClaimPoint data, int point_id) {

        boolean customerPoint = this.customerPointRepository.existsById(point_id);

        if (!customerPoint)
            throw new ResourceNotFoundException("This Customer Point is not found !"+ point_id);

        return this.customerPointRepository.findById(point_id).map(post -> {
            data.setCustomerPoint(post);
            return this.claimPointRepository.save(data);
        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

    }

    public ClaimPoint enable(int id) {

        boolean claimPoint = this.claimPointRepository.existsById(id);

        if (!claimPoint)
            throw new ResourceNotFoundException("This Claim Point is not found !"+ id);

        return this.claimPointRepository.findById(id)
                .map(claim -> {

                    claim.setEnable(true);
                    return this.claimPointRepository.save(claim);

                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

    public ClaimPoint disable(int id) {

        boolean claimPoint = this.claimPointRepository.existsById(id);

        if (!claimPoint)
            throw new ResourceNotFoundException("This Claim Point is not found !" + id);

        return this.claimPointRepository.findById(id)
                .map(claim -> {

                    claim.setEnable(false);
                    return this.claimPointRepository.save(claim);

                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
    }

}
