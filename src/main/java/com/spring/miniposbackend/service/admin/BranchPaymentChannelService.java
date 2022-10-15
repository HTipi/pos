package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.repository.admin.BranchPaymentChannelRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
@Service
public class BranchPaymentChannelService {

    @Autowired
    private BranchPaymentChannelRepository branchPaymentChannelRepository;
  
	
	public List<BranchPaymentChannel> showAllChannels(Integer branchId) {
		return branchPaymentChannelRepository.findByBranchId(branchId);
	}
}
