package com.spring.miniposbackend.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;

@Service
public class ItemTypeService {

	@Autowired
	private ItemTypeRepository itemTypeRepository;
	
	@Transactional(readOnly = true)
	public List<ItemType> shows(){
		return itemTypeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<ItemType> shows(boolean enable){
		return itemTypeRepository.findAllWithEnable(enable);
	}
	
	@Transactional(readOnly = true)
	public ItemType show(Integer itemTypeId) {
		return itemTypeRepository.findById(itemTypeId)
				.orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}
	
	
	public List<ItemType> showByCorporateId(Integer corporateId, boolean enable){
		return itemTypeRepository.findByCorporateIdWithEnable(corporateId,enable);
	}
	
	public ItemType create(ItemType itemType) {
		return itemTypeRepository.save(itemType);
	}
	
	public ItemType update(Integer itemTypeId,ItemType requestItemType) {
		return itemTypeRepository.findById(itemTypeId)
				.map(itemType -> {
					itemType.setName(requestItemType.getName());
					itemType.setNameKh(requestItemType.getNameKh());
					itemType.setImage(requestItemType.getImage());
					itemType.setEnable(requestItemType.isEnable());
					return itemTypeRepository.save(itemType);
				}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}
	
	public ItemType setEnable(Integer itemTypeId,boolean enable) {
        return  itemTypeRepository.findById(itemTypeId)
				.map(itemType -> {
					itemType.setEnable(enable);
					return itemTypeRepository.save(itemType);
				}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
    }
}
