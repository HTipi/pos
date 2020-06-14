package com.spring.miniposbackend.service.stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.exception.UnprocessableEntityException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.stock.StockOutTemporary;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.stock.StockOutTemporaryRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class StockOutTemporaryService {

	@Autowired
	private StockOutTemporaryRepository stockOutRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchRepository branchRepository;

	@Transactional
	public List<StockOutTemporary> addItem(List<Map<String, Integer>> requestItems, Integer branchId) {
		List<StockOutTemporary> list = new ArrayList<StockOutTemporary>();
		for (int i = 0; i < requestItems.size(); i++) {
			for (int j = i + 1; j < requestItems.size(); j++) {
				if (requestItems.get(i).get("itemId").equals(requestItems.get(j).get("itemId"))) {
					throw new UnprocessableEntityException("item id are duplicated");
				}
			}
		}
		// update by user edit

		// insert into stockout
		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));

		User user = userProfile.getProfile().getUser();

		Integer corporateId = userProfile.getProfile().getCorporate().getId();

		if (branch.getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Branch is unauthorized");
		}
		requestItems.forEach((requestItem) -> {
			Long itemId = requestItem.get("itemId").longValue();
			Short quantity = requestItem.get("quantity").shortValue();
			if (quantity < 1) {
				throw new UnprocessableEntityException("Quantity must be greater than 0");
			}

			StockOutTemporary stockOut = itemRepository.findById(itemId).map(item -> {
				if (!item.isEnable()) {
					throw new ConflictException("Item is disable");
				}
				if (item.getItemType().getCorporate().getId() != corporateId) {
					throw new UnauthorizedException("Item is unauthorized");
				}
				StockOutTemporary stockOutTemporary = new StockOutTemporary();
				stockOutTemporary.setValueDate(new Date());
				stockOutTemporary.setItem(item);
				stockOutTemporary.setQuantity(quantity);
				stockOutTemporary.setPrice(item.getPrice());
				stockOutTemporary.setDiscount(item.getDiscount());
				stockOutTemporary.setBranch(branch);
				stockOutTemporary.setPrinted(false);
				stockOutTemporary.setCancel(false);
				stockOutTemporary.setUser(user);
				return stockOutRepository.save(stockOutTemporary);
			}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
			list.add(stockOut);
		});
		return list;
	}

	@Transactional
	public StockOutTemporary setQuantity(Long stockOutId, Short quantity) {

		Integer corporateId = userProfile.getProfile().getCorporate().getId();

		if (quantity < 1) {
			throw new ConflictException("Qty must be greater than zero");
		}

		return stockOutRepository.findById(stockOutId).map(stockOut -> {
			if (stockOut.isPrinted()) {
				if (stockOut.getQuantity() > quantity)
					throw new ConflictException("Record is already printed");
			}
			if (stockOut.getBranch().getCorporate().getId() != corporateId) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			stockOut.setQuantity(quantity);
			stockOut.setValueDate(new Date());
			stockOut.setUser(userProfile.getProfile().getUser());
			return stockOutRepository.save(stockOut);
		}).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
	}

	public List<StockOutTemporary> showByBranchId(Integer branchId, Optional<Boolean> isPrinted,
			Optional<Boolean> cancel) {

		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		if (branch.getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
			throw new UnauthorizedException("Branch is unauthorized");
		}

		if (isPrinted.isPresent()) {
			if (cancel.isPresent()) {
				return stockOutRepository.findByBranchIdWithIsPrintedCancel(branchId, isPrinted.get(), cancel.get());
			} else {
				return stockOutRepository.findByBranchIdWithIsPrinted(branchId, isPrinted.get());
			}
		} else {
			return stockOutRepository.findByBranchId(branchId);
		}
	}

	public StockOutTemporary removeItem(Long stockOutId) {
		Integer corporateId = userProfile.getProfile().getCorporate().getId();

		StockOutTemporary stockOut = stockOutRepository.findById(stockOutId)
				.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
		if (stockOut.isPrinted()) {
			throw new ConflictException("Record is already printed");
		}
		if (stockOut.getBranch().getCorporate().getId() != corporateId) {
			throw new UnauthorizedException("Branch is unauthorized");
		}
		stockOutRepository.delete(stockOut);
		return stockOut;
	}
}
