package com.spring.miniposbackend.service.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.exception.UnprocessableEntityException;
import com.spring.miniposbackend.model.stock.StockEntry;
import com.spring.miniposbackend.modelview.StockEntryRequest;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.stock.StockEntryRepository;
import com.spring.miniposbackend.repository.stock.StockRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class StockEntryService {

	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockEntryRepository stockEntryRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private UserRepository userRepository;

	public List<StockEntry> showByStockId(Long stockId, Optional<Boolean> posted) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (posted.isPresent()) {
				return stockEntryRepository.findByStockIdWithPosted(stockId, posted.get());
			} else
				return stockEntryRepository.findByStockIdWithPosted(stockId, false);
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}

	@Transactional
	public List<StockEntry> create(Long stockId, List<StockEntryRequest> stockEntries) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.isStockIn()) {
				if (stock.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
			} else {
				if (stock.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
			}
			if (stock.isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			List<StockEntry> entries = new ArrayList<StockEntry>();
			return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
				stockEntries.forEach((stockEntryRequest) -> {
					if (stockEntryRequest.getQuantity() < 1) {
						throw new UnprocessableEntityException("Quantity must be greater than 0");
					}
					StockEntry stockEntry = itemRepository.findById(stockEntryRequest.getItemId()).map((item) -> {
						if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate()
								.getId()) {
							throw new UnauthorizedException("Item is unauthorized");
						}
						StockEntry stockEnt = new StockEntry();
						stockEnt.setStockIn(stock.isStockIn());
						stockEnt.setValueDate(stock.getValueDate());
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
			if (stockEntry.isStockIn()) {
				if (stockEntry.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
			} else {
				if (stockEntry.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
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
			if (stockEntry.isStockIn()) {
				if (stockEntry.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
			} else {
				if (stockEntry.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
			}
			if (stockEntry.getStock().isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			stockEntryRepository.deleteById(stockEntryId);
			return stockEntry;
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}
}
