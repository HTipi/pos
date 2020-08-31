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
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.repository.admin.StockTypeRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.stock.StockRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class StockService {

	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private CorporateRepository corporateRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private StockTypeRepository stockTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<Stock> showStockInByBranchId(Integer branchId, Optional<Boolean> posted) {
		return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
			return branchRepository.findById(branchId).map((branch) -> {
				if (branch.getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is unauthorized");
				}
				return stockRepository.findByBranchId(branchId, "STOCK-IN", posted.get());

			}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public List<Stock> showStockOutByCorporateId(Integer corporateId, Optional<Boolean> posted) {
		return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
			return corporateRepository.findById(corporateId).map((corporate) -> {
				if (corporate.getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
				return stockRepository.findByCorporateId(corporateId, "STOCK-OUT", posted.get());

			}).orElseThrow(() -> new ResourceNotFoundException("Corporate does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public List<Stock> showStockDisposeByCorporateId(Integer corporateId, Optional<Boolean> posted) {
		return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
			return corporateRepository.findById(corporateId).map((corporate) -> {
				if (corporate.getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
				return stockRepository.findByCorporateId(corporateId, "STOCK-DISPOSE", posted.get());

			}).orElseThrow(() -> new ResourceNotFoundException("Corporate does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

	}

	public Stock create(Integer branchId, String stockTypeCode, Stock requestStock) {
		return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
			return branchRepository.findById(branchId).map((branch) -> {
				if (stockTypeCode.compareTo("STOCK-IN") == 0) {
					if (branch.getId() != userProfile.getProfile().getBranch().getId()) {
						throw new UnauthorizedException("Branch is unauthorized");
					}
				} else {
					if (branch.getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
						throw new UnauthorizedException("Corporate is unauthorized");
					}
				}
				return stockTypeRepository.findById(stockTypeCode).map((stockType) -> {
					Stock stock = new Stock();
					stock.setValueDate(requestStock.getValueDate());
					stock.setDescription(requestStock.getDescription());
					stock.setPosted(false);
					stock.setStockType(stockType);
					stock.setBranch(branch);
					stock.setUser(user);
					return stockRepository.save(stock);
				}).orElseThrow(() -> new ResourceNotFoundException("Stock Type does not exist"));

			}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
	}

	public Stock updateDescription(Long stockId, String description) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.getStockType().getCode().compareTo("STOCK-IN") == 0) {
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
			stock.setDescription(description);
			return stockRepository.save(stock);
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}

	public Stock delete(Long stockId) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.getStockType().getCode().compareTo("STOCK-IN") == 0) {
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
			stockRepository.deleteById(stockId);
			return stock;
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
	}
}
