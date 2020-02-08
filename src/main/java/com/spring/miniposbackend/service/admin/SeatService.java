package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.repository.admin.SeatRepository;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;
	
	public List<Seat> showByBranchId(Integer branchId){
		return seatRepository.findByBranchId(branchId, true);
	}
}
