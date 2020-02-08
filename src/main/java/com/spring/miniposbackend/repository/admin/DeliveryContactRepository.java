package com.spring.miniposbackend.repository.admin;

import com.spring.miniposbackend.model.admin.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.DeliveryContact;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryContactRepository extends JpaRepository<DeliveryContact, Integer>{
    @Query(value = "select d from DeliveryContact d where d.enable=true")
    List<DeliveryContact> findAllActive();
}
