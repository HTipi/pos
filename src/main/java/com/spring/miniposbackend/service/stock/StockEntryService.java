package com.spring.miniposbackend.service.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.stock.StockEntry;
import com.spring.miniposbackend.modelview.StockEntryRequest;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.stock.StockEntryRepository;
import com.spring.miniposbackend.repository.stock.StockRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

public class StockEntryService {

	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockEntryRepository stockEntryRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	private UserRepository userRepository;

	public List<StockEntry> create(Long stockId, List<StockEntryRequest> stockEntries) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			if (stock.isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			List<StockEntry> entries = new ArrayList<StockEntry>();
			return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
				stockEntries.forEach((stockEntryRequest) -> {
					StockEntry stockEntry = itemRepository.findById(stockEntryRequest.getItemId()).map((item) -> {
						if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate()
								.getId()) {
							throw new UnauthorizedException("Item is unauthorized");
						}
						StockEntry stockEnt = new StockEntry();
						stockEnt.setItem(item);
						stockEnt.setPrice(stockEntryRequest.getPrice());
						stockEnt.setQuantity(stockEntryRequest.getQuantity());
						stockEnt.setBranch(stock.getBranch());
						stockEnt.setUser(user);
						stockEnt.setStock(stock);
						return stockEntryRepository.save(stockEnt);
					}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
					entries.add(stockEntry);
				});
				return entries;
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));

	}

	public StockEntry update(Long stockEntryId, StockEntryRequest stockEntryRequest) {
		return stockEntryRepository.findById(stockEntryId).map((stockEntry) -> {
			if (stockEntry.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			if (stockEntry.getStock().isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			return itemRepository.findById(stockEntryRequest.getItemId()).map((item) -> {
				if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Item is unauthorized");
				}
				stockEntry.setItem(item);
				stockEntry.setPrice(stockEntryRequest.getPrice());
				stockEntry.setQuantity(stockEntryRequest.getQuantity());
				return stockEntryRepository.save(stockEntry);
			}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}

	public StockEntry delete(Long stockEntryId) {
		return stockEntryRepository.findById(stockEntryId).map((stockEntry) -> {
			if (stockEntry.getStock().getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			if (stockEntry.getStock().isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			stockEntryRepository.deleteById(stockEntryId);
			return stockEntry;
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}
}
