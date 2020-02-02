package com.spring.miniposbackend.query.DeliveryContact;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.service.admin.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryContactResolver implements GraphQLResolver<DeliveryContact> {

    @Autowired
    private BranchService branchService;

    public Branch branch(DeliveryContact deliveryContact) {
        return this.branchService.show(deliveryContact.getBranch().getId());
    }

}
