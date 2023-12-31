package com.spring.miniposbackend.service.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.modelview.ItemBranchCheckList;
import com.spring.miniposbackend.modelview.ItemBranchUpdate;
import com.spring.miniposbackend.modelview.PointRewardRequest;
import com.spring.miniposbackend.modelview.SubItemView;
import com.spring.miniposbackend.modelview.account.PointAndRewardView;
import com.spring.miniposbackend.modelview.packages.PackageItemView;
import com.spring.miniposbackend.modelview.packages.PackageListView;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ItemBranchService {
	@Autowired
	private SaleTemporaryRepository saleTemporaryRepository;
	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	private ItemTypeRepository itemTypeRepository;

	@Value("${file.path.image.item}")
	private String imagePath;

	public List<ItemBranch> showByItemId(Long itemId, Optional<Boolean> enable) {
		if (enable.isPresent()) {
			return itemBranchRepository.findByItemId(itemId, enable.get());
		} else {
			return itemBranchRepository.findByItemId(itemId);
		}
	}

	public List<ItemBranch> showByItemCheckListId(Long itemId) {

		return itemBranchRepository.findByItemCheckListId(itemId);
	}

	public List<ItemBranch> doCheckList(List<ItemBranchCheckList> checkList) {
		List<ItemBranch> itemList = new ArrayList<ItemBranch>();
		checkList.forEach((item) -> {
			ItemBranch itemBr = itemBranchRepository.findById(item.getItemBranchId())
					.orElseThrow(() -> new ResourceNotFoundException("ItemBranch does not exist"));
			if (itemBr.getItemBalance().doubleValue() > 0) {
				throw new ConflictException("We still have the item in stock", "10");
			}
			if (saleTemporaryRepository.existsByItemId(itemBr.getId())) {
				throw new ConflictException("The item is pending ordered", "11");
			}
			itemBr.setEnable(item.isEnable());
			itemBranchRepository.save(itemBr);
			itemList.add(itemBr);
		});
		return itemList;
	}

	public List<ItemBranch> showByBranchId(Integer branchId, Optional<Boolean> enable) {
		return branchRepository.findById(branchId).map(branch -> {
			if (branch.getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			if (enable.isPresent()) {
				return itemBranchRepository.findByBranchId(branchId, enable.get());
			} else {
				return itemBranchRepository.findByBranchId(branchId);
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}

	public ImageResponse getImage(Long itemBranchId) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			if (itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			return getImage(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ImageResponse getImage(ItemBranch itemBranch) {
		if (itemBranch.getImage() == null) {
			return new ImageResponse(itemBranch.getItem_Id(), null, itemBranch.getVersion());
		}
		try {
			String fileLocation = imagePath + "/" + itemBranch.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(itemBranch.getItem_Id(), bArray, itemBranch.getVersion());

		} catch (Exception e) {
			return new ImageResponse(itemBranch.getItem_Id(), null, itemBranch.getVersion());
		}
	}

	public List<ImageResponse> getImages(Integer branchId) {
		List<ItemBranch> itemBranches = itemBranchRepository.findByBranchId(branchId, true);
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		itemBranches.forEach((itemBranch) -> {
			ImageResponse image = getImage(itemBranch);
			images.add(image);
		});
		return images;
	}

	public List<ImageResponse> getImages(List<ImageRequest> requestImages) {
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		requestImages.forEach((requestImage) -> {
			ItemBranch itemBranch = itemBranchRepository.findById(requestImage.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
			if (itemBranch.getVersion() > requestImage.getVersion()) {
				if (itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
					throw new UnauthorizedException("Branch is authorized");
				}
				ImageResponse image = getImage(itemBranch);
				images.add(image);
			}
		});
		return images;
	}

	public List<ImageResponse> getImagesFromList(List<ImageRequest> requestImages) {
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		requestImages.forEach((requestImage) -> {
			ItemBranch itemBranch = itemBranchRepository.findById(requestImage.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
			if (itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
				throw new UnauthorizedException("Branch is authorized");
			}
			ImageResponse image = getImage(itemBranch);
			images.add(image);
		});
		return images;
	}

	@Transactional
	public void refresh() {
		List<Branch> branches = branchRepository.findAll();
		branches.forEach((branch) -> {
			List<Item> items = itemRepository.findByCorporateId(branch.getCorporate().getId());
			items.forEach((item) -> {
				Long itemId = item.getId();
				Integer branchId = branch.getId();
				Optional<ItemBranch> itemBranch = itemBranchRepository
						.findFirstByBranchIdAndItemIdOrderByIdDesc(branchId, itemId);
				if (!itemBranch.isPresent()) {
					ItemBranch itemBr = new ItemBranch();
					itemBr.setBranch(branch);
					itemBr.setItem(item);
					itemBr.setUseItemConfiguration(true);
					itemBr.setEnable(false);
					itemBr.setStockIn(BigDecimal.valueOf(0));
					itemBr.setStockOut(BigDecimal.valueOf(0));
					itemBr.setPrice(item.getPrice());
					itemBr.setDiscount(itemBr.getDiscount());
					itemBr.setPoint((short) 0);
					itemBr.setReward((short) 0);
					itemBranchRepository.save(itemBr);
				}
//				else {
//					itemBranch.get().setPrice(item.getPrice());
//					itemBranch.get().setDiscount(item.getDiscount());
//					itemBranchRepository.save(itemBranch.get());
//					
//				}
			});
		});
	}

	public ItemBranch update(Long itemBranchId, ItemBranchUpdate requestItem) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			if (itemBranch.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			itemBranch.setUseItemConfiguration(requestItem.isUseItemConfiguration());
			itemBranch.setPrice(requestItem.getPrice());
			itemBranch.setDiscount(requestItem.getDiscount());
			itemBranch.setEnable(requestItem.isEnable());
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch updateAddOn(Long itemBranchId, List<Long> addOnItems) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			if (itemBranch.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			itemBranch.setAddOnItems(addOnItems);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch updateAddOnInventory(Long itemBranchId, List<Long> addOnItems) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			if (itemBranch.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			itemBranch.setAddOnInven(addOnItems);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch setPrice(Long itemBranchId, BigDecimal price, Short discount, BigDecimal costing,
			Optional<Short> point) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			itemBranch.setPrice(price);
			itemBranch.setDiscount(discount);
			itemBranch.setCosting(costing);
			if (point.isPresent())
				itemBranch.setPoint(point.get());
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch setDiscount(Long itemBranchId, Short discount) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			itemBranch.setDiscount(discount);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch setInvenQty(Long itemBranchId, double qty) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			itemBranch.setInvenQty(BigDecimal.valueOf(qty));
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch setEnable(Long itemBranchId, Boolean enable) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			if (itemBranch.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			itemBranch.setEnable(enable);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch disable(Long itemBranchId) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			if (itemBranch.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			boolean pending = false;
			if (itemBranch.getItemBalance().doubleValue() > 0) {
				throw new ConflictException("The item is still in stock", "13");
			}
			pending = saleTemporaryRepository.existsByItemId(itemBranchId);
			if (pending)
				throw new ConflictException("The item is pending ordered", "10");
			List<ItemBranch> itemsBranch = itemBranchRepository.findAnyAddOn(itemBranchId);
			if (itemsBranch.size() > 0) {
				throw new ConflictException("Please remove sub item from " + itemsBranch.get(0).getNameKh(), "13");
			}
			itemBranch.setEnable(false);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	@Transactional
	public List<ItemBranch> updatePointAndReward(PointRewardRequest itemBranchView) {
		List<ItemBranch> list = new ArrayList<>();
		for (int i = 0; i < itemBranchView.getItemBranchId().size(); i++) {
			ItemBranch itemBranch = itemBranchRepository.findById(itemBranchView.getItemBranchId().get(i))
					.orElseThrow(() -> new ResourceNotFoundException("This item doesn’t exit", "01"));
			if (userProfile.getProfile().getBranch().getId() != itemBranch.getBranch().getId()) {
				throw new UnauthorizedException("Branch is unauthorized");
			}
			itemBranch.setPoint(itemBranchView.getPoint());
			itemBranch.setReward(itemBranchView.getReward());
			itemBranchRepository.save(itemBranch);
			list.add(itemBranch);
		}
		return list;
	}

	@Transactional
	public List<ItemBranch> updateByItemTypeId(Integer itemTypeId, PointAndRewardView pointAndRewardView) {
		ItemType itemType = itemTypeRepository
				.findBycorporateIdandId(userProfile.getProfile().getBranch().getCorporate().getId(), itemTypeId)
				.orElseThrow(() -> new ResourceNotFoundException("Please refresh the app", "01"));
		List<Item> item = itemRepository.findByItemTypeId(itemType.getId());
		ItemBranch itemBranch = null;
		List<ItemBranch> list = new ArrayList<>();
		for (int i = 0; i < item.size(); i++) {
			itemBranch = itemBranchRepository
					.findFirstByBranchIdAndItemIdOrderByIdDesc(userProfile.getProfile().getBranch().getId(),
							item.get(i).getId())
					.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
			list.add(itemBranch);
			itemBranch.setPoint(pointAndRewardView.getPoint());
			itemBranch.setReward(pointAndRewardView.getReward());
			itemBranchRepository.save(itemBranch);
		}
		return list;
	}

	@Transactional
	public ItemBranch updateById(Long itemBranchId, PointAndRewardView pointAndRewardView) {
		ItemBranch itemBranch = itemBranchRepository
				.findByBranchIdandId(userProfile.getProfile().getBranch().getId(), itemBranchId)
				.orElseThrow(() -> new ResourceNotFoundException("Please refresh the app", "01"));
		itemBranch.setPoint(pointAndRewardView.getPoint());
		itemBranch.setReward(pointAndRewardView.getReward());
		itemBranchRepository.save(itemBranch);
		return itemBranch;
	}

	@Transactional
	public List<Map<String, Object>> createjsonb(Long id, List<PackageItemView> packageItem) throws Exception {
		try {

			List<Map<String, Object>> itembranch = new ArrayList<>();
			ItemBranch itembr = itemBranchRepository
					.findByBranchIdandId(userProfile.getProfile().getBranch().getId(), id)
					.orElseThrow(() -> new ResourceNotFoundException("This item is not package", "01"));

			List<Map<String, Object>> list = new ArrayList<>();
			for (int i = 0; i < packageItem.size(); i++) {
				ItemBranch ibranch = itemBranchRepository.findById(packageItem.get(i).getItemBranchId())
						.orElseThrow(() -> new ResourceNotFoundException("This item does not exit", "01"));
				Map<String, Object> json = new HashMap<String, Object>();
				json.put("id", ibranch.getId());
				// json.put("Discount", packageItem.get(i).getDiscount());
				json.put("qty", packageItem.get(i).getQty());
				json.put("freq", packageItem.get(i).getFreq());
				list.add(json);
				itembranch.add(json);

			}
			itembr.setAddOnPackages(itembranch);
			itemBranchRepository.save(itembr);
			return itembranch;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Transactional
	public ItemBranch updateItemPackage(long itemPackageId, List<PackageItemView> itemBranchxItemPackage)
			throws Exception {
		try {
			List<Map<String, Object>> itembranch = new ArrayList<>();

			ItemBranch ibranch = itemBranchRepository
					.findByBranchIdandId(userProfile.getProfile().getBranch().getId(), itemPackageId)
					.orElseThrow(() -> new ResourceNotFoundException("This itempackage doesn't exit", "01"));

			if (ibranch.getAddOnPackages() == null || ibranch.getAddOnPackages().size() < 0) {
				throw new ResourceNotFoundException("This package doesn't have item yet please create item first",
						"02");
			}

			List<Map<String, Object>> list = new ArrayList<>();
			for (int j = 0; j < itemBranchxItemPackage.size(); j++) {
				Map<String, Object> json = new HashMap<String, Object>();
				ItemBranch ibId = itemBranchRepository.findById(itemBranchxItemPackage.get(j).getItemBranchId())
						.orElseThrow(
								() -> new ResourceNotFoundException("This item doesn't exit in your branch", "03"));
				json.put("id", ibId.getId());
				// json.put("Discount", packageItem.get(i).getDiscount());
				json.put("qty", itemBranchxItemPackage.get(j).getQty());
				json.put("freq", itemBranchxItemPackage.get(j).getFreq());
				list.add(json);
				itembranch.add(json);

			}
			ibranch.setAddOnPackages(itembranch);
			itemBranchRepository.save(ibranch);
			return ibranch;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}
	@Transactional
	 public List<SubItemView> subItem(Long id, List<SubItemView> subItemView) throws Exception {
	  try {
	   ItemBranch itemBranch = itemBranchRepository.findById(id)
	     .orElseThrow(() -> new ResourceNotFoundException("This MainItem does not exit", "01"));
	   if (itemBranch.getBranch().getId() != userProfile.getProfile().getBranch().getId()) {
	    
	    throw new UnauthorizedException("Branch is unauthorized","03 ");
	   }
	   for (int i = 0; i < subItemView.size(); i++) {
	    ItemBranch ibranch = itemBranchRepository.findById(subItemView.get(i).getId())
	      .orElseThrow(() -> new ResourceNotFoundException("This SubItem does not exit", "02"));
//	    Map<String, Object> json = new HashMap<String, Object>();
//	    json.put("id", ibranch.getId());
//	    json.put("required", subItemView.get(i).isRequired());
//	    json.put("sort", subItemView.get(i).getSort());
//	    listMap.add(json);

	   }
	   itemBranch.setAddOnItem(subItemView);;
	   itemBranchRepository.save(itemBranch);
	   return subItemView;
	  } catch (Exception e) {
	   throw new Exception(e.getMessage());
	  }
	 }
}
