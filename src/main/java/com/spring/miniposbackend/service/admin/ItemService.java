package com.spring.miniposbackend.service.admin;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private ItemTypeRepository itemTypeRepository;
		
	@Transactional(readOnly = true)
    public List<Item> shows(){
        return itemRepository.findAll();
    }
	
	@Transactional(readOnly = true)
    public List<Item> showByBranchId(Integer branchId, boolean enable){
		return branchRepository.findById(branchId)
				.map(branch -> {
					return itemRepository.findByBranchId(branchId, enable);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		
    }
	
	@Transactional(readOnly = true)
    public List<Item> showByItemTypeId(Integer itemTypeId, boolean enable){
		return itemTypeRepository.findById(itemTypeId)
				.map(itemType -> {
					return itemRepository.findByItemTypeId(itemTypeId, enable);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
	}
	
	@Transactional(readOnly = true)
    public List<Item> showByBranchItemTypeId(Integer branchId, Integer itemTypeId, boolean enable){
		return branchRepository.findById(branchId)
				.map(branch -> {
					return itemTypeRepository.findById(itemTypeId)
							.map(itemType -> {
								return itemRepository.findByBranchItemTypeId(branchId, itemTypeId, enable);
							})
							.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
				})
				.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
    }
    
    @Transactional(readOnly = true)
    public List<Item> showActiveOnly(){
        return itemRepository.findActive(true);
    }
    
    @Transactional(readOnly = true)
    public Item show(Long itemId){
        return itemRepository.findById(itemId)
        		.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }

    @Transactional
    public Item create(Integer branchId, Integer itemTypeId,Item item) {
        return branchRepository.findById(branchId)
        		.map(branch -> {
        			return itemTypeRepository.findById(itemTypeId)
        					.map(itemType ->{
        						item.setBranch(branch);
        						item.setItemType(itemType);
        						try {
        							return itemRepository.save(item);
        						}catch (Exception e) {
									throw new BadRequestException(e.getMessage());
								}
        						
        					})
        					.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
        		})
        		.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist "));
    }
    
    @Transactional
    public Item update(Long itemId,Integer branchId, Integer itemTypeId,Item requestItem) {
    	return branchRepository.findById(branchId)
        		.map(branch -> {
        			return itemTypeRepository.findById(itemTypeId)
        					.map(itemType ->{
        						return itemRepository.findById(itemId)
        								.map(item -> {
        									item.setBranch(branch);
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
        		})
        		.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist "));
    }
    
    @Transactional
    public Item setPrice(Long itemId, BigDecimal price) {
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
    public Item setDiscount(Long itemId, Short discount) {
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
    public Item setEnable(Long itemId,boolean enable) {
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
