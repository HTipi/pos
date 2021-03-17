package com.spring.miniposbackend.service.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.sale.InvoiceRepository;
import com.spring.miniposbackend.repository.sale.SaleDetailRepository;
import com.spring.miniposbackend.repository.sale.SaleRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SaleTemporaryRepository saleTemporaryRepository;

	@Autowired
	private SaleDetailRepository saleDetailRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private ItemBranchRepository itemRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ReceiptService receiptService;

	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchSettingRepository branchSettingRepository;
	@Autowired
	private BranchCurrencyRepository branchCurrencyRepository;

	public List<Sale> showSaleByUser(@DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date,boolean byUser) {
		if (date.isPresent()) {
			if(byUser)
			return saleRepository.findByIdWithValueDate(userProfile.getProfile().getUser().getId(), date.get());
			else 
				return saleRepository.findByBranchIdWithValueDate(userProfile.getProfile().getBranch().getId(), date.get());	
		}
		return saleRepository.findByUserId(userProfile.getProfile().getUser().getId());

	}

	public List<Sale> showSaleByBranch(Integer branchId) {
		return saleRepository.findByBranchId(branchId);
	}

	@Transactional
	public List<SaleDetail> create(Optional<Long> invoiceId,Optional<Integer> seatId, Integer branchId, Integer userId, Double discount,
			Double cashIn, Double change, Integer currencyId) {
		entityManager.clear();
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
		Seat seat = null;
		Invoice invoice = null;
		String seatName = "";
		List<SaleTemporary> saleTemps;
		Sale sale;
		BranchCurrency branchCurrency = branchCurrencyRepository.findById(currencyId)
				.orElseThrow(() -> new ResourceNotFoundException("Currency does not exist"));

		if(invoiceId.isPresent()) {
			invoice = invoiceRepository.findById(invoiceId.get()).orElseThrow(() -> new ResourceNotFoundException("Invoice does not exist"));
			if (invoice.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Transaction is unauthorized");
			}
			saleTemps = saleTemporaryRepository.findByInvoiceId(invoiceId.get());
			if (saleTemps.size() == 0) {
				throw new ResourceNotFoundException("Record not found");
			}
		}
		else if (seatId.isPresent()) {
			seat = seatRepository.findById(seatId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));

			if (seat.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Transaction is unauthorized");
			}
			seatName = seat.getName();
			saleTemps = saleTemporaryRepository.findBySeatId(seatId.get());
			if (saleTemps.size() == 0) {
				throw new ResourceNotFoundException("Seat not found");
			}
		} else {
			saleTemps = saleTemporaryRepository.findByUserId(userId);
			if (saleTemps.size() == 0) {
				throw new ResourceNotFoundException("Record not found");
			}
		}
		sale = new Sale();
		sale.setBranch(branch);
		sale.setUser(user);
		sale.setSeatName(seatName);
		sale.setSubTotal(BigDecimal.valueOf(0.00));
		sale.setDiscountSaleDetail(BigDecimal.valueOf(0.00));
		sale.setDiscountAmount(BigDecimal.valueOf(discount));
		sale.setReceiptNumber("0");
		sale.setValueDate(new Date());
		sale.setCashIn(cashIn);
		sale.setChange(change);
		sale.setBranchCurrency(branchCurrency);
		Sale saleResult = saleRepository.save(sale);
		saleTemps.forEach((saleTemp) -> {
			SaleDetail saleDetail = addItem(branch, user, saleResult, saleTemp, Optional.empty());
			List<SaleTemporary> subItems = saleTemp.getAddOns() == null ? new ArrayList<SaleTemporary>()
					: saleTemp.getAddOns();
			subItems.forEach((subItem) -> {
				addItem(branch, user, saleResult, subItem, Optional.of(saleDetail));
			});
		});
		if(invoiceId.isPresent()) {
			saleTemporaryRepository.deleteByInvoiceId(invoiceId.get());
			invoiceRepository.deleteById(invoiceId.get());
		}
		else if (seatId.isPresent()) {
			saleTemporaryRepository.deleteBySeatId(seatId.get());
		} else {
			saleTemporaryRepository.deleteByUserId(userId);
		}
		List<SaleDetail> saleDetails = saleDetailRepository.findMainBySaleId(sale.getId());

		double subTotal = 0.00;
		double discountAmount = 0.00;
		for(int i=0;i<saleDetails.size();i++) {
			subTotal += saleDetails.get(i).getSubTotal().doubleValue();
			discountAmount += saleDetails.get(i).getDiscountTotal().doubleValue();
		}
		saleResult.setSubTotal(BigDecimal.valueOf(subTotal));
		saleResult.setDiscountSaleDetail(BigDecimal.valueOf(discountAmount));
		String receiptNum = receiptService.getReceiptNumberByBranchId(branchId).toString();
		saleResult.setReceiptNumber(receiptNum);
		saleRepository.save(saleResult);
		entityManager.flush();
		entityManager.clear();
		return saleDetailRepository.findMainBySaleId(saleResult.getId());
	}

	@Transactional
	public Sale reverseSale(Long saleId) {
		Sale sale = saleRepository.findById(saleId)
				.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));

		if (sale.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
			throw new UnauthorizedException("Reverse is unauthorized");
		}
		sale.setReverse(true);
		sale.setReverseDate(new Date());
		List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(saleId);
		if (saleDetails.size() == 0) {
			throw new ResourceNotFoundException("Record does not exist");
		}
		saleDetails.forEach((sales) -> {
			sales.setReverseDate(new Date());
			sales.setReverse(true);
			saleDetailRepository.save(sales);
			ItemBranch itemBr = itemRepository.findById(sales.getItemBranch().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
			itemBr.setStockOut(itemBr.getStockOut() - sales.getQuantity());
			itemRepository.save(itemBr);
		});
		return saleRepository.save(sale);
	}

	@Transactional(readOnly = true)
	public List<SaleDetail> showSaleTranByUser(Integer userId, Optional<Long> saleId) {
//		List<SaleTransaction> saleTransactions = new ArrayList<>();
//		List<Sale> saleList = new ArrayList<>();
//		if (saleId.isPresent())
//			saleList = saleRepository.findByIdWithUserId(userId, saleId.get());
//		else
//			saleList = saleRepository.findByUserId(userId);
//
//		if (saleList.size() == 0) {
//			throw new ResourceNotFoundException("Record does not exist");
//		}
//
//		saleList.forEach((sale) -> {
//			List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(sale.getId());
//			saleDetails.forEach((saleDetail) -> {
//				SaleTransaction saleTransaction = new SaleTransaction();
//				saleTransaction.setId(sale.getId());
//				saleTransaction.setBranchName(saleDetail.getBranch().getName());
//				saleTransaction.setDiscount(saleDetail.getDiscount());
//				saleTransaction.setDiscountAmount(saleDetail.getDiscountAmount());
//				saleTransaction.setItemName(saleDetail.getItemNameKh());
//				saleTransaction.setPrice(Double.parseDouble(saleDetail.getPrice().toString()));
//				saleTransaction.setQuantity(saleDetail.getQuantity());
//				saleTransaction.setSeatName(sale.getSeatName());
//				saleTransaction.setReceiptNumber(sale.getReceiptNumber());
//				saleTransaction.setTotal(saleDetail.getTotal());
//				saleTransaction.setReverse(sale.isReverse());
//				saleTransaction.setReverseDate(saleDetail.getReverseDate());
//				saleTransaction.setValueDate(sale.getValueDate());
//				saleTransaction.setUserName(sale.getUser().getFullName());
//				saleTransaction.setItemId(saleDetail.getItemBranch().getId());
//				saleTransactions.add(saleTransaction);
//			});
//		});
//
//		return saleTransactions;
		return saleDetailRepository.findMainBySaleId(saleId.get());

	}

	private SaleDetail addItem(Branch branch, User user, Sale sale, SaleTemporary saleTemporary,
			Optional<SaleDetail> parentSaleDetail) {
		ItemBranch itemBranch = saleTemporary.getItemBranch();
		if (itemBranch.isStock()) {
			int itembalance = saleTemporaryRepository.findItemBalanceByUserId(user.getId(), itemBranch.getId())
					.orElse(0);
			if (itemBranch.getItemBalance() < itembalance) {
				String setting = branchSettingRepository
						.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN").orElse("");
				if (!setting.contentEquals(setting))
					throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
			}
			itemBranch.setStockOut(itemBranch.getStockOut() + saleTemporary.getQuantity());
			itemRepository.save(itemBranch);
		}
		SaleDetail saleDeail = new SaleDetail();
		saleDeail.setItemBranch(itemBranch);
		saleDeail.setBranch(branch);
		saleDeail.setUser(user);
		saleDeail.setSale(sale);
		saleDeail.setValueDate(new Date());
		saleDeail.setDiscountPercentage(saleTemporary.getDiscountPercentage());
		saleDeail.setDiscountAmount(saleTemporary.getDiscountAmount());
		saleDeail.setPrice(saleTemporary.getPrice());
		saleDeail.setQuantity(saleTemporary.getQuantity());
		saleDeail.setSubTotal(BigDecimal.valueOf(saleTemporary.getSubTotal()));
		saleDeail.setDiscountTotal(BigDecimal.valueOf(saleTemporary.getDiscountTotal()));
		if(parentSaleDetail.isPresent()) {
			saleDeail.setParentSaleDetail(parentSaleDetail.get());
		}
		return saleDetailRepository.save(saleDeail);
	}
}
