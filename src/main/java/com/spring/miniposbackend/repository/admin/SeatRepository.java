package com.spring.miniposbackend.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.miniposbackend.model.admin.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{

}
