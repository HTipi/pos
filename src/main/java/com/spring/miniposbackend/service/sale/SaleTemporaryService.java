package com.spring.miniposbackend.service.sale;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
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
	
	public SaleTemporary addItem(Integer seatId, Integer itemId, Short quantity) {
		
		return seatRepository.findById(seatId)
		.map(seat -> {
			if(!seat.isEnable()) {
				throw new ConflictException("Seat is disable");
			}
			return itemRepository.findById(itemId)
					.map(item -> {
						if(!item.isEnable()) {
							throw new ConflictException("Item is disable");
						}
						SaleTemporary sale = new SaleTemporary();
						sale.setSeat(seat);
						sale.setItem(item);
						sale.setValueDate(new Date());
						sale.setQuantity(quantity);
						sale.setPrice(item.getPrice());
						sale.setDiscount(item.getDiscount());
						sale.setPrinted(false);
						sale.setCancel(false);
						return saleRepository.save(sale);
					})
					.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
		})
		.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));
	}
	
	public SaleTemporary removeItem(Long saleTempId) {
		return saleRepository.findById(saleTempId)
                .map(sale -> {
                	if(sale.isPrinted()) {
                		throw new ConflictException("Record is already printed");
                	}
                	sale.setCancel(true);
                	return saleRepository.save(sale);
                }).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
	}
	
	public SaleTemporary setQuantity(Long saleTempId, Short quantity) {
		return saleRepository.findById(saleTempId)
                .map(sale -> {
                	if(sale.isPrinted()) {
                		throw new ConflictException("Record is already printed");
                	}
                	sale.setQuantity(quantity);
                	return saleRepository.save(sale);
                }).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
	}

//	public List<SaleTemporary> Print(Integer seatId) {
//		return saleRepository.print(seatId);
//	}
	
	
	
	public List<SaleTemporary> showBySeatId(Integer seatId, Optional<Boolean> isPrinted, Optional<Boolean> cancel) {
		if(isPrinted.isPresent()) {
			if(cancel.isPresent()) {
				return saleRepository.findBySeatIdWithIsPrintedCancel(seatId,isPrinted.get(),cancel.get());
			}else {
				return saleRepository.findBySeatIdWithisPrinted(seatId,isPrinted.get());
			}
		}else {
			return saleRepository.findBySeatId(seatId);
		}
	}
//	
//	public void cancelBySeatId(Long seatId) {
//		
//	}
//	
//	public void changeSeatId(Long seatIdFrom, Long seatIdTo) {
//		
//	}
	
}
