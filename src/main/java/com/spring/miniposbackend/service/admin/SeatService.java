package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.admin.Setting;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<Seat> showByBranchId(Integer branchId) {
		return seatRepository.findByBranchId(branchId, true);
	}

	public Seat showBySeatId(Integer seatId) {
		return seatRepository.findById(seatId)
				.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist", "01"));
	}

	@Transactional
	public Seat create(Seat requestItem, Integer userId) {
		return userRepository.findById(userId).map((user) -> {
			Seat seat = new Seat();
			seat.setBranch(user.getBranch());
			seat.setEnable(true);
			seat.setName(requestItem.getName());
			seat.setSequence(requestItem.getSequence());
			seat.setFree(true);
			seat.setPrinted(false);
			return seatRepository.save(seat);
		}).orElseThrow(() -> new ResourceNotFoundException("user does not exist", "01"));

	}

	@Transactional
	public Seat update(Integer seatId, Seat set, Integer userId) {
		return seatRepository.findById(seatId).map((seat) -> {
			return userRepository.findById(userId).map((user) -> {
				if (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
					throw new UnauthorizedException("Seat is unauthorized", "02");
				}
				seat.setBranch(user.getBranch());
				seat.setEnable(true);
				seat.setName(set.getName());
				seat.setSequence(set.getSequence());
				return seatRepository.save(seat);
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist", "01"));
		}).orElseThrow(() -> new ResourceNotFoundException("Seat does not exist", "01"));

	}

	@Transactional
	public Seat delete(Integer seatId, Integer userId) {
		return seatRepository.findById(seatId).map((seat) -> {
			return userRepository.findById(userId).map((user) -> {
				if (userProfile.getProfile().getBranch().getId() != user.getBranch().getId()) {
					throw new UnauthorizedException("Seat is unauthorized", "02");
				}
				seat.setEnable(false);
				return seatRepository.save(seat);
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist", "01"));
		}).orElseThrow(() -> new ResourceNotFoundException("Seat does not exist", "01"));

	}
}
