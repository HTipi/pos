package com.spring.miniposbackend.service.sale;

import org.springframework.stereotype.Service;
import com.spring.miniposbackend.model.sale.SaleTemporary;

@Service
public class SaleTemporaryService {

	public void addItem(Integer branchId, Long seatId, Long itemId, Short quantity) {
		//Seat seat, branch
		SaleTemporary sale = new SaleTemporary();
		sale.getSeat();
		
	}
	
	public void removeItem(Long saleTempId) {
		
	}
	
	public void changeQuantity(Long saleTempId, Short quantity) {
		
	}
	
	public void Print(Long seatId) {
		
	}
	
	public void getBySeatId(Long seatId) {
		
	}
	
	public void cancelBySeatId(Long seatId) {
		
	}
	
	public void changeSeatId(Long seatIdFrom, Long seatIdTo) {
		
	}
	
}
