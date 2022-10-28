package com.spring.miniposbackend.service.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.exception.UnprocessableEntityException;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.PaymentChannel;
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.modelview.SaleRequest;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.PaymentChannelRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.sale.InvoiceRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleTemporaryService {

	@Autowired
	private ItemBranchRepository itemBranchRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private SaleTemporaryRepository saleRepository;
	@Autowired
	private ReceiptService receiptService;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchSettingRepository branchSettingRepository;
	@Autowired
	private PaymentChannelRepository paymentChannelRepository;

	
	@Transactional
	public List<SaleTemporary> changeSeat(Integer seatId,Integer newSeatId){
		Optional<Seat> seat = seatRepository.findById(newSeatId);
		if(!seat.isPresent())
		{
			throw new ResourceNotFoundException("Seat does not exist");
		}
		List<SaleTemporary> oldList = saleRepository.findBySeatId(seatId);
		if(oldList.size() == 0) {
			throw new ResourceNotFoundException("Item not found in the old seat");
		}
		List<SaleTemporary> newList = saleRepository.findBySeatId(newSeatId);
		if(newList.size() == 0)
		{
			for (int i = 0; i < oldList.size(); i++) {
				oldList.get(i).setSeat(seat.get());
				saleRepository.save(oldList.get(i));
			}
		}
		else {
			for (int i = 0; i < oldList.size(); i++) {	
				boolean check = false;
				SaleTemporary oldSale = oldList.get(i);
				for (int j = 0; j < newList.size(); j++) {
					SaleTemporary newSale = newList.get(j);
					if(newSale.getItemId() == oldSale.getItemId()) {
						if(oldSale.getPrice().compareTo(newSale.getPrice()) == 0 && oldSale.getDiscountAmount().compareTo(newSale.getDiscountAmount()) == 0 && oldSale.getDiscountPercentage() == newSale.getDiscountPercentage())
						{
							newSale.setQuantity((short) (newSale.getQuantity() + oldSale.getQuantity()));
							saleRepository.save(newSale);
							System.out.println(1);
							check = true;
							break;
						}
						else
						{
							oldSale.setSeat(newSale.getSeat());
							saleRepository.save(oldSale);
							check = true;
							System.out.println(2);
							break;
						}
						
					}
				}
				if(check == false)
				{
					oldSale.setSeat(seat.get());
					saleRepository.save(oldSale);
					System.out.println(3);
				}
			}
		}
		saleRepository.deleteBySeatId(seatId);
		entityManager.flush();
		entityManager.clear();
		return saleRepository.findBySeatId(newSeatId);
		
	}
	@Transactional
	public Object moveToPending(List<SaleRequest> requestItems, Optional<Integer> seatId, String remark,
			Integer userId,Optional<Integer> channelId) {
		entityManager.clear();
		Optional<Seat> seat = Optional.empty();
		Optional<PaymentChannel> channel = Optional.empty();
		User user = userProfile.getProfile().getUser();
		if(channelId.isPresent())
		{
			channel = paymentChannelRepository.findById(channelId.get());
		}
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		if (seatId.isPresent()) {
			seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}
			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saletmps;
			}
		}
		Invoice invoice = new Invoice();
		invoice.setRemark(remark);
		invoice.setUser(userProfile.getProfile().getUser());
		invoice.setBranch(userProfile.getProfile().getUser().getBranch());
		invoice = invoiceRepository.save(invoice);
		Optional<Seat> finSeat = seat;
		Optional<Invoice> finInvoice = Optional.of(invoice);
		Optional<PaymentChannel> finChannel = channel;
		requestItems.forEach((requestItem) -> {
			SaleTemporary saleTemporary = addItem(requestItem, user, finSeat, finInvoice, Optional.empty(),finChannel);
			List<SaleRequest> subItems = requestItem.getAddOns() == null ? new ArrayList<SaleRequest>()
					: requestItem.getAddOns();
			subItems.forEach((subItem) -> {
				SaleTemporary addOnItem = addItem(subItem, user, finSeat, finInvoice, Optional.of(saleTemporary),finChannel);
				saleTemporary.setPrice(saleTemporary.getPrice().add(addOnItem.getPrice()));
			});
			if (requestItem.getAddOns() != null) {
				saleRepository.save(saleTemporary);
			}
		});
		entityManager.flush();
		entityManager.clear();
		invoice.setSaleTemporaries(saleRepository.findByInvoiceId(invoice.getId()));
		return invoice;
	}

	@Transactional
	public List<SaleTemporary> addItems(List<SaleRequest> requestItems, Optional<Integer> seatId,
			Optional<Long> invoiceId, Integer userId,Optional<Integer> channelId) {
		entityManager.clear();
		Optional<Seat> seat = Optional.empty();
		Optional<Invoice> invoice = Optional.empty();
		Optional<PaymentChannel> channel = Optional.empty();
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		User user = userProfile.getProfile().getUser();
		if(channelId.isPresent())
		{
			channel = paymentChannelRepository.findById(channelId.get());
		}
		if (invoiceId.isPresent()) {
			invoice = invoiceRepository.findById(invoiceId.get());
			if (!invoice.isPresent()) {
				throw new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
			}
			saletmps = saleRepository.findByInvoiceId(invoiceId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saletmps;
			}
		} else if (seatId.isPresent()) {
			seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}
			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saletmps;
			}
		}
		Optional<Seat> finSeat = seat;
		Optional<Invoice> finInvoice = invoice;
		Optional<PaymentChannel> finChannel = channel;
		requestItems.forEach((requestItem) -> {
			SaleTemporary saleTemporary = addItem(requestItem, user, finSeat, finInvoice, Optional.empty(),finChannel);
			List<SaleRequest> subItems = requestItem.getAddOns() == null ? new ArrayList<SaleRequest>()
					: requestItem.getAddOns();
			subItems.forEach((subItem) -> {
				SaleTemporary addOnItem = addItem(subItem, user, finSeat, finInvoice, Optional.of(saleTemporary),finChannel);
				saleTemporary.setPrice(saleTemporary.getPrice().add(addOnItem.getPrice()));
			});
			if (requestItem.getAddOns() != null) {
				saleRepository.save(saleTemporary);
			}
		});
		entityManager.flush();
		entityManager.clear();
		if (invoiceId.isPresent()) {
			return saleRepository.findByInvoiceId(invoiceId.get());
		} else if (seatId.isPresent()) {
			return saleRepository.findBySeatId(seatId.get());
		} else {
			return saleRepository.findByUserId(user.getId());
		}
	}

	@Transactional
	public List<SaleTemporary> removeItem(Long saleTempId, Optional<Integer> seatId, Optional<Long> invoiceId,
			Integer userId) {
		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		User user = userProfile.getProfile().getUser();
		if (invoiceId.isPresent()) {
			Optional<Invoice> invoice = invoiceRepository.findById(invoiceId.get());
			if (!invoice.isPresent()) {
				throw new ResourceNotFoundException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
			}
			saletmps = saleRepository.findByInvoiceId(invoiceId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saletmps;
			}
			saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
		} else if (seatId.isPresent()) {
			Optional<Seat> seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}

			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size() == 0) {
				throw new ConflictException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
			}
			if (!saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saletmps;
			}
			saleRepository.updateUserEditSeat(user.getId(), seatId.get());
		}
		SaleTemporary saletemp = saleRepository.findById(saleTempId).map(sale -> {
//			if (sale.isPrinted()) {
//				throw new ConflictException("Record is already printed");
//			}
			ItemBranch itemBranch = sale.getItemBranch();
			if ((itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId())
					|| !itemBranch.isEnable()) {
				throw new UnauthorizedException("Item is unauthorized");
			}
			if (sale.getParentSaleTemporary() != null) {
				saleRepository.deductPriceBySaleTempId(sale.getParentSaleTemporary().getId(), sale.getPrice());
			}
			saleRepository.deleteBySaleTempId(saleTempId);
			if (itemBranch.getItem().getType().equalsIgnoreCase("SUBITEM")) {
				SaleTemporary parentSale = saleRepository.findById(sale.getParentSaleTemporary().getId())
						.orElseThrow(() -> new ResourceNotFoundException("item not found", "17"));
				parentSale.setPrice(parentSale.getPrice().subtract(itemBranch.getPrice()));
				saleRepository.save(parentSale);
			}
			return sale;
		}).orElseThrow(
				() -> new ResourceNotFoundException("ប្រតិបត្តិការនេះត្រូវបានលុបដោយអ្នកប្រើប្រាស់ម្នាក់ទៀត", "17"));
		list.add(saletemp);

		return list;

	}

	@Transactional
	public List<SaleTemporary> setQuantity(Long saleTempId, Short quantity, Optional<Integer> seatId,
			Optional<Long> invoiceId, Integer userId) {
		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		User user = userProfile.getProfile().getUser();
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		if (invoiceId.isPresent()) {
			Optional<Invoice> invoice = invoiceRepository.findById(invoiceId.get());
			if (!invoice.isPresent()) {
				throw new ResourceNotFoundException("Invoice does not exit");
			}
			saletmps = saleRepository.findByInvoiceId(invoiceId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saletmps;
			}
		} else if (seatId.isPresent()) {
			Optional<Seat> seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}
			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saletmps;
			}
		}
		SaleTemporary saletemp = saleRepository.findById(saleTempId).map(sale -> {
			if (sale.isPrinted()) {
				if (sale.getQuantity() > quantity)
					throw new ConflictException("Record is already printed");
			}
			if (sale.getQuantity() < 1) {
				throw new ConflictException("Qty must be greater than one");
			}
			if (sale.getItemBranch().isStock()) {
				// int itembalance = saleRepository.findItemBalanceByUserId(userId,
				// sale.getItemBranch().getId())
				// .orElse(0);
				if (sale.getItemBranch().getItemBalance() < quantity) {
					String setting = branchSettingRepository
							.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
							.orElse("");
					if (setting != "true")
						throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
				}
			}
			ItemBranch itemBranch = sale.getItemBranch();
			if ((itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId())
					|| !itemBranch.isEnable()) {
				throw new UnauthorizedException("Item is unauthorized");
			}

			sale.setQuantity(quantity);
			sale.setValueDate(new Date());
			sale.setUser(user);
			sale.setUserEdit(user);
			return saleRepository.save(sale);
		}).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
		list.add(saletemp);
		return list;
	}

	@Transactional
	public List<SaleTemporary> printBySeat(Integer seatId,Optional<Long> invoiceId) {
		try {
			List<SaleTemporary> list = invoiceId.isPresent() ?  saleRepository.findBySeatForPrintIdWithInvoice(invoiceId.get()) : saleRepository.findBySeatForPrintId(seatId);
			Long receiptNum = receiptService.getBillNumberByBranchId(userProfile.getProfile().getBranch().getId());
			list.forEach(saleTemporary -> {
				saleTemporary.setPrinted(true);
				saleTemporary.setBillNumber(receiptNum);
				saleRepository.save(saleTemporary);
			});
			return list;
		} catch (Exception ex) {
			throw new ConflictException("print SaleTmp Failed", "04");
		}
	}
	
	public List<SaleTemporary> printByUser(Optional<Long> invoiceId) {
		try {
			List<SaleTemporary> list = invoiceId.isPresent()  ? saleRepository.findByUserIdForPrintWithInvoice(invoiceId.get()) : saleRepository.findByUserIdForPrint(userProfile.getProfile().getUser().getId());
			Long receiptNum = receiptService.getBillNumberByBranchId(userProfile.getProfile().getBranch().getId());
			list.forEach(saleTemporary -> {
				saleTemporary.setPrinted(true);
				saleTemporary.setBillNumber(receiptNum);
				saleRepository.save(saleTemporary);
			});
			return list;
		} catch (Exception ex) {
			throw new ConflictException("print SaleTmp Failed", "04");
		}
	}

	public List<SaleTemporary> showByInvoiceId(Long invoiceId, Optional<Boolean> isPrinted, Optional<Boolean> cancel) {
		Invoice invoice = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new ResourceNotFoundException("Invoice does not exist"));
		if (invoice.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
			throw new UnauthorizedException("Transaction is unauthorized");
		}
		if (isPrinted.isPresent()) {
			if (cancel.isPresent()) {
				return saleRepository.findByInvoiceIdWithIsPrintedCancel(invoiceId, isPrinted.get(), cancel.get());
			} else {
				return saleRepository.findByInvoiceIdWithisPrinted(invoiceId, isPrinted.get());
			}
		} else {
			return saleRepository.findByInvoiceId(invoiceId);
		}
	}

	public List<SaleTemporary> showBySeatId(Integer seatId, Optional<Boolean> isPrinted, Optional<Boolean> cancel) {
		Seat seat = seatRepository.findById(seatId)
				.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));
		if (seat.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
			throw new UnauthorizedException("Transaction is unauthorized");
		}
		if (isPrinted.isPresent()) {
			if (cancel.isPresent()) {
				return saleRepository.findBySeatIdWithIsPrintedCancel(seatId, isPrinted.get(), cancel.get());
			} else {
				return saleRepository.findBySeatIdWithisPrinted(seatId, isPrinted.get());
			}
		} else {
			return saleRepository.findBySeatId(seatId);
		}
	}

	public List<Integer> showStatusSeatByBranchId(Integer branchId) {

		return saleRepository.findStatusSeatByBranchId(branchId);

	}

	public List<SaleTemporary> showByUserId(Integer userId, Optional<Boolean> isPrinted, Optional<Boolean> cancel) {
		List<SaleTemporary> saleTmps = new ArrayList<SaleTemporary>();
		if (isPrinted.isPresent()) {
			if (cancel.isPresent()) {
				saleTmps = saleRepository.findByUserIdWithIsPrintedCancel(userId, isPrinted.get(), cancel.get());
			} else {
				saleTmps = saleRepository.findByUserIdWithisPrinted(userId, isPrinted.get());
			}
		} else {
			saleTmps = saleRepository.findByUserId(userId);
		}

		if (saleTmps.size() == 0) {
			if (isPrinted.isPresent()) {
				if (cancel.isPresent()) {
					saleTmps = saleRepository.findByUserIdSeatWithIsPrintedCancel(userId, isPrinted.get(),
							cancel.get());
				} else {
					saleTmps = saleRepository.findByUserIdSeatWithisPrinted(userId, isPrinted.get());
				}
			} else {
				saleTmps = saleRepository.findBySeatUserId(userId);
			}
		}
		return saleTmps;
	}

	@Transactional
	public Object moveToPendingOrder(Optional<Integer> seatId, String remark, Integer userId) {
		Invoice invoice = new Invoice();
		invoice.setRemark(remark);
		invoice.setUser(userProfile.getProfile().getUser());
		invoice.setBranch(userProfile.getProfile().getUser().getBranch());
		if (seatId.isPresent()) {
			Seat seat = seatRepository.findById(seatId.get())
					.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist"));
			if (seat.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Transaction is unauthorized");
			}
			List<SaleTemporary> saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size() > 0 && !saletmps.get(0).getUserEdit().getId().equals(userId)) {
				saleRepository.updateUserEditSeat(userProfile.getProfile().getUser().getId(), seatId.get());
				return saletmps;
			}
			boolean checkSaleTmp = saleRepository.existsBySeatIdWithUserEditId(seatId.get(), userId);
			if (!checkSaleTmp) {
				throw new ConflictException("ប្រតិបតិ្តការនេះបានបម្រុងដោយអ្នកផ្សេងរួចហើយ", "15");
			}
			invoice = invoiceRepository.save(invoice);
			saleRepository.updateInvoiceBySeatId(invoice.getId(), seatId.get());
		} else {
			invoice = invoiceRepository.save(invoice);
			saleRepository.updateInvoiceByUserId(invoice.getId(), userProfile.getProfile().getUser().getId());
		}
		invoice.setSaleTemporaries(saleRepository.findByInvoiceId(invoice.getId()));
		return invoice;
	}

	private SaleTemporary addItem(SaleRequest requestItem, User user, Optional<Seat> seat, Optional<Invoice> invoice,
			Optional<SaleTemporary> parentSale,Optional<PaymentChannel> channelId) {
		Long saleTmpId = requestItem.getSaleTmpId();
		Long itemId = requestItem.getItemId();
		Short quantity = requestItem.getQuantity();
		Short discountPercentage = requestItem.getDiscountPercentage();
		Double discountAmount = requestItem.getDiscountAmount();
		Double price = requestItem.getPrice();
		if (quantity < 1) {
			throw new UnprocessableEntityException("Quantity must be greater than 0");
		}
		return itemBranchRepository.findById(itemId).map((item) -> {
			if (!item.isEnable()) {
				throw new ConflictException("Item is disable");
			}
			if ((item.getBranch().getId() != userProfile.getProfile().getBranch().getId()) || !item.isEnable()) {
				throw new UnauthorizedException("Item is unauthorized");
			}
			if (item.isStock()) {
				if (item.getItemBalance() < quantity) {
					String setting = branchSettingRepository
							.findByBranchIdAndSettingCode(userProfile.getProfile().getBranch().getId(), "STN")
							.orElse("");
					if (!setting.contentEquals(setting))
						throw new ConflictException("ចំនួនដែលបញ្ជាទិញច្រើនចំនួនក្នុងស្តុក", "09");
				}
			}

			return saleRepository.findById(saleTmpId).map(saleTmp -> {

				saleTmp.setValueDate(new Date());
				saleTmp.setQuantity(quantity);
				saleTmp.setDiscountPercentage(discountPercentage);
				saleTmp.setDiscountAmount(BigDecimal.valueOf(discountAmount));
				saleTmp.setPrice(BigDecimal.valueOf(price));
				saleTmp.setUserEdit(user);
				if (invoice.isPresent()) {
					saleTmp.setInvoice(invoice.get());
				} else if (seat.isPresent()) {
					saleTmp.setSeat(seat.get());
				}
				if (parentSale.isPresent() && item.getType().contentEquals("SUBITEM")) {
					saleTmp.setParentSaleTemporary(parentSale.get());
				}
				if(channelId.isPresent())
				{
					saleTmp.setPaymentChannel(channelId.get());
				}
				else {
					saleTmp.setPaymentChannel(null);
				}
				return saleRepository.save(saleTmp);
			}).orElseGet(() -> {
				if (saleTmpId > 0 && item.getType().contentEquals("MAINITEM")) {
					throw new ConflictException("វិក័យប័ត្រនេះបានគិតរួចហើយ", "16");
				}
				SaleTemporary saleTmp = new SaleTemporary();
				saleTmp.setItemBranch(item);
				saleTmp.setValueDate(new Date());
				saleTmp.setQuantity(quantity);
				saleTmp.setPrice(BigDecimal.valueOf(price));
				saleTmp.setDiscountPercentage(discountPercentage);
				saleTmp.setDiscountAmount(BigDecimal.valueOf(discountAmount));
				saleTmp.setPrinted(false);
				saleTmp.setCancel(false);
				saleTmp.setBillNumber((long)0);
				saleTmp.setUser(user);
				saleTmp.setUserEdit(user);
				if(channelId.isPresent())
				{
					saleTmp.setPaymentChannel(channelId.get());
				}
				else {
					saleTmp.setPaymentChannel(null);
				}
				if (invoice.isPresent()) {
					saleTmp.setInvoice(invoice.get());
				}
				if (seat.isPresent()) {
					saleTmp.setSeat(seat.get());
				}
				if (parentSale.isPresent() && item.getType().contentEquals("SUBITEM")) {
					saleTmp.setParentSaleTemporary(parentSale.get());
				}
				return saleRepository.save(saleTmp);
			});
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}
}
