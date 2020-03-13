package com.spring.miniposbackend.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.service.admin.SeatService;

@RestController
@RequestMapping("seat")
public class SeatController {

	@Autowired
	private SeatService seatService;
	
	@GetMapping("by-branch")
	public List<Seat> getAll(@RequestParam Integer branchId){
		return seatService.showByBranchId(branchId);
	}
	
}
