package com.spring.miniposbackend.service.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ItemTypeService {

	@Autowired
	private ItemTypeRepository itemTypeRepository;
	@Autowired
	private CorporateRepository corporateRepository;
	@Autowired
	private ImageUtil imageUtil;

	@Value("${file.path.image.item-type}")
	private String imagePath;

	@Autowired
	private UserProfileUtil userProfile;

//	@Transactional(readOnly = true)
//	public List<ItemType> shows(){
//		return itemTypeRepository.findAll();
//	}
//	
//	@Transactional(readOnly = true)
//	public List<ItemType> shows(boolean enable){
//		return itemTypeRepository.findAllWithEnable(enable);
//	}
//	
//	@Transactional(readOnly = true)
//	public ItemType show(Integer itemTypeId) {
//		return itemTypeRepository.findById(itemTypeId)
//				.orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
//	}

	public List<ItemType> showByCorporateId(Integer corporateId, Optional<Boolean> enable) {
		return corporateRepository.findById(corporateId).map(corporate -> {
			if (!corporate.isEnable()) {
				throw new ConflictException("Corporate is disable");
			}
			if (enable.isPresent()) {
				return itemTypeRepository.findByCorporateIdWithEnable(corporateId, enable.get());
			} else {
				return itemTypeRepository.findByCorporateId(corporateId);
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Corporate does not exist"));

	}

	public ItemType uploadImage(Integer itemTypeId, MultipartFile file) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			if (file.isEmpty()) {
				throw new ResourceNotFoundException("File content does not exist");
			}
			try {
				// read and write the file to the selected location-
				String baseLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base"));
				String fileName = imageUtil.uploadImage(baseLocation, itemType.getId().toString(), file);
				itemType.setImage(fileName);
				itemType.setVersion((short) (itemType.getVersion() + 1));
				return itemTypeRepository.save(itemType);
			} catch (IOException e) {
				throw new ConflictException("Upable to upload File");

			} catch (Exception e) {
				throw new ConflictException("Exception :" + e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

	public ImageResponse getImage(Integer itemTypeId) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			return getImage(itemType);
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

	public ImageResponse getImage(ItemType itemType) {
		if (itemType.getImage().isEmpty()) {
			return new ImageResponse(itemType.getId().longValue(), null, itemType.getVersion());
		}
		try {
			String fileLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base")) + "/"
					+ itemType.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(itemType.getId().longValue(), bArray, itemType.getVersion());

		} catch (Exception e) {
			return new ImageResponse(itemType.getId().longValue(), null, itemType.getVersion());
		}
	}

	public List<ImageResponse> getImages(Integer corporateId) {
		List<ItemType> itemTypes = itemTypeRepository.findByCorporateIdWithEnable(corporateId, true);
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		itemTypes.forEach((itemType) -> {
			ImageResponse image = getImage(itemType);
			images.add(image);
		});
		return images;
	}

	public List<ImageResponse> getImages(List<ImageRequest> requestImages) {
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		requestImages.forEach((requestImage) -> {
			ItemType itemType = itemTypeRepository.findById(requestImage.getId().intValue())
					.orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
			if (itemType.getVersion() > requestImage.getVersion()) {
				ImageResponse image = getImage(itemType);
				images.add(image);
			}
		});
		return images;
	}

	public ItemType create(Integer corporateId, ItemType requestItemType) {
		return corporateRepository.findById(corporateId).map((corporate) -> {
			if (userProfile.getProfile().getCorporate().getId() != corporate.getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			ItemType itemType = new ItemType();
			itemType.setCorporate(corporate);
			itemType.setName(requestItemType.getName());
			itemType.setNameKh(requestItemType.getNameKh());
			itemType.setEnable(true);
			return itemTypeRepository.save(itemType);
		}).orElseThrow(() -> new ResourceNotFoundException("Corporate does not exist"));
	}

	public ItemType update(Integer itemTypeId, ItemType requestItemType) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			if (userProfile.getProfile().getCorporate().getId() != itemType.getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			itemType.setName(requestItemType.getName());
			itemType.setNameKh(requestItemType.getNameKh());
			itemType.setEnable(requestItemType.isEnable());
			return itemTypeRepository.save(itemType);
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}
	
	public ItemType disable(Integer itemTypeId) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			if (userProfile.getProfile().getCorporate().getId() != itemType.getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			itemType.setEnable(false);
			return itemTypeRepository.save(itemType);
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

//	public ItemType setEnable(Integer itemTypeId, boolean enable) {
//		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
//			if (userProfile.getProfile().getCorporate().getId() != itemType.getCorporate().getId()) {
//				throw new UnauthorizedException("Corporate is unauthorized");
//			}
//			itemType.setEnable(enable);
//			return itemTypeRepository.save(itemType);
//		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
//	}
}
