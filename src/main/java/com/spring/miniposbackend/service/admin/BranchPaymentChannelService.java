package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.repository.admin.BranchPaymentChannelRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
@Service
public class BranchPaymentChannelService {

    @Autowired
    private BranchPaymentChannelRepository branchPaymentChannelRepository;
    
    @Autowired
	private UserProfileUtil userProfile;
   
	
	public List<BranchPaymentChannel> showAllChannels(Integer branchId) {
		return branchPaymentChannelRepository.findByBranchId(branchId);
	}
}
