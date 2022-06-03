package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchPaymentIdentity;
public interface BranchPaymentChannelRepository extends JpaRepository<BranchPaymentChannel, BranchPaymentIdentity>{
	
	@Query(value = "select a from BranchPaymentChannel a where a.branchPaymentIdentity.branch.id=?1")
	List<BranchPaymentChannel> findByBranchId(Integer branchId);
}
