package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
