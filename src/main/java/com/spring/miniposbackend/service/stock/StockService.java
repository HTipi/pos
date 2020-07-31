package com.spring.miniposbackend.service.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.stock.Stock;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.stock.StockRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class StockService {

	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<Stock> showByBranchId(Integer branchId, Optional<Boolean> stockIn, Optional<Boolean> posted) {
		return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
			return branchRepository.findById(branchId).map((branch) -> {
				if (stockIn.isPresent()) {
					boolean isStockIn = stockIn.get();
					if (isStockIn) {
						if (branch.getId() != userProfile.getProfile().getBranch().getId()) {
							throw new UnauthorizedException("Branch is unauthorized");
						}
					}else{
						if (branch.getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
							throw new UnauthorizedException("Corporate is unauthorized");
						}
					}
					if (posted.isPresent()) {
						boolean isPosted = posted.get();
						return stockRepository.findByBranchId(branchId, isStockIn, isPosted);
					} else {
						return stockRepository.findByBranchId(branchId, stockIn.get().booleanValue());
					}
				} else {
					if (branch.getId() != userProfile.getProfile().getBranch().getId()
							&& stockIn.get().booleanValue()) {
						throw new UnauthorizedException("Branch is unauthorized");
					}
					return stockRepository.findByBranchId(branchId);
				}

			}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public Stock create(Integer branchId, Stock requestStock) {
		return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
			return branchRepository.findById(branchId).map((branch) -> {
				if (requestStock.isStockIn()) {
					if (branch.getId() != userProfile.getProfile().getBranch().getId()) {
						throw new UnauthorizedException("Branch is unauthorized");
					}
				}else{
					if (branch.getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
						throw new UnauthorizedException("Corporate is unauthorized");
					}
				}
				Stock stock = new Stock();
				stock.setValueDate(requestStock.getValueDate());
				stock.setDescription(requestStock.getDescription());
				stock.setPosted(false);
				stock.setStockIn(requestStock.isStockIn());
				stock.setBranch(branch);
				stock.setUser(user);
				return stockRepository.save(stock);
			}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}

	public Stock updateDescription(Long stockId, String description) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.isStockIn()) {
				if (stock.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
			}else{
				if (stock.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
			}
			if (stock.isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			stock.setDescription(description);
			return stockRepository.save(stock);
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}

	public Stock delete(Long stockId) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.isStockIn()) {
				if (stock.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
			}else{
				if (stock.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
			}
			if (stock.isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			stockRepository.deleteById(stockId);
			return stock;
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}
}
