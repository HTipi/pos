package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchPaymentIdentity;
public interface BranchPaymentChannelRepository extends JpaRepository<BranchPaymentChannel, BranchPaymentIdentity>{
	
	@Query(value = "select a from BranchPaymentChannel a where a.branchPaymentIdentity.branch.id=?1")
	List<BranchPaymentChannel> findByBranchId(Integer branchId);
	
	@Query(value = "select a from BranchPaymentChannel a where a.branchPaymentIdentity.branch.id=?1 and a.branchPaymentIdentity.channel.id=?2")
	BranchPaymentChannel findByBranchIdandPaymentChannelId(Integer branchId,Integer paymentChannelId);
	
	@Query(value = "select a from BranchPaymentChannel a where a.branchPaymentIdentity.branch.id=?1 and a.branchPaymentIdentity.channel.id=?2")
	BranchPaymentChannel findByBranchIdandPaymentChannelName(Integer branchId,String paymentChannel);
	
	@Modifying
	@Query(value = "delete from Branch_payment_channels a where a.branch_id = ?1 ", nativeQuery = true)
	void deleteByBranchId(Integer branchId);
}
