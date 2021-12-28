package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Printer;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.admin.Setting;
import com.spring.miniposbackend.repository.admin.PrinterRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class PrinterService {

	@Autowired
	private PrinterRepository printerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<Printer> showByBranchId(int branchId) {
		return printerRepository.findByBranchId(branchId);
	}

}
