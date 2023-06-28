package com.spring.miniposbackend.service.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.BranchPromotion;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemBranchInventory;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.model.sale.Sale;
import com.spring.miniposbackend.model.sale.SaleDetail;
import com.spring.miniposbackend.model.sale.SaleDetailPromotion;
import com.spring.miniposbackend.model.sale.SaleDetailPromotionIdentity;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.model.transaction.Transaction;
import com.spring.miniposbackend.model.transaction.TransactionSale;
import com.spring.miniposbackend.model.transaction.TransactionType;
import com.spring.miniposbackend.modelview.SpitBillItems;
import com.spring.miniposbackend.modelview.sale.SalePaymentRequest;
import com.spring.miniposbackend.modelview.sale.TransactionSalePointView;
import com.spring.miniposbackend.repository.account.AccountRepository;
import com.spring.miniposbackend.repository.admin.BranchCurrencyRepository;
import com.spring.miniposbackend.repository.admin.BranchPromotionRepository;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchinventoryRepository;
import com.spring.miniposbackend.repository.admin.PaymentChannelRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.admin.UserRepository;
import com.spring.miniposbackend.repository.customer.CustomerRepository;
import com.spring.miniposbackend.repository.sale.InvoiceRepository;
import com.spring.miniposbackend.repository.sale.SaleDetailPromotionRepository;
import com.spring.miniposbackend.repository.sale.SaleDetailRepository;
import com.spring.miniposbackend.repository.sale.SaleRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.repository.transaction.TransactionRepository;
import com.spring.miniposbackend.repository.transaction.TransactionSaleRepository;
import com.spring.miniposbackend.repository.transaction.TransactionTypeRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private PaymentChannelRepository paymentChannelRepository;
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private SaleTemporaryRepository saleTemporaryRepository;

	@Autowired
	private SaleDetailRepository saleDetailRepository;

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
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BranchPromotionRepository branchPromotionRepository;
	@Autowired
	private SaleDetailPromotionRepository saleDetailPromotionRepository;
	@Autowired
	private ItemBranchinventoryRepository itemBranchinventoryRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionTypeRepository transactionTypeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private TransactionSaleRepository transactionSaleRepository;

	public List<Sale> showSaleByUser(@DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> date, boolean byUser,
			Optional<Integer> paymentId) {
		if (date.isPresent()) {
			if (byUser) {
				if (paymentId.isPresent()) {
					if (paymentId.get() == 0)
						return saleRepository.findByIdWithValueDateAndPaymentNullId(
								userProfile.getProfile().getUser().getId(), date.get());
					else
						return saleRepository.findByIdWithValueDateAndPaymentId(
								userProfile.getProfile().getUser().getId(), date.get(), paymentId.get());
				} else
					return saleRepository.findByIdWithValueDate(userProfile.getProfile().getUser().getId(), date.get());

			} else {
				if (paymentId.isPresent()) {
					if (paymentId.get() == 0)
						return saleRepository.findByBranchIdWithValueDateAndPaymentNullId(
								userProfile.getProfile().getBranch().getId(), date.get());
					else
						return saleRepository.findByBranchIdWithValueDateAndPaymentId(
								userProfile.getProfile().getBranch().getId(), date.get(), paymentId.get());
				} else
					return saleRepository.findByBranchIdWithValueDate(userProfile.getProfile().getBranch().getId(),
							date.get());
			}
		}
		return saleRepository.findByUserId(userProfile.getProfile().getUser().getId());

	}

	public List<Sale> showSaleRangeByUser(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Optional<Date> date,
			boolean byUser, Optional<Integer> paymentId,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Optional<Date> end) {
		if (date.isPresent()) {
			if (byUser) {
				if (paymentId.isPresent()) {
					if (paymentId.get() == 0)
						return saleRepository.findByIdWithValueDateRangeAndPaymentNullId(
								userProfile.getProfile().getUser().getId(), date.get(), end.get());
					else
						return saleRepository.findByIdWithValueDateRangeAndPaymentId(
								userProfile.getProfile().getUser().getId(), date.get(), paymentId.get(), end.get());
				} else
					return saleRepository.findByIdWithValueDateRange(userProfile.getProfile().getUser().getId(),
							date.get(), end.get());

			} else {
				if (paymentId.isPresent()) {
					if (paymentId.get() == 0)
						return saleRepository.findByBranchIdWithValueDateRangeAndPaymentNullId(
								userProfile.getProfile().getBranch().getId(), date.get(), end.get());
					else
						return saleRepository.findByBranchIdWithValueDateRangeAndPaymentId(
								userProfile.getProfile().getBranch().getId(), date.get(), paymentId.get(), end.get());
				} else
					return saleRepository.findByBranchIdWithValueDateRange(userProfile.getProfile().getBranch().getId(),
							date.get(), end.get());
			}
		}
		return saleRepository.findByUserId(userProfile.getProfile().getUser().getId());

	}

	public List<Sale> showSaleByBranch(Integer branchId) {
		return saleRepository.findByBranchId(branchId);
	}

	@Transactional
	public List create(Optional<Long> invoiceId, Optional<Integer> seatId, Optional<Integer> channelId, Double discount,
			Double cashIn, Double change, Integer currencyId, Integer userId, boolean cancel, Optional<String> remark,
			Optional<Double> serviceCharge, Optional<SpitBillItems> spitBillItems, Optional<Long> customerId,
			Optional<Double> vat, Optional<Long> personId, Optional<Integer> transactionTypeId,
			Optional<Short> discountPercentage) {
		entityManager.clear();
		User user = userProfile.getProfile().getUser();
		Branch branch = userProfile.getProfile().getBranch();
		Seat seat = null;
		Invoice invoice = null;
		String seatName = "";
		List<SaleTemporary> saleTemps;
		Sale sale;
		Account account;
		Transaction transaction = null;
		BranchCurrency branchCurrency = branchCurrencyRepository.findById(currencyId)
				.orElseThrow(() -> new ResourceNotFoundException("Currency does not exist"));

		if (invoiceId.isPresent()) {
			invoice = invoiceRepository.findById(invoiceId.get())
					.orElseThrow(() -> new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16"));
			if (invoice.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Transaction is unauthorized");
			}
			saleTemps = saleTemporaryRepository.findByInvoiceId(invoiceId.get());
			if (saleTemps.size() == 0) {
				throw new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
			} else if (saleTemps.size() > 0 && !saleTemps.get(0).getUserEdit().getId().equals(userId)) {
				saleTemporaryRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saleTemps;
			}
			seatName = saleTemps.get(0).getSeat_name();
		} else if (seatId.isPresent()) {
			seat = seatRepository.findById(seatId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));

			if (seat.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Transaction is unauthorized");
			}
			seatName = seat.getName();
			if (spitBillItems.isPresent()) {
				if (spitBillItems.get().getSaleTempIds().size() > 0) {
					saleTemps = saleTemporaryRepository.findSplitBySeatId(seatId.get(),
							spitBillItems.get().getSaleTempIds());
				} else {
					saleTemps = saleTemporaryRepository.findBySeatId(seatId.get());
				}
			} else {
				saleTemps = saleTemporaryRepository.findBySeatId(seatId.get());
				Seat seatDb = seatRepository.findById(seatId.get())
						.orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
				seatDb.setPrinted(false);
				seatDb.setFree(true);
				seatRepository.save(seatDb);
			}
			if (saleTemps.size() == 0) {
				throw new ConflictException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
			} else if (saleTemps.size() > 0 && !saleTemps.get(0).getUserEdit().getId().equals(userId)) {
				if (saleTemps.get(0).getInvoice_id() != null) {
					throw new ConflictException("ប្រតិបតិ្តការនេះបានបម្រុងដោយអ្នកផ្សេងរួចហើយ", "15");
				}
				saleTemporaryRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saleTemps;
			}
		} else {
			saleTemps = saleTemporaryRepository.findByUserId(user.getId());
			if (saleTemps.size() == 0) {
				throw new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
			}
		}
		SaleTemporary saleTmp = saleTemps.get(0);
		TransactionType transactionType;
		boolean tran = false;
		if (saleTmp.getItemBranch().getType().equalsIgnoreCase("CREDIT")) {
			if (!personId.isPresent() || !transactionTypeId.isPresent()) {
				throw new ConflictException("ប្រតិបត្តិការមិនត្រឹមត្រូវ", "17");
			}
			tran = true;
		}
		sale = new Sale();
		sale.setBranch(branch);
		sale.setUser(user);
		sale.setSeatName(seatName);
		sale.setSubTotal(BigDecimal.valueOf(0.00));
		sale.setDiscountSaleDetail(BigDecimal.valueOf(0.00));
		sale.setDiscountAmount(BigDecimal.valueOf(discount));
		sale.setReceiptNumber("0");
		sale.setValueDate(saleTemps.get(0).getValueDate());
		sale.setCashIn(cashIn);
		sale.setChange(change);
		sale.setReverse(cancel);
		sale.setEndDate(new Date());
		if (discountPercentage.isPresent())
			sale.setDiscountPercentage(discountPercentage.get());
		else
			sale.setDiscountPercentage((short) 0);
		if (serviceCharge.isPresent())
			sale.setServiceCharge(serviceCharge.get());
		else
			sale.setServiceCharge(0.00);
		if (vat.isPresent())
			sale.setVat(vat.get());
		else
			sale.setVat(0.00);
		if (saleTemps.get(0).getBillNumber() == 0) {
			Long receiptNum = receiptService.getBillNumberByBranchId(userProfile.getProfile().getBranch().getId());
			sale.setBillNumber(receiptNum);
		} else
			sale.setBillNumber(saleTemps.get(0).getBillNumber());
		if (remark.isPresent())
			sale.setRemark(remark.get());
		sale.setBranchCurrency(branchCurrency);
		if (channelId.isPresent()) {
			sale.setPaymentChannel(paymentChannelRepository.findById(channelId.get()).orElse(null));
		}
		if (customerId.isPresent()) {
			sale.setCustomer(customerRepository.findById(customerId.get()).orElse(null));
		}
		Sale saleResult = saleRepository.save(sale);
		saleTemps.forEach((saleTemp) -> {
			SaleDetail saleDetail = addItem(branch, user, saleResult, saleTemp, Optional.empty(), cancel);
			List<SaleTemporary> subItems = saleTemp.getAddOns() == null ? new ArrayList<SaleTemporary>()
					: saleTemp.getAddOns();
			subItems.forEach((subItem) -> {
				addItem(branch, user, saleResult, subItem, Optional.of(saleDetail), cancel);
			});
			ItemBranch itemBranch = saleTemp.getItemBranch();
			List<ItemBranchInventory> itemInventories = itemBranchinventoryRepository
					.findByItemBranchId(itemBranch.getId());
			if (itemInventories.size() > 0) {
				itemInventories.forEach((inventory) -> {
					ItemBranch item = itemRepository.findById(inventory.getInvenId())
							.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
					int itembalance = saleTemporaryRepository.findItemBalanceByUserId(user.getId(), item.getId())
							.orElse(0);
					if (item.getItemBalance() < itembalance) {
						String setting = branchSettingRepository
								.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
								.orElse("");
						if (!setting.contentEquals(setting))
							throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
					}
					item.setStockOut((long) (item.getStockOut() + (saleTemp.getQuantity() * inventory.getQty())));
					itemRepository.save(item);
				});
			} else {
				List<Long> inventories = itemBranch.getAddOnInven() == null ? new ArrayList<Long>()
						: itemBranch.getAddOnInven();
				inventories.forEach((inventory) -> {
					ItemBranch item = itemRepository.findById(inventory)
							.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
					int itembalance = saleTemporaryRepository.findItemBalanceByUserId(user.getId(), item.getId())
							.orElse(0);
					if (item.getItemBalance() < itembalance) {
						String setting = branchSettingRepository
								.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
								.orElse("");
						if (!setting.contentEquals(setting))
							throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
					}
					item.setStockOut((long) (item.getStockOut() + (saleTemp.getQuantity() * itemBranch.getInvenQty())));
					itemRepository.save(item);
				});
			}
		});
		if (invoiceId.isPresent()) {
			saleTemporaryRepository.deleteByInvoiceId(invoiceId.get());
			invoiceRepository.deleteById(invoiceId.get());
		} else if (seatId.isPresent()) {
			if (spitBillItems.isPresent()) {
				if (spitBillItems.get().getSaleTempIds().size() > 0) {
					saleTemporaryRepository.deleteByListId(spitBillItems.get().getSaleTempIds());
				} else
					saleTemporaryRepository.deleteBySeatId(seatId.get());
			} else
				saleTemporaryRepository.deleteBySeatId(seatId.get());
		} else {
			saleTemporaryRepository.deleteByUserId(user.getId());
		}
		List<SaleDetail> saleDetails = saleDetailRepository.findMainBySaleId(sale.getId());

		double subTotal = 0.00;
		double discountAmount = 0.00;

		for (int i = 0; i < saleDetails.size(); i++) {
			subTotal += saleDetails.get(i).getSubTotal().doubleValue();
			discountAmount += saleDetails.get(i).getDiscountTotal().doubleValue();
		}
		saleResult.setSubTotal(BigDecimal.valueOf(subTotal));
		saleResult.setDiscountSaleDetail(BigDecimal.valueOf(discountAmount));
		String receiptNum = receiptService.getReceiptNumberByBranchId(branch.getId()).toString();
		saleResult.setReceiptNumber(receiptNum);
		if (tran) {
			double addAmount = 0.00;
			addAmount = (saleDetails.get(0).getSubTotal().doubleValue()
					* saleDetails.get(0).getAddPercent().doubleValue() / 100);
			account = accountRepository.findByCreditAccount(personId.get())
					.orElseThrow(() -> new ResourceNotFoundException("acccount does not exist"));
			transactionType = transactionTypeRepository.findById(transactionTypeId.get())
					.orElseThrow(() -> new ResourceNotFoundException("tranType does not exist"));
			final BigDecimal previousBalance = account.getBalance();
			final BigDecimal tranAmount = BigDecimal.valueOf(subTotal + addAmount);
			transaction = new Transaction();
			account.setBalance(account.getBalance().add(tranAmount));
			transaction.setTransactionAmount(tranAmount);
			transaction.setPreviousBalance(previousBalance);
			transaction.setAccount(account);
			transaction.setTransactionType(transactionType);
			transaction.setBranch(userProfile.getProfile().getBranch());
			transaction.setCurrentBalance(account.getBalance());
			transaction.setRemark(remark.get());
			transaction.setUser(user);
			transaction.setValueDate(new Date());
			accountRepository.save(account);
			transactionRepository.save(transaction);
			ItemBranch itemBranch = saleTemps.get(0).getItemBranch();
			if (itemBranch.getPoint() > 0) {
				final Account accountPoint = accountRepository.findByPointAccount(personId.get())
						.orElseThrow(() -> new ResourceNotFoundException("acccount does not exist"));
				final TransactionType pointType = transactionTypeRepository.findById(4)
						.orElseThrow(() -> new ResourceNotFoundException("Point does not exist"));
				final BigDecimal previousPoint = accountPoint.getBalance();
				final BigDecimal pointAmount = BigDecimal.valueOf(itemBranch.getPoint());
				final Transaction pointTran = new Transaction();
				accountPoint.setBalance(accountPoint.getBalance().add(pointAmount));
				pointTran.setTransactionAmount(pointAmount);
				pointTran.setPreviousBalance(previousPoint);
				pointTran.setAccount(accountPoint);
				pointTran.setTransactionType(pointType);
				pointTran.setBranch(userProfile.getProfile().getBranch());
				pointTran.setCurrentBalance(accountPoint.getBalance());
				pointTran.setRemark(remark.get());
				pointTran.setUser(user);
				pointTran.setValueDate(new Date());
				accountRepository.save(accountPoint);
				transactionRepository.save(pointTran);
			}
		}
		saleRepository.save(saleResult);
		entityManager.flush();
		entityManager.clear();
		if (tran) {
			TransactionSale tranSale = new TransactionSale();
			tranSale.setSale(saleResult);
			tranSale.setTransaction(transaction);
			transactionSaleRepository.save(tranSale);
		}
		return saleDetailRepository.findMainBySaleId(saleResult.getId());
	}

	@Transactional
	public List<?> createByQr(SalePaymentRequest request, UUID qr) {
		Account account = accountRepository.findById(request.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("acccount does not exist"));
		TransactionType transactionType = transactionTypeRepository.findById(request.getTransactionTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("tranType does not exist"));
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("user does not exist"));
		final BigDecimal previousBalance = account.getBalance();
		Transaction transaction = new Transaction();
		transaction.setPreviousBalance(previousBalance);
		transaction.setAccount(account);
		transaction.setTransactionType(transactionType);
		transaction.setBranch(userProfile.getProfile().getBranch());
		transaction.setCurrentBalance(account.getBalance());
		transaction.setRemark(request.getRemark());
		transaction.setUser(user);
		transaction.setValueDate(new Date());
		entityManager.clear();
		Branch branch = userProfile.getProfile().getBranch();
		// Seat seat = null;
		// Invoice invoice = null;

		Sale sale;
		BranchCurrency branchCurrency = branchCurrencyRepository.findById(request.getCurrencyId())
				.orElseThrow(() -> new ResourceNotFoundException("Currency does not exist"));
		List<SaleTemporary> saleTemps = saleTemporaryRepository.findByQrnumber(qr);
		if (saleTemps.size() == 0) {
			throw new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
		}
		SaleTemporary saleTmp = saleTemps.get(0);
		String seatName = saleTmp.getSeat_name();
