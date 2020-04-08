package com.spring.miniposbackend.service.admin;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;

@Service
public class ItemBranchService {

	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private BranchRepository branchRepository;

	@Value("${file.path.image.item}")
	private String imagePath;

	public List<ItemBranch> showByItemId(Integer itemId, Optional<Boolean> enable) {
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
		if(itemBranch.getImage().isEmpty()) {
			return new ImageResponse(itemBranch.getId(), null, itemBranch.getVersion());
		}
		try {
			String fileLocation = Paths.get("").toAbsolutePath().toString() + "/" + imagePath + "/" + itemBranch.getImage();
			File file = new File(fileLocation);
			byte[] bArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
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

}
