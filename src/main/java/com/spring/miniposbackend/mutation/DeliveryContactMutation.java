package com.spring.miniposbackend.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.service.admin.DeliveryContactService;
import org.springframework.stereotype.Component;

@Component
public class DeliveryContactMutation implements GraphQLMutationResolver {

    private DeliveryContactService deliveryContactService;

    public DeliveryContact createDeliveryContact(Integer branchId, DeliveryContact deliveryContactRequest) {
        return this.deliveryContactService.create(branchId, deliveryContactRequest);
    }

    public DeliveryContact updateDeliveryContact(Integer branchId, DeliveryContact deliveryContactRequest) {
        return this.deliveryContactService.create(branchId, deliveryContactRequest);
    }

}
