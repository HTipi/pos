package com.spring.miniposbackend.service.admin;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemTypeRepository itemTypeRepository;
		
	@Transactional(readOnly = true)
    public List<Item> shows(){
        return itemRepository.findAll();
    }
	
    @Transactional(readOnly = true)
    public List<Item> shows(boolean enable){
        return itemRepository.findAllWithEnable(enable);
    }
    
    public List<Item> showByCorpoateId(Integer corporateId, boolean enable){
        return itemRepository.findByCorporateId(corporateId, enable);
    }
    
    @Transactional(readOnly = true)
    public Item show(Integer itemId){
        return itemRepository.findById(itemId)
        		.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
	
	@Transactional(readOnly = true)
    public List<Item> showByItemTypeId(Integer itemTypeId, boolean enable){
		return itemTypeRepository.findById(itemTypeId)
				.map(itemType -> {
					return itemRepository.findByItemTypeIdWithEnable(itemTypeId, enable);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
	}

    @Transactional
    public Item create(Integer itemTypeId,Item item) {
        return itemTypeRepository.findById(itemTypeId)
				.map(itemType ->{
					item.setItemType(itemType);
					try {
						return itemRepository.save(item);
					}catch (Exception e) {
						throw new BadRequestException(e.getMessage());
					}
					
				})
				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
    }
    
    @Transactional
    public Item update(Integer itemId, Integer itemTypeId,Item requestItem) {
    	return itemTypeRepository.findById(itemTypeId)
				.map(itemType ->{
					return itemRepository.findById(itemId)
							.map(item -> {
								item.setItemType(itemType);
								item.setCode(requestItem.getCode());
								item.setName(requestItem.getName());
								item.setNameKh(requestItem.getNameKh());
								item.setPrice(requestItem.getPrice());
								item.setDiscount(requestItem.getDiscount());
								item.setEnable(requestItem.isEnable());
								try {
        							return itemRepository.save(item);
        						}catch (Exception e) {
									throw new BadRequestException(e.getMessage());
								}
							})
							.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
				
				})
				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
    }
    
    @Transactional
    public Item setPrice(Integer itemId, BigDecimal price) {
    	return itemRepository.findById(itemId)
    			.map(item -> {
    				item.setPrice(price);
    				try {
						return itemRepository.save(item);
					}catch (Exception e) {
						throw new BadRequestException(e.getMessage());
					}
    			})
    			.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
    
    @Transactional
    public Item setDiscount(Integer itemId, Short discount) {
    	return itemRepository.findById(itemId)
    			.map(item -> {
    				item.setDiscount(discount);
    				try {
						return itemRepository.save(item);
					}catch (Exception e) {
						throw new BadRequestException(e.getMessage());
					}
    			})
    			.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
    
    @Transactional
    public Item setEnable(Integer itemId,boolean enable) {
        return  itemRepository.findById(itemId)
				.map(item -> {
					item.setEnable(enable);
					try {
						return itemRepository.save(item);
					}catch (Exception e) {
						throw new BadRequestException(e.getMessage());
					}
				}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
    
}
