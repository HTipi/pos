package com.spring.miniposbackend.service.transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.transaction.Transaction;
import com.spring.miniposbackend.model.transaction.TransactionType;
import com.spring.miniposbackend.modelview.transaction.GivePointRequest;
import com.spring.miniposbackend.modelview.transaction.TransactionRequest;
import com.spring.miniposbackend.modelview.transaction.TransactionSaleDetail;
import com.spring.miniposbackend.repository.account.AccountRepository;
import com.spring.miniposbackend.repository.transaction.TransactionRepository;
import com.spring.miniposbackend.repository.transaction.TransactionTypeRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionTypeRepository transactionTypeRepository;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	
	@Transactional
	public Transaction create(TransactionRequest request) {

		Account account = accountRepository.findById(request.getAccountId())
				.orElseThrow(()-> new ResourceNotFoundException("Account does not exit!"));
		
		TransactionType transactionType = transactionTypeRepository.findById(request.getTransactionTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("TrasactionType does not exit"));

		Transaction transaction = new Transaction();
		transaction.setTransactionType(transactionType);
		transaction.setAccount(account);
		transaction.setTransactionAmount(BigDecimal.valueOf(request.getTransactionAmount()));
		transaction.setBranch(userProfile.getProfile().getBranch());
		transaction.setRemark(request.getRemark());
		transaction.setValueDate(new Date());
		transaction.setPreviousBalance(account.getBalance());
		BigDecimal currentBalance = transactionType.isOperater() ? account.getBalance().add(BigDecimal.valueOf(request.getTransactionAmount())) : account.getBalance().subtract(BigDecimal.valueOf(request.getTransactionAmount()));
 		transaction.setCurrentBalance(currentBalance); 
		transaction.setUser(userProfile.getProfile().getUser());
		transaction.setBranch(account.getBranch());
		Transaction result = transactionRepository.save(transaction);
		account.setBalance(currentBalance);
		accountRepository.save(account);
		return result;
	}
	public List<Transaction> getTransactionByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			Long accountId){
	
		return transactionRepository.findAllByAccountAndDate(accountId,startDate,endDate);
	}
	public List<Transaction> getTransactionByDateAndPerson(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,Integer branchId){
	
		return transactionRepository.findAllByPersonAndDate(userProfile.getProfile().getUser().getPerson().getId(),startDate,endDate,branchId);
	}
	
	
	public List<TransactionSaleDetail> getTransactionSummary(
			Date startDate, Date endDate
//			boolean byUser
			) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("startDate", startDate);
		mapSqlParameterSource.addValue("endDate", endDate);
		String queryCondition = "and sale.branch_id = :branchId ";
//		if (byUser) {
//			if (userProfile.getProfile().getUser().getId() != userProfile.getProfile().getUser().getId()) {
//				throw new UnauthorizedException("User is unauthorized");
//			}
			mapSqlParameterSource.addValue("userId", userProfile.getProfile().getUser().getId());
			queryCondition = "and sale.user_id = :userId ";
//		} else {
//			if (userProfile.getProfile().getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
//				throw new UnauthorizedException("Branch is unauthorized");
//			}
//			mapSqlParameterSource.addValue("branchId", userProfile.getProfile().getBranch().getId());
//		}

//		TransactionDetailSummary summary = jdbc.queryForObject(
//				"select count(case when sale.reverse = false then 1 else null end) as paid_invoice,  "
//						+ "min(value_date) as start_date, " + "max(value_date) as end_date, "
//						+ "sum(case when sale.reverse = false then sale.sub_total else 0 end) as sub_total, "
//						+ "sum(case when sale.reverse = false then sale.discount_amount else 0 end) as discount_amount, "
//						+ "sum(case when sale.reverse = false then sale.discount_sale_detail else 0 end) as discount_sale_detail "
//						+ "from sales sale where date_trunc('day',sale.value_date) between :startDate and :endDate "
//						+ queryCondition,
//				mapSqlParameterSource,
//				(rs, rowNum) -> new TransactionDetailSummary(rs.getInt("paid_invoice"),
//						rs.getString("start_date"), rs.getString("end_date"), rs.getDouble("sub_total"),
//						rs.getDouble("discount_amount"), rs.getDouble("discount_sale_detail")));
		
		List<TransactionSaleDetail> details = jdbc
				.query("select ib.id as item_id, " + "i.name as item_name, " + "sale.quantity as quantity, "
						+ "sale.sub_total as sub_total, " + "sale.discount_total as discount_total "
						+ "from sale_details sale " + "inner join item_branches ib on sale.item_branch_id = ib.id "
						+ "inner join items i on ib.item_id=i.id "
						+ "where sale.reverse = false and i.type='MAINITEM' " 
						+ queryCondition
						+ "and date_trunc('day',sale.value_date) between :startDate and :endDate " + "group by ib.id,sub_total,i.name,sale.quantity,sale.discount_total ,sub_total "
						,mapSqlParameterSource,
						(rs, rowNum) -> new TransactionSaleDetail(rs.getString("item_name"),
								rs.getDouble("quantity"), rs.getDouble("sub_total"), rs.getDouble("discount_total")));
//		summary.setDetails(details);
//		return summary;
		return details;
	}
	@Transactional
	 public boolean givePoint(Long personId, GivePointRequest givePointRequest) {

	   Account pointAccount = accountRepository.findByPoint(userProfile.getProfile().getBranch().getId(), personId)
	     .orElseThrow(() -> new ResourceNotFoundException("Point Account not found", "01"));
	   TransactionType tranType = transactionTypeRepository.findById(5)
	     .orElseThrow(() -> new ResourceNotFoundException("type does not exist", "02"));
	   final BigDecimal previousBalance = pointAccount.getBalance();
	   pointAccount.setBalance(pointAccount.getBalance().add(BigDecimal.valueOf(givePointRequest.getPoint())));
	   accountRepository.save(pointAccount);
	   Transaction transaction = new Transaction();
	   transaction.setTransactionAmount(BigDecimal.valueOf(givePointRequest.getPoint()));
	   transaction.setPreviousBalance(previousBalance);
	   transaction.setAccount(pointAccount);
	   transaction.setTransactionType(tranType);
	   transaction.setBranch(userProfile.getProfile().getBranch());
	   transaction.setCurrentBalance(pointAccount.getBalance());
	   transaction.setUser(userProfile.getProfile().getUser());
	   transaction.setValueDate(new Date());
	   transaction.setRemark(givePointRequest.getRemark());
	   transactionRepository.save(transaction);
	   return true;
	  
	}
}
