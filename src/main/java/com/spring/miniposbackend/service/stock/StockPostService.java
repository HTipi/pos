package com.spring.miniposbackend.service.stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.StockType;
import com.spring.miniposbackend.model.stock.StockEntry;
import com.spring.miniposbackend.model.stock.StockPost;
import com.spring.miniposbackend.modelview.dashboard.StockDetail;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.stock.StockEntryRepository;
import com.spring.miniposbackend.repository.stock.StockPostRepository;
import com.spring.miniposbackend.repository.stock.StockRepository;
import com.spring.miniposbackend.util.UserProfileUtil;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Transactional
	public List<StockPost> create(Long stockId) {
		return stockRepository.findById(stockId).map((stock) -> {
			StockType stockType = stock.getStockType();
			if (stockType.getCode().compareTo("STOCK-IN") == 0) {
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
			return userRepository.findById(userProfile.getProfile().getUser().getId()).map((user) -> {
				List<StockEntry> stockEntries = stockEntryRepository.findByStockId(stockId);
				List<StockPost> stockPosts = new ArrayList<StockPost>();
				stockEntries.forEach((stockEntry) -> {
					StockPost stockPost = itemBranchRepository.findById(stockEntry.getItemBranch().getId()).map((itemBranch) -> {
						if(!itemBranch.isStock()) {
							throw new ConflictException("Item is not allowed");
						}
						StockPost stockPostTem  = new StockPost();
						stockPostTem.setStockType(stockType);
						stockPostTem.setValueDate(stock.getValueDate());
						stockPostTem.setItemBranch(stockEntry.getItemBranch());
						stockPostTem.setPrice(stockEntry.getPrice());
						stockPostTem.setQuantity(stockEntry.getQuantity());
						stockPostTem.setBranch(stock.getBranch());
						stockPostTem.setUser(user);
						stockPostTem.setStock(stock);
						if(stockType.getCode().compareTo("STOCK-IN") == 0) {
							itemBranch.setStockIn(itemBranch.getStockIn()+stockEntry.getQuantity());
						}else {
							itemBranch.setStockOut(itemBranch.getStockOut()+stockEntry.getQuantity());
						}
						itemBranchRepository.save(itemBranch);
						stockPostTem.setStockBalance(itemBranch.getItemBalance());
						return stockPostRepository.save(stockPostTem);
					}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
					
					stockPosts.add(stockPost);
				});
				stock.setPosted(true);
				stockRepository.save(stock);
				return stockPosts;
			}).orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("Stock does not exist"));
		
	}
	public List<StockDetail> stockDetailByBranchId(Integer branchId, Date startDate, Date endDate) {
		if (userProfile.getProfile().getBranch().getId() != branchId) {
			throw new UnauthorizedException("You are unauthorized");
		}
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		mapSqlParameterSource.addValue("branchId", branchId);
		return jdbc.query("select * from stockdetailbybranchid(:branchId,:startDate,:endDate)",
				mapSqlParameterSource,
				(rs, rowNum) -> new StockDetail(rs.getInt("stockId"), rs.getString("itemName"),
						rs.getString("itemNameKh"), rs.getString("stockDesc"), rs.getDouble("stockPrice"),
						rs.getInt("stockItem"), rs.getDate("value_date"), rs.getString("branchkh"),rs.getLong("stockBalance"),rs.getString("stockcode")));
	}
}
