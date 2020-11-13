package com.spring.miniposbackend.service.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.exception.UnprocessableEntityException;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.StockType;
import com.spring.miniposbackend.model.stock.StockEntry;
import com.spring.miniposbackend.modelview.StockEntryRequest;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
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
	ItemBranchRepository itemBranchRepository;

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
			StockType stockType = stock.getStockType();
			if (stockType.getCode().compareTo("STOCK-IN") == 0) {
				if (stock.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
			} else {
				if (stock.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
				stockEntries.forEach((entries) -> {
					ItemBranch itemBr = itemBranchRepository.findById(entries.getItemId())
							.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
					int stockEntry = 0;
					stockEntry = stockEntryRepository.findByItemBranchId(itemBr.getId(), stockId).orElse(0);
					int stockBalance = entries.getQuantity() + stockEntry;
					if (itemBr.getItemBalance() < stockBalance) {
						throw new ResourceNotFoundException("មិនមានចំនួនស្តុកគ្រប់គ្រាន់", "09");
					}
				});
			}
			if (stock.isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			List<StockEntry> entries = new ArrayList<StockEntry>();
			return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
				stockEntries.forEach((stockEntryRequest) -> {
					if (stockEntryRequest.getQuantity() < 1) {
						throw new ConflictException("Quantity must be greater than 0");
					}
					StockEntry stockEntry = itemBranchRepository.findById(stockEntryRequest.getItemId())
							.map((itemBranch) -> {
								if (itemBranch.getItem().getItemType().getCorporate().getId() != userProfile
										.getProfile().getCorporate().getId()) {
									throw new UnauthorizedException("Item is unauthorized");
								}
								StockEntry stockEnt = new StockEntry();
								stockEnt.setStockType(stockType);
								stockEnt.setValueDate(stock.getValueDate());
								stockEnt.setItemBranch(itemBranch);
								stockEnt.setPrice(stockEntryRequest.getPrice());
								stockEnt.setQuantity(stockEntryRequest.getQuantity());
								stockEnt.setBranch(stock.getBranch());
								stockEnt.setUser(user);
								stockEnt.setDiscount(stockEntryRequest.getDiscount());
								stockEnt.setDiscountAmount(stockEntryRequest.getDiscountAmount());
								stockEnt.setTotal(stockEntryRequest.getTotal());
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
			if (stockEntry.getStockType().getCode().compareTo("STOCK-IN") == 0) {
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
			return itemBranchRepository.findById(stockEntryRequest.getItemId()).map((itemBranch) -> {
				if (itemBranch.getItem().getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate()
						.getId()) {
					throw new UnauthorizedException("Item is unauthorized");
				}
				stockEntry.setItemBranch(itemBranch);
				stockEntry.setPrice(stockEntryRequest.getPrice());
				stockEntry.setQuantity(stockEntryRequest.getQuantity());
				return stockEntryRepository.save(stockEntry);
			}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}

	public StockEntry delete(Long stockEntryId) {
		return stockEntryRepository.findById(stockEntryId).map((stockEntry) -> {
			if (stockEntry.getStockType().getCode().compareTo("STOCK-IN") == 0) {
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
