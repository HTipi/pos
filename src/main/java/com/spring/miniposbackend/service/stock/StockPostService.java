package com.spring.miniposbackend.service.stock;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.stock.StockEntry;
import com.spring.miniposbackend.model.stock.StockPost;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.stock.StockEntryRepository;
import com.spring.miniposbackend.repository.stock.StockPostRepository;
import com.spring.miniposbackend.repository.stock.StockRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class StockPostService {

	@Autowired
	private StockPostRepository stockPostRepository;
	@Autowired
	private StockEntryRepository stockEntryRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public List<StockPost> create(Long stockId) {
		return stockRepository.findById(stockId).map((stock) -> {
			if (stock.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			if (stock.isPosted()) {
				throw new ConflictException("Stock transaction is already posted");
			}
			return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
				List<StockEntry> stockEntries = stockEntryRepository.findByStockId(stockId);
				List<StockPost> stockPosts = new ArrayList<StockPost>();
				stockEntries.forEach((stockEntry) -> {					
					StockPost stockPostTem  = new StockPost();
					stockPostTem.setStockIn(stock.isStockIn());
					stockPostTem.setValueDate(stock.getValueDate());
					stockPostTem.setItem(stockEntry.getItem());
					stockPostTem.setPrice(stockEntry.getPrice());
					stockPostTem.setQuantity(stockEntry.getQuantity());
					stockPostTem.setBranch(stock.getBranch());
					stockPostTem.setUser(user);
					stockPostTem.setStock(stock);
					StockPost stockPost = stockPostRepository.save(stockPostTem);
					stockPosts.add(stockPost);
				});
				return stockPosts;
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
		
	}
}
