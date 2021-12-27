package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.PaymentChannel;

public interface PaymentChannelRepository extends JpaRepository<PaymentChannel, Integer>{

}
