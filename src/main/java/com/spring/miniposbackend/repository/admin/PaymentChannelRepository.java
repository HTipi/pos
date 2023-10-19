package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.PaymentChannel;

public interface PaymentChannelRepository extends JpaRepository<PaymentChannel, Integer>{
	
	@Query("select case when count(name)>0 then true else false end from PaymentChannel where name=?1")
	boolean findByName(String name);
	
	
	@Query("select p from PaymentChannel p where name=?1")
	PaymentChannel findByChannelName(String name);

}
