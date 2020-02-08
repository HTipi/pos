package com.spring.miniposbackend.service.sale;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;

@Service
public class SaleTemporaryService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private SaleTemporaryRepository saleRepository;
	
	public SaleTemporary addItem(Long seatId, Long itemId, Short quantity) {
		
		return seatRepository.findById(seatId)
		.map(seat -> {
			return itemRepository.findById(itemId)
					.map(item -> {
						SaleTemporary sale = new SaleTemporary();
						sale.setSeat(seat);
						sale.setItem(item);
						sale.setValueDate(new Date());
						sale.setQuantity(quantity);
						sale.setPrice(item.getPrice());
						sale.setDiscount(item.getDiscount());
						sale.setPrinted(false);
						return saleRepository.save(sale);
					})
					.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
		})
		.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));
	}
	
//	public void removeItem(Long saleTempId) {
//		
//	}
//	
//	public void changeQuantity(Long saleTempId, Short quantity) {
//		
//	}
//	
//	public void Print(Long seatId) {
//		
//	}
//	
//	public void getBySeatId(Long seatId) {
//		
//	}
//	
//	public void cancelBySeatId(Long seatId) {
//		
//	}
//	
//	public void changeSeatId(Long seatIdFrom, Long seatIdTo) {
//		
//	}
	
}
