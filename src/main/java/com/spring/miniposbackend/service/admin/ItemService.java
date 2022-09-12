package com.spring.miniposbackend.service.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.repository.admin.ImageRepository;
import com.spring.miniposbackend.repository.admin.ItemBranchRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;
import com.spring.miniposbackend.repository.sale.SaleTemporaryRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CorporateRepository corporateReposity;
	@Autowired
	private ItemTypeRepository itemTypeRepository;
	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private UserProfileUtil userProfile;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	private ItemBranchRepository itemBranchRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private SaleTemporaryRepository saleTemporaryRepository;

	@Value("${file.path.image.item}")
	private String imagePath;
	@Value("${file.path.image.item-photo}")
	private String photoPath;

	public List<Item> showByCorpoateId(Integer corporateId, Optional<Boolean> enable) {
		return corporateReposity.findById(corporateId).map(corporate -> {
			if (!corporate.isEnable()) {
				throw new ConflictException("Corporate is disable");
			}
			if (enable.isPresent()) {
				return itemRepository.findByCorporateId(corporateId, enable.get());
			} else {
				return itemRepository.findByCorporateId(corporateId);
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Corporate does not exist"));

	}

	public ImageResponse updateImage(Long itemId, UUID imageId) {
		return imageRepository.findById(imageId).map((image) -> {
			return itemRepository.findById(itemId).map((item) -> {
				if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
				item.setImage(image.getImage());
				item.setVersion((short) (item.getVersion() + 1));
				itemRepository.save(item);
				return getImage(itemId);
			}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("Image does not exist"));
	}

	@Transactional
	public Item uploadImage(Long itemId, MultipartFile file) {
		return itemRepository.findById(itemId).map(item -> {
			if (file.isEmpty()) {
				throw new ResourceNotFoundException("File content does not exist");
			}
			if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			try {
				// read and write the file to the selected location-
				// String baseLocation = String.format("%s/" + imagePath,
				// System.getProperty("catalina.base"));
				String baseLocation = imagePath;
				String fileName = imageUtil.uploadImage(baseLocation, item.getId().toString(), file);
				item.setImage(fileName);
				item.setVersion((short) (item.getVersion() + 1));
				return itemRepository.save(item);
			} catch (IOException e) {
				throw new ConflictException("Upable to upload File");

			} catch (Exception e) {
				throw new ConflictException(e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}
	
	@Transactional
	public Item uploadPhoto(Long itemId, MultipartFile file) throws IOException {
		return itemRepository.findById(itemId).map(item -> {
			if (file.isEmpty()) {
				throw new ResourceNotFoundException("File content does not exist");
			}
			try {

				// read and write the file to the selected location-
				// String baseLocation = String.format("%s/" + imagePath,
				// System.getProperty("catalina.base"));
				String baseLocation = photoPath;
				String fileName = imageUtil.uploadImage(
						baseLocation, itemId.toString(),
						file);
				item.setPhoto(fileName);
				return itemRepository.save(item);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new ConflictException(e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}
	
	public byte[] getFileData(Long itemId) {
		return itemRepository.findById(itemId).map(item -> {
			try {
				   return get(photoPath + "/" + itemId + ".png");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new ConflictException(e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
		
	}

	public byte[] get(String filePath) throws Exception {
		if (filePath.isEmpty()) {
			return null;
		}
		try {
			File file = new File(filePath);
			byte[] bArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
			return bArray;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw new Exception();

		}

	}
	public ImageResponse getImage(Item item) {
		if (item.getImage().isEmpty()) {
			return new ImageResponse(item.getId(), null, item.getVersion());
		}
		try {
//			String fileLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base")) + "/"
//					+ item.getImage();
			String fileLocation = imagePath + "/" + item.getImage();
			byte[] bArray = imageUtil.getImage(fileLocation);
			return new ImageResponse(item.getId(), bArray, item.getVersion());

		} catch (Exception e) {
			return new ImageResponse(item.getId(), null, item.getVersion());
		}
	}

	public ImageResponse getImage(Long itemId) {
		return itemRepository.findById(itemId).map(item -> {
			if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Corporate is unauthorized");
			}
			return getImage(item);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	public List<ImageResponse> getImages(Integer corporateId) {
		List<Item> items = itemRepository.findByCorporateId(corporateId, true);
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		items.forEach((item) -> {
			ImageResponse image = getImage(item);
			images.add(image);
		});
		return images;
	}

	public List<ImageResponse> getImages(List<ImageRequest> requestImages) {
		List<ImageResponse> images = new ArrayList<ImageResponse>();
		requestImages.forEach((requestImage) -> {
			Item item = itemRepository.findById(requestImage.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
			if (item.getVersion() > requestImage.getVersion()) {
				if (item.getItemType().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
				ImageResponse image = getImage(item);
				images.add(image);
			}
		});
		return images;
	}

	@Transactional
	public Item create(Integer itemTypeId, Item requestItem) {
		return itemTypeRepository.findById(itemTypeId).map((itemType) -> {
			if (userProfile.getProfile().getCorporate().getId() != itemType.getCorporate().getId()) {
				throw new UnauthorizedException("Item type is unauthorized");
			}
			Item item = new Item();
			item.setCode(requestItem.getCode());
			item.setType(requestItem.getType());
			item.setName(requestItem.getName());
			item.setNameKh(requestItem.getNameKh());
			item.setPrice(requestItem.getPrice());
			item.setDiscount(requestItem.getDiscount());
			item.setStock(requestItem.isStock());
			item.setItemType(itemType);
			item.setEnable(true);
			itemRepository.save(item);
			List<Branch> branches = branchRepository.findByCorporateId(itemType.getCorporate().getId(), true);
			branches.forEach((branch) -> {
				Integer branchId = branch.getId();
				ItemBranch itemBr = new ItemBranch();
				itemBr.setBranch(branch);
				itemBr.setItem(item);
				itemBr.setUseItemConfiguration(true);
				if (branchId == userProfile.getProfile().getBranch().getId()) {
					itemBr.setEnable(true);
				} else {
					itemBr.setEnable(false);
				}
				itemBr.setAddOnItems(new ArrayList<Long>());
				itemBr.setStockIn(0L);
				itemBr.setStockOut(0L);
				itemBr.setPrice(item.getPrice());
				itemBr.setDiscount(item.getDiscount());
				itemBranchRepository.save(itemBr);

//					else {
//						itemBranch.get().setPrice(item.getPrice());
//						itemBranch.get().setDiscount(item.getDiscount());
//						itemBranchRepository.save(itemBranch.get());
//						
//					}
			});
			return item;
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

	@Transactional
	public ItemBranch update(Long itemId, Item requestItem) {
		boolean check = saleTemporaryRepository.existsByMainItemId(itemId);
		if (check) {
			throw new ConflictException("សូមបញ្ចប់ការបញ្ជាទិញផលិតផលនេះសិនមុននឹងកែប្រែ", "14");
		}
		return itemRepository.findById(itemId).map((item) -> {
			if (userProfile.getProfile().getCorporate().getId() != item.getItemType().getCorporate().getId()) {
				throw new UnauthorizedException("Item is unauthorized");
			}
			List<ItemBranch> itemBr = itemBranchRepository.findByItemId(itemId, true);

			itemBr.forEach((items) -> {
				boolean pending = false;
//				if (items.getItemBalance() > 0) {
//					throw new ConflictException("The item is still in stock", "13");
//				}
				pending = saleTemporaryRepository.existsByItemId(itemId);
				if (pending)
					throw new ConflictException("The item is pending ordered", "10");
			});

			item.setCode(requestItem.getCode());
			item.setType(requestItem.getType());
			item.setName(requestItem.getName());
			item.setNameKh(requestItem.getNameKh());
			item.setStock(requestItem.isStock());
			itemRepository.save(item);
			return itemBranchRepository
					.findFirstByBranchIdAndItemIdOrderByIdDesc(userProfile.getProfile().getBranch().getId(), itemId)
					.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}

	@Transactional
	public Item disable(Long itemId) {
		return itemRepository.findById(itemId).map((item) -> {
			if (userProfile.getProfile().getCorporate().getId() != item.getItemType().getCorporate().getId()) {
				throw new UnauthorizedException("Item is unauthorized");
			}
			List<ItemBranch> itemBr = itemBranchRepository.findByItemId(itemId, true);
			itemBr.forEach((items) -> {
				boolean pending = false;
				if (items.getItemBalance() > 0) {
					throw new ConflictException("The item is still in stock", "13");
				}
				pending = saleTemporaryRepository.existsByItemId(itemId);
				if (pending)
					throw new ConflictException("The item is pending ordered", "10");
			});
			item.setEnable(false);
			return itemRepository.save(item);
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}
}
