package com.spring.miniposbackend.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.modelview.ItemBranchUpdate;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.util.ImageUtil;

@Service
public class ItemBranchService {

	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ImageUtil imageUtil;

	@Value("${file.path.image.item}")
	private String imagePath;

	public List<ItemBranch> showByItemId(Long itemId, Optional<Boolean> enable) {
		if (enable.isPresent()) {
			return itemBranchRepository.findByItemIdWithEnable(itemId, enable.get());
		} else {
			return itemBranchRepository.findByItemId(itemId);
		}
	}

	public List<ItemBranch> showByBranchId(Integer branchId, Optional<Boolean> enable) {
		return branchRepository.findById(branchId).map(branch -> {
			if (enable.isPresent()) {
				return itemBranchRepository.findByBranchIdWithEnable(branchId, enable.get());
			} else {
				return itemBranchRepository.findByBranchId(branchId);
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
	}

	public ImageResponse getImage(Long itemBranchId) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			return getImage(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ImageResponse getImage(ItemBranch itemBranch) {
		if (itemBranch.getImage().isEmpty()) {
			return new ImageResponse(itemBranch.getId(), null, itemBranch.getVersion());
		}
		try {
			String fileLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base")) + "/"
					+ itemBranch.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(itemBranch.getId(), bArray, itemBranch.getVersion());

		} catch (Exception e) {
			return new ImageResponse(itemBranch.getId(), null, itemBranch.getVersion());
		}
	}

	public List<ImageResponse> getImages(Integer branchId) {
		List<ItemBranch> itemBranches = itemBranchRepository.findByBranchIdWithEnable(branchId, true);
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
				ImageResponse image = getImage(itemBranch);
				images.add(image);
			}
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
					itemBr.setPrice(item.getPrice());
					itemBr.setDiscount(itemBr.getDiscount());
					itemBranchRepository.save(itemBr);
				}
			});
		});
	}

	public ItemBranch update(Long itemBranchId, ItemBranchUpdate requestItem) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			itemBranch.setUseItemConfiguration(requestItem.isUseItemConfiguration());
			itemBranch.setPrice(requestItem.getPrice());
			itemBranch.setDiscount(requestItem.getDiscount());
			itemBranch.setEnable(requestItem.isEnable());
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public ItemBranch setEnable(Long itemBranchId, Boolean enable) {
		return itemBranchRepository.findById(itemBranchId).map(itemBranch -> {
			itemBranch.setEnable(enable);
			return itemBranchRepository.save(itemBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}
}
