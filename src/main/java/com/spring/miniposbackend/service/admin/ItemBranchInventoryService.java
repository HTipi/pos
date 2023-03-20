package com.spring.miniposbackend.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemBranchInventory;
import com.spring.miniposbackend.modelview.ItemBranchInventoryView;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchinventoryRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ItemBranchInventoryService {

	@Autowired
	private ItemBranchRepository itembranchRepository;
	@Autowired
	private ItemBranchinventoryRepository itembranchinventoryRepository;
	@Autowired
	private UserProfileUtil userProfile;

	@Transactional
	public List<ItemBranchInventory> insert(List<ItemBranchInventoryView> inventoryView, Long itembranchid) {
		List<ItemBranchInventory> list = new ArrayList<>();
		ItemBranch itemBranch = itembranchRepository.findById(itembranchid)
				.orElseThrow(() -> new ResourceAccessException("you're branch doesn't has this item"));
		itembranchinventoryRepository.deleteBybranchId(userProfile.getProfile().getBranch().getId());
		for (int i = 0; i < inventoryView.size(); i++) {
		ItemBranch inventory = itembranchRepository.findById(inventoryView.get(i).getInventoryId())
				.orElseThrow(() -> new ResourceAccessException("you're branch doesn't has this item"));
			
			if(inventory.getItem().getType().equalsIgnoreCase("INVENTORY"))
			{
				ItemBranchInventory inventoryDb = new ItemBranchInventory();
				inventoryDb.setInventoryId(inventory);
				inventoryDb.setItembranch(itemBranch);
				inventoryDb.setQty(inventoryView.get(i).getQty());
				inventoryDb.setBranch(userProfile.getProfile().getBranch());
				list.add(inventoryDb);
				
				itembranchinventoryRepository.save(inventoryDb);
			}
			else {
				throw new ResourceNotFoundException("not inventory");
			}
				
		}
		return list;
	}

}
