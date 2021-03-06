package com.spring.miniposbackend.service.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.spring.miniposbackend.model.admin.Seat;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.sale.Invoice;
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.modelview.SaleRequest;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
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
	private UserProfileUtil userProfile;
	@Autowired
	private BranchSettingRepository branchSettingRepository;

	@Transactional
	public List<SaleTemporary> addItems(List<SaleRequest> requestItems, Optional<Integer> seatId,
			Optional<Long> invoiceId) {
		entityManager.clear();
		Optional<Seat> seat = Optional.empty();
		Optional<Invoice> invoice = Optional.empty();
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		User user = userProfile.getProfile().getUser();
		if (invoiceId.isPresent()) {
			invoice = invoiceRepository.findById(invoiceId.get());
			if (!invoice.isPresent()) {
				throw new ResourceNotFoundException("Invoice does not exit");
			}
			saletmps = saleRepository.findByInvoiceId(invoiceId.get());
			if (saletmps.size()>0 && !saletmps.get(0).getUserEdit().getId().equals(user.getId())) {
				saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saletmps;
			}
		} else if (seatId.isPresent()) {
			seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}
			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size()>0 && !saletmps.get(0).getUserEdit().getId().equals(user.getId())) {
				saleRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saletmps;
			}
		}
		Optional<Seat> finSeat = seat;
		Optional<Invoice> finInvoice = invoice;

		requestItems.forEach((requestItem) -> {
			SaleTemporary saleTemporary = addItem(requestItem, user, finSeat, finInvoice, Optional.empty());
			List<SaleRequest> subItems = requestItem.getAddOns() == null ? new ArrayList<SaleRequest>()
					: requestItem.getAddOns();
			subItems.forEach((subItem) -> {
				SaleTemporary addOnItem = addItem(subItem, user, finSeat, finInvoice, Optional.of(saleTemporary));
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
	public List<SaleTemporary> removeItem(Long saleTempId, Optional<Integer> seatId, Optional<Long> invoiceId) {
		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		User user = userProfile.getProfile().getUser();
		if (invoiceId.isPresent()) {
			Optional<Invoice> invoice = invoiceRepository.findById(invoiceId.get());
			if (!invoice.isPresent()) {
				throw new ResourceNotFoundException("Invoice does not exit");
			}
			saletmps = saleRepository.findByInvoiceId(invoiceId.get());
			if (saletmps.size()>0 &&  !saletmps.get(0).getUserEdit().getId().equals(user.getId())) {
				saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saletmps;
			}
		} else if (seatId.isPresent()) {
			Optional<Seat> seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}
			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size()>0 &&  !saletmps.get(0).getUserEdit().getId().equals(user.getId())) {
				saleRepository.updateUserEditSeat(user.getId(), seatId.get());
				return saletmps;
			}
		}
		SaleTemporary saletemp = saleRepository.findById(saleTempId).map(sale -> {
			if (sale.isPrinted()) {
				throw new ConflictException("Record is already printed");
			}
			ItemBranch itemBranch = sale.getItemBranch();
			if ((itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId())
					|| !itemBranch.isEnable()) {
				throw new UnauthorizedException("Item is unauthorized");
			}
			saleRepository.deleteBySaleTempId(saleTempId);
			return sale;
		}).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));

		list.add(saletemp);

		return list;

	}

	@Transactional
	public List<SaleTemporary> setQuantity(Long saleTempId, Short quantity, Optional<Integer> seatId,
			Optional<Long> invoiceId) {
		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		User user = userProfile.getProfile().getUser();
		List<SaleTemporary> saletmps = new ArrayList<SaleTemporary>();
		if (invoiceId.isPresent()) {
			Optional<Invoice> invoice = invoiceRepository.findById(invoiceId.get());
			if (!invoice.isPresent()) {
				throw new ResourceNotFoundException("Invoice does not exit");
			}
			saletmps = saleRepository.findByInvoiceId(invoiceId.get());
			if (saletmps.size()>0 &&  !saletmps.get(0).getUserEdit().getId().equals(user.getId())) {
				saleRepository.updateUserEditInvoice(user.getId(), invoiceId.get());
				return saletmps;
			}
		} else if (seatId.isPresent()) {
			Optional<Seat> seat = seatRepository.findById(seatId.get());
			if (!seat.isPresent()) {
				throw new ResourceNotFoundException("Seat does not exist");
			}
			saletmps = saleRepository.findBySeatId(seatId.get());
			if (saletmps.size()>0 &&  !saletmps.get(0).getUserEdit().getId().equals(user.getId())) {
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
	public boolean printBySeat(Integer seatId) throws Exception {
		try {
			List<SaleTemporary> list = saleRepository.findBySeatId(seatId);
			list.forEach(saleTemporary -> {
				saleTemporary.setPrinted(true);
				saleRepository.save(saleTemporary);
			});
			return true;
		} catch (Exception ex) {
			throw new ConflictException("prin SaleTmp Failed", "04");
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

	public List<SaleTemporary> showByUserId(Integer userId, Optional<Boolean> isPrinted, Optional<Boolean> cancel,
			boolean OBU) {
		if (!OBU) {
			if (isPrinted.isPresent()) {
				if (cancel.isPresent()) {
					return saleRepository.findByUserIdSeatWithIsPrintedCancel(userId, isPrinted.get(), cancel.get());
				} else {
					return saleRepository.findByUserIdSeatWithisPrinted(userId, isPrinted.get());
				}
			} else {
				return saleRepository.findBySeatUserId(userId);
			}
		} else {
			if (isPrinted.isPresent()) {
				if (cancel.isPresent()) {
					return saleRepository.findByUserIdWithIsPrintedCancel(userId, isPrinted.get(), cancel.get());
				} else {
					return saleRepository.findByUserIdWithisPrinted(userId, isPrinted.get());
				}
			} else {
				return saleRepository.findByUserId(userId);
			}
		}

	}

	private SaleTemporary addItem(SaleRequest requestItem, User user, Optional<Seat> seat, Optional<Invoice> invoice,
			Optional<SaleTemporary> parentSale) {
		Long saleTmpId = requestItem.getSaleTmpId();
		Long itemId = requestItem.getItemId();
		Short quantity = requestItem.getQuantity();
		Short discountPercentage = requestItem.getDiscountPercentage();
		Double discountAmount = requestItem.getDiscountAmount();
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
				saleTmp.setPrice(item.getPrice());
				saleTmp.setUserEdit(user);
				if (invoice.isPresent()) {
					saleTmp.setInvoice(invoice.get());
				} else if (seat.isPresent()) {
					saleTmp.setSeat(seat.get());
				}
				if (parentSale.isPresent() && item.getType().contentEquals("SUBITEM")) {
					saleTmp.setParentSaleTemporary(parentSale.get());
				}
				return saleRepository.save(saleTmp);
			}).orElseGet(() -> {
				SaleTemporary saleTmp = new SaleTemporary();
				saleTmp.setItemBranch(item);
				saleTmp.setValueDate(new Date());
				saleTmp.setQuantity(quantity);
				saleTmp.setPrice(item.getPrice());
				saleTmp.setDiscountPercentage(discountPercentage);
				saleTmp.setDiscountAmount(BigDecimal.valueOf(discountAmount));
				saleTmp.setPrinted(false);
				saleTmp.setCancel(false);
				saleTmp.setUser(user);
				saleTmp.setUserEdit(user);
				if (invoice.isPresent()) {
					saleTmp.setInvoice(invoice.get());
				} else if (seat.isPresent()) {
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
