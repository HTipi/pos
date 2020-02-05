package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Item> showActiveOnly(){
        return itemRepository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    public Item show(Long categoryId){
        return itemRepository.findById(categoryId)
        		.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }

    public Item create(Integer branchId, Integer itemTypeId,Item item) {
        return branchRepository.findById(branchId)
        		.map(branch -> {
        			return itemTypeRepository.findById(itemTypeId)
        					.map(itemType ->{
        						item.setBranch(branch);
        						item.setItemType(itemType);
        						return itemRepository.save(item);
        					})
        					.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
        		})
        		.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist "));
    }
    
//    public Item update(Long itemId,Item requestItem) {
//    	return  itemRepository.findById(itemId)
//			.map(item -> {
//				item.setCode(requestItem.getCode());
//				item.setName(requestItem.getName());
//				item.setNameKh(requestItem.getNameKh());
//				return itemRepository.save(item);
//			}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
//    }
    
    
    public Item updatePrice(Long itemId, Float price) {
    	return itemRepository.findById(itemId)
    			.map(item -> {
    				item.setPrice(price);
    				return itemRepository.save(item);
    			})
    			.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
    public Item updateDiscount(Long itemId, Integer discount) {
    	return itemRepository.findById(itemId)
    			.map(item -> {
    				item.setDiscount(discount);
    				return itemRepository.save(item);
    			})
    			.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
    
    public Item enable(Long itemId) {
        return  itemRepository.findById(itemId)
				.map(item -> {
					item.setEnable(true);
					return itemRepository.save(item);
				}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
    
    public Item disable(Long itemId) {
        return  itemRepository.findById(itemId)
				.map(item -> {
					item.setEnable(false);
					return itemRepository.save(item);
				}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
    }
}