//		if (saleTmp.getInvoice() != null) {
//			invoice = invoiceRepository.findById(saleTmp.getInvoice_id())
//					.orElseThrow(() -> new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16"));
//		} else if (saleTmp.getSeat() != null) {
//			seat = seatRepository.findById(saleTmp.getSeat_id())
//					.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));
//
//			if (seat.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
//				throw new UnauthorizedException("Transaction is unauthorized");
//			}
//		}
		sale = new Sale();
		sale.setBranch(branch);
		sale.setUser(user);
		sale.setSeatName(seatName);
		sale.setSubTotal(BigDecimal.valueOf(0.00));
		sale.setDiscountSaleDetail(BigDecimal.valueOf(0.00));
		sale.setDiscountAmount(BigDecimal.valueOf(request.getDiscount()));
		sale.setReceiptNumber("0");
		sale.setValueDate(saleTemps.get(0).getValueDate());
		sale.setCashIn(request.getTotal().doubleValue());
		sale.setChange(0.00);
		sale.setReverse(false);
		sale.setEndDate(new Date());
		sale.setServiceCharge(request.getServiceCharge());
		sale.setVat(request.getVat());
		sale.setDiscountPercentage(request.getDiscountPercentage());
		if (saleTemps.get(0).getBillNumber() == 0) {
			Long receiptNum = receiptService.getBillNumberByBranchId(userProfile.getProfile().getBranch().getId());
			sale.setBillNumber(receiptNum);
		} else
			sale.setBillNumber(saleTemps.get(0).getBillNumber());
		sale.setRemark(request.getRemark());
		sale.setBranchCurrency(branchCurrency);
		Sale saleResult = saleRepository.save(sale);
		saleTemps.forEach((saleTemp) -> {
			SaleDetail saleDetail = addItem(branch, user, saleResult, saleTemp, Optional.empty(), false);
			List<SaleTemporary> subItems = saleTemp.getAddOns() == null ? new ArrayList<SaleTemporary>()
					: saleTemp.getAddOns();
			subItems.forEach((subItem) -> {
				addItem(branch, user, saleResult, subItem, Optional.of(saleDetail), false);
			});
			ItemBranch itemBranch = saleTemp.getItemBranch();
			List<ItemBranchInventory> itemInventories = itemBranchinventoryRepository
					.findByItemBranchId(itemBranch.getId());
			if (itemInventories.size() > 0) {
				itemInventories.forEach((inventory) -> {
					ItemBranch item = itemRepository.findById(inventory.getInvenId())
							.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
					int itembalance = saleTemporaryRepository.findItemBalanceByUserId(user.getId(), item.getId())
							.orElse(0);
					if (item.getItemBalance() < itembalance) {
						String setting = branchSettingRepository
								.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
								.orElse("");
						if (!setting.contentEquals(setting))
							throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
					}
					item.setStockOut((long) (item.getStockOut() + (saleTemp.getQuantity() * inventory.getQty())));
					itemRepository.save(item);
				});
			} else {
				List<Long> inventories = itemBranch.getAddOnInven() == null ? new ArrayList<Long>()
						: itemBranch.getAddOnInven();
				inventories.forEach((inventory) -> {
					ItemBranch item = itemRepository.findById(inventory)
							.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
					int itembalance = saleTemporaryRepository.findItemBalanceByUserId(user.getId(), item.getId())
							.orElse(0);
					if (item.getItemBalance() < itembalance) {
						String setting = branchSettingRepository
								.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
								.orElse("");
						if (!setting.contentEquals(setting))
							throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
					}
					item.setStockOut((long) (item.getStockOut() + (saleTemp.getQuantity() * itemBranch.getInvenQty())));
					itemRepository.save(item);
				});
			}
		});
		if (saleTmp.getInvoice() != null) {
			saleTemporaryRepository.deleteByInvoiceId(saleTmp.getInvoice_id());
			invoiceRepository.deleteById(saleTmp.getInvoice_id());
		} else if (saleTmp.getSeat() != null) {
			saleTemporaryRepository.deleteBySeatId(saleTmp.getSeat_id());
		} else {
			saleTemporaryRepository.deleteByUserId(user.getId());
		}
		List<SaleDetail> saleDetails = saleDetailRepository.findMainBySaleId(sale.getId());

		double subTotal = 0.00;
		double discountAmount = 0.00;
		for (int i = 0; i < saleDetails.size(); i++) {
			subTotal += saleDetails.get(i).getSubTotal().doubleValue();
			discountAmount += saleDetails.get(i).getDiscountTotal().doubleValue();
		}
		saleResult.setSubTotal(BigDecimal.valueOf(subTotal));
		saleResult.setDiscountSaleDetail(BigDecimal.valueOf(discountAmount));
		if (account.getAccountType().getId() == 1) {
			if (account.getBalance().compareTo(request.getTotal()) == -1) {
				throw new ConflictException("insufficient balance", "09");
			}
			account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(subTotal - discountAmount)));
			transaction.setTransactionAmount(BigDecimal.valueOf(subTotal - discountAmount));
		} else {
			if (account.getBalance().shortValue() < request.getPoint()) {
				throw new ConflictException("insufficient point", "09");
			}
			account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(request.getPoint())));
			transaction.setTransactionAmount(BigDecimal.valueOf(request.getPoint()));
		}
		String receiptNum = receiptService.getReceiptNumberByBranchId(branch.getId()).toString();
		saleResult.setReceiptNumber(receiptNum);
		saleRepository.save(saleResult);
		accountRepository.save(account);
		transactionRepository.save(transaction);
		entityManager.flush();
		entityManager.clear();
		TransactionSale tranSale = new TransactionSale();
		tranSale.setSale(saleResult);
		tranSale.setTransaction(transaction);
		tranSale.setQrNumber(qr);
		transactionSaleRepository.save(tranSale);
		// packageitemrservice.create(sale.getId(),request.getExpirydate(),
		// saleDetails);
		return saleDetailRepository.findMainBySaleId(saleResult.getId());
	}

	public List<SaleDetail> checkQrSale(UUID qr) {
		TransactionSale tranSale = transactionSaleRepository.findByQr(qr)
				.orElseThrow(() -> new ResourceNotFoundException("Not Pay Yet", "02"));
		return saleDetailRepository.findMainBySaleId(tranSale.getSaleId());
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
			if (itemBr.isStock()) {
				itemBr.setStockOut((long) (itemBr.getStockOut() - sales.getQuantity()));
				itemRepository.save(itemBr);
			}
			List<ItemBranchInventory> itemInventories = itemBranchinventoryRepository
					.findByItemBranchId(itemBr.getId());
			if (itemInventories.size() > 0) {
				itemInventories.forEach((inventory) -> {
					ItemBranch item = itemRepository.findById(inventory.getInvenId())
							.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
					int itembalance = saleTemporaryRepository
							.findItemBalanceByUserId(userProfile.getProfile().getUser().getId(), item.getId())
							.orElse(0);
					if (item.getItemBalance() < itembalance) {
						String setting = branchSettingRepository
								.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
								.orElse("");
						if (!setting.contentEquals(setting))
							throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
					}
					item.setStockOut((long) (item.getStockOut() - (sales.getQuantity() * inventory.getQty())));
					itemRepository.save(item);
				});
			} else {
				List<Long> inventories = itemBr.getAddOnInven() == null ? new ArrayList<Long>()
						: itemBr.getAddOnInven();
				inventories.forEach((inventory) -> {
					ItemBranch item = itemRepository.findById(inventory)
							.orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
					int itembalance = saleTemporaryRepository
							.findItemBalanceByUserId(userProfile.getProfile().getUser().getId(), item.getId())
							.orElse(0);
					if (item.getItemBalance() < itembalance) {
						String setting = branchSettingRepository
								.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
								.orElse("");
						if (!setting.contentEquals(setting))
							throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
					}
					item.setStockOut((long) (item.getStockOut() - (sales.getQuantity() * itemBr.getInvenQty())));
					itemRepository.save(item);
				});
			}
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
			Optional<SaleDetail> parentSaleDetail, boolean cancel) {
		ItemBranch itemBranch = saleTemporary.getItemBranch();
		if (!cancel) {
			if (itemBranch.isStock()) {
				int itembalance = saleTemporaryRepository.findItemBalanceByUserId(user.getId(), itemBranch.getId())
						.orElse(0);
				if (itemBranch.getItemBalance() < itembalance) {
					String setting = branchSettingRepository
							.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
							.orElse("");
					if (!setting.contentEquals(setting))
						throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
				}
				itemBranch.setStockOut((long) (itemBranch.getStockOut() + saleTemporary.getQuantity()));
				itemRepository.save(itemBranch);
			}
		}
		SaleDetail saleDeail = new SaleDetail();
		saleDeail.setItemBranch(itemBranch);
		saleDeail.setBranch(branch);
		saleDeail.setUser(user);
		saleDeail.setSale(sale);
		saleDeail.setReverse(cancel);
		saleDeail.setValueDate(new Date());
		saleDeail.setDiscountPercentage(saleTemporary.getDiscountPercentage());
		saleDeail.setDiscountAmount(saleTemporary.getDiscountAmount());
		saleDeail.setPrice(saleTemporary.getPrice());
		saleDeail.setQuantity(saleTemporary.getQuantity());
		saleDeail.setSubTotal(BigDecimal.valueOf(saleTemporary.getSubTotal()));
		saleDeail.setDiscountTotal(BigDecimal.valueOf(saleTemporary.getDiscountTotal()));
		saleDeail.setCosting(itemBranch.getCosting());
		saleDeail.setAddPercent(saleTemporary.getAddPercent());
		if (parentSaleDetail.isPresent()) {
			saleDeail.setParentSaleDetail(parentSaleDetail.get());
		}
		SaleDetail details = saleDetailRepository.save(saleDeail);
		List<Integer> listPromo = saleTemporary.getAddPromo() == null ? new ArrayList<Integer>()
				: saleTemporary.getAddPromo();
		if (listPromo.size() > 0) {
			List<Integer> list = saleTemporary.getAddPromo();
			SaleDetailPromotion salePromo = null;
			for (int i = 0; i < list.size(); i++) {
				salePromo = new SaleDetailPromotion();
				BranchPromotion branchPro = branchPromotionRepository.findById(list.get(i))
						.orElseThrow(() -> new ResourceNotFoundException("Promotion does not exist"));
				salePromo.setSaleDetailPromotionIdentity(new SaleDetailPromotionIdentity(details, branchPro));
				salePromo.setDiscount(BigDecimal.valueOf(saleTemporary.getDiscountTotal()));
				saleDetailPromotionRepository.save(salePromo);
			}
		}
		return details;
	}

	@Transactional
	public boolean topupPoint(TransactionSalePointView request) {
		entityManager.clear();
		boolean check = transactionSaleRepository.existsBySaleId(request.getSaleId());
		if (check) {
			throw new ConflictException("Point already settled", "01");
		}
		Account pointAccount = accountRepository.findPointById(request.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Point does not exist"));
		Sale sale = saleRepository.findById(request.getSaleId())
				.orElseThrow(() -> new ResourceNotFoundException("Sale does not exist"));
		TransactionType tranType = transactionTypeRepository.findById(request.getTransactionTypeId())
				.orElseThrow(() -> new ResourceNotFoundException("type does not exist"));
		final BigDecimal previousBalance = pointAccount.getBalance();
		Transaction transaction = new Transaction();
		pointAccount.setBalance(pointAccount.getBalance().add(BigDecimal.valueOf(request.getPoint())));
		transaction.setTransactionAmount(BigDecimal.valueOf(request.getPoint()));
		transaction.setPreviousBalance(previousBalance);
		transaction.setAccount(pointAccount);
		transaction.setTransactionType(tranType);
		transaction.setBranch(userProfile.getProfile().getBranch());
		transaction.setCurrentBalance(pointAccount.getBalance());
		transaction.setUser(userProfile.getProfile().getUser());
		transaction.setValueDate(new Date());
		transactionRepository.save(transaction);
		entityManager.flush();
		entityManager.clear();
		TransactionSale tranSale = new TransactionSale();
		tranSale.setSale(sale);
		tranSale.setTransaction(transaction);
		transactionSaleRepository.save(tranSale);
		return true;
	}
}
