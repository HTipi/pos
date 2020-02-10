package com.spring.miniposbackend.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.miniposbackend.model.admin.Seat;

public interface SeatRepository extends JpaRepository<Seat, Integer>{

	@Query(value = "select s from Seat s where s.enable=?1")
	List<Seat> findAll(boolean enable);
	
	List<Seat> findByBranchId(Integer branchId);
	@Query(value = "select s from Seat s where s.branch.id = ?1 and s.enable = ?2")
	List<Seat> findByBranchId(Integer branchId, boolean enable);
}
