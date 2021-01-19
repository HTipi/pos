package com.spring.miniposbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.miniposbackend.model.SuccessResponse;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.service.admin.SeatService;
import com.spring.miniposbackend.util.UserProfileUtil;

@RestController
@RequestMapping("seat")
public class SeatController {

	@Autowired
	private SeatService seatService;
	@Autowired
	private UserProfileUtil userProfile;

	@GetMapping("by-branch")
	public SuccessResponse getAll() {
		return new SuccessResponse("00", "fetch Seat", seatService.showByBranchId(userProfile.getProfile().getBranch().getId()));
	}
	@GetMapping("seat-status")
	public SuccessResponse getSeatStatus() {
		return new SuccessResponse("00", "fetch Seat", seatService.showByBranchId(userProfile.getProfile().getBranch().getId()));
	}
	@PostMapping
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse create(@RequestBody Seat requestItem) {
		return new SuccessResponse("00", "Seat create",
				seatService.create(requestItem, userProfile.getProfile().getUser().getId()));
	}

	@PutMapping("{seatId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse update(@PathVariable Integer seatId, @RequestBody Seat seat) {
		return new SuccessResponse("00", "Seat updated",
				seatService.update(seatId, seat, userProfile.getProfile().getUser().getId()));

	}

	@PatchMapping("delete/{seatId}")
	@PreAuthorize("hasAnyRole('OWNER','BRANCH')")
	public SuccessResponse delete(@PathVariable Integer seatId) {
		return new SuccessResponse("00", "Seat disabled",
				seatService.delete(seatId, userProfile.getProfile().getUser().getId()));

	}

}
