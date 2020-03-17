package com.spring.miniposbackend.service.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spring.miniposbackend.repository.admin.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnprocessableEntityException;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;

@Service
public class SaleTemporaryService {

    @Autowired
    private ItemBranchRepository itemBranchRepository;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SaleTemporaryRepository saleRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<SaleTemporary> addItem(List<Map<String, Integer>> requestItems) {
        List<SaleTemporary> list = new ArrayList<SaleTemporary>();
        for (int i = 0; i < requestItems.size(); i++) {
            for (int j = i + 1; j < requestItems.size(); j++) {
                if (requestItems.get(i).get("itemId").equals(requestItems.get(j).get("itemId"))) {
                    throw new UnprocessableEntityException("itemId is duplicated");
                }
            }
        }
        requestItems.forEach(
                (requestItem) -> {
                    Integer seatId = requestItem.get("seatId");
                    Long itemId = requestItem.get("itemId").longValue();
                    Short quantity = requestItem.get("quantity").shortValue();
                    Integer userId = requestItem.get("userId");
                    if (quantity < 1) {
                        throw new UnprocessableEntityException("Quantity must be greater than 0");
                    }
                    SaleTemporary saleTemp = seatRepository.findById(seatId)
                            .map(seat -> {
                                if (!seat.isEnable()) {
                                    throw new ConflictException("Seat is disable");
                                }
                                return userRepository.findById(userId).map(user -> itemBranchRepository.findById(itemId)
                                        .map(item -> {
                                            if (!item.isEnable()) {
                                                throw new ConflictException("Item is disable");
                                            }
                                            SaleTemporary sale = new SaleTemporary();
                                            sale.setSeat(seat);
                                            sale.setItemBranch(item);
                                            sale.setValueDate(new Date());
                                            sale.setQuantity(quantity);
                                            sale.setPrice(item.getPrice());
                                            sale.setDiscount(item.getDiscount());
                                            sale.setPrinted(false);
                                            sale.setCancel(false);
                                            sale.setUser(user);
                                            return saleRepository.save(sale);
                                        })
                                        .orElseThrow(() -> new ResourceNotFoundException("Record does not exist"))).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));


                            })
                            .orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));
                    list.add(saleTemp);
                }
        );
        return list;
    }

    public SaleTemporary removeItem(Long saleTempId) {
        return saleRepository.findById(saleTempId)
                .map(sale -> {
                    if (sale.isPrinted()) {
                        throw new ConflictException("Record is already printed");
                    }
                    sale.setCancel(true);
                    return saleRepository.save(sale);
                }).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
    }

    public List<SaleTemporary> removeItemBySeat(Integer seatId) {
        return saleRepository.deleteBySeatId(seatId);
    }

    public SaleTemporary setQuantity(Long saleTempId, Short quantity) {
        return saleRepository.findById(saleTempId)
                .map(sale -> {
                    if (sale.isPrinted()) {
                        if (sale.getQuantity() > quantity)
                        throw new ConflictException("Record is already printed");
                    }
                    sale.setQuantity(quantity);
                    return saleRepository.save(sale);
                }).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
    }
    @Transactional
    public boolean printBySeat(Integer seatId) {
        List<SaleTemporary> list = saleRepository.findBySeatId(seatId);
        list.forEach(saleTemporary -> {
            saleTemporary.setPrinted(true);
            saleRepository.save(saleTemporary);
        });
        return true;
    }


    public List<SaleTemporary> showBySeatId(Integer seatId, Optional<Boolean> isPrinted, Optional<Boolean> cancel) {
        if (isPrinted.isPresent()) {
            if (cancel.isPresent()) {
                return saleRepository.findBySeatIdWithIsPrintedCancel(seatId, isPrinted.get(), cancel.get());
            } else {
                return saleRepository.findBySeatIdWithisPrinted(seatId, isPrinted.get());
            }
        } else {
            return saleRepository.findBySeatId(seatId);
        }
    }

    public List<SaleTemporary> showByUserId(Integer userId, Optional<Boolean> isPrinted, Optional<Boolean> cancel) {

        return saleRepository.findByUserId(userId);

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
