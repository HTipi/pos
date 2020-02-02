package com.spring.miniposbackend.query.DeliveryContact;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.spring.miniposbackend.model.admin.DeliveryContact;
import com.spring.miniposbackend.service.admin.DeliveryContactService;
import org.springframework.stereotype.Component;

@Component
public class DeliveryContactQuery implements GraphQLQueryResolver {

    private DeliveryContactService deliveryContactService;

    public DeliveryContact deliveryContact(Long id) {
        return this.deliveryContactService.show(id);
    }

}
