package com.spring.miniposbackend.service.sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.spring.miniposbackend.repository.admin.UserRepository;
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
import com.spring.miniposbackend.model.sale.SaleTemporary;
import com.spring.miniposbackend.modelview.SaleRequest;
import com.spring.miniposbackend.repository.admin.BranchSettingRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.SeatRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class SaleTemporaryService {

	@Autowired
	private ItemBranchRepository itemBranchRepository;

	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private SaleTemporaryRepository saleRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private BranchSettingRepository branchSettingRepository;

	@Transactional
	public List<SaleTemporary> addItems(List<SaleRequest> requestItems, boolean OBU, Integer userId) {
//		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		for (int i = 0; i < requestItems.size(); i++) {
			for (int j = i + 1; j < requestItems.size(); j++) {
				if (requestItems.get(i).getItemId().equals(requestItems.get(j).getItemId())) {
					throw new UnprocessableEntityException("itemId are duplicated");
				}
			}
		}
		Optional<Seat> seat;
		if (!OBU) {
			Integer seatId = requestItems.get(0).getSeatId();
			List<SaleTemporary> saletmp = saleRepository.findBySeatId(seatId);
			seat = Optional.of(seatRepository.findById(seatId)
					.orElseThrow(() -> new ResourceNotFoundException("Seat does not exist")));
			if (saletmp.size() > 0) {
				if (!saletmp.get(0).getUserEdit().getId().equals(userProfile.getProfile().getUser().getId())) {
					saleRepository.updateUserEdit(userProfile.getProfile().getUser().getId(), seatId);
					return saletmp;
				}
			}
		} else {
			seat = Optional.empty();
		}
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
		requestItems.forEach((requestItem) -> {
			SaleTemporary saleTemporary = addItem(requestItem, user, seat, Optional.empty());
//			list.add(saleTemporary);
			List<SaleRequest> subItems = requestItem.getAddOns() == null ? new ArrayList<SaleRequest>():requestItem.getAddOns();
			subItems.forEach((subItem) -> {
				addItem(subItem, user, seat, Optional.of(saleTemporary));
			});
		});
		if (!OBU) {
			return saleRepository.findByUserId(userId,seat.get().getId());
		}else {
			return saleRepository.findByUserId(userId);
		}
			
	}

	@Transactional
	public List<SaleTemporary> removeItem(Long saleTempId, Integer seatId, boolean OBU) {
		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		if (!OBU) {
			List<SaleTemporary> saletmp = saleRepository.findBySeatId(seatId);
			if (!saletmp.get(0).getUserEdit().getId().equals(userProfile.getProfile().getUser().getId())) {
				saleRepository.updateUserEdit(userProfile.getProfile().getUser().getId(), seatId);
				return saletmp;

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
			// sale.setCancel(true);
			saleRepository.deleteBySaleTempId(saleTempId);
			return sale;
		}).orElseThrow(() -> new ResourceNotFoundException("Record does not exist"));
		list.add(saletemp);
		if (!OBU)
			saleRepository.updateUserEdit(userProfile.getProfile().getUser().getId(), seatId);

		return list;

	}

	@Transactional
	public List<SaleTemporary> setQuantity(Long saleTempId, Short quantity, Integer seatId, Integer userId,
			boolean OBU) {
		List<SaleTemporary> list = new ArrayList<SaleTemporary>();
		if (!OBU) {
			List<SaleTemporary> saletmp = saleRepository.findBySeatId(seatId);
			if (!saletmp.get(0).getUserEdit().getId().equals(userProfile.getProfile().getUser().getId())) {
				saleRepository.updateUserEdit(userProfile.getProfile().getUser().getId(), seatId);
				return saletmp;
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
			sale.setUser(userProfile.getProfile().getUser());
			sale.setUserEdit(userProfile.getProfile().getUser());
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

	private SaleTemporary addItem(SaleRequest requestItem, User user, Optional<Seat> seat,
			Optional<SaleTemporary> parentSale) {
		Long saleTmpId = requestItem.getSaleTmpId();
		Long itemId = requestItem.getItemId();
		Short quantity = requestItem.getQuantity();
		Short discount = requestItem.getDiscount();
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
				saleTmp.setDiscount(discount);
				saleTmp.setDiscountAmount(discountAmount);
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
				saleTmp.setDiscount(discount);
				saleTmp.setPrinted(false);
				saleTmp.setCancel(false);
				saleTmp.setUser(user);
				saleTmp.setUserEdit(user);
				saleTmp.setDiscountAmount(discountAmount);
				if (parentSale.isPresent() && item.getType().contentEquals("SUBITEM")) {
					saleTmp.setParentSaleTemporary(parentSale.get());
				}
				if (seat.isPresent()) {
					saleTmp.setSeat(seat.get());
				}
				return saleRepository.save(saleTmp);
			});
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}
}
