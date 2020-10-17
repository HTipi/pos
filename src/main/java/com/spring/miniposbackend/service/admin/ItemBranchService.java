package com.spring.miniposbackend.service.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.modelview.ItemBranchCheckList;
import com.spring.miniposbackend.modelview.ItemBranchUpdate;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;
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
			if (itemBr.getItemBalance() > 0) {
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
			return new ImageResponse(itemBranch.getId(), null, itemBranch.getVersion());
		}
		try {
			String fileLocation = imagePath + "/" + itemBranch.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(itemBranch.getId(), bArray, itemBranch.getVersion());

		} catch (Exception e) {
			return new ImageResponse(itemBranch.getId(), null, itemBranch.getVersion());
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
					itemBr.setStockIn(0L);
					itemBr.setStockOut(0L);
					itemBr.setPrice(item.getPrice());
					itemBr.setDiscount(itemBr.getDiscount());
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

	public ItemBranch setPrice(Long itemBranchId, BigDecimal price, Short discount) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			System.out.print(price);
			itemBranch.setPrice(price);
			itemBranch.setDiscount(discount);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch setDiscount(Long itemBranchId, Short discount) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			itemBranch.setDiscount(discount);
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
}
