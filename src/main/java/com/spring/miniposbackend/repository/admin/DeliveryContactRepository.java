package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.miniposbackend.model.admin.DeliveryContact;

@Repository
public interface DeliveryContactRepository extends JpaRepository<DeliveryContact, Long>{

}
