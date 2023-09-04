package com.spring.miniposbackend.service.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.modelview.ImageRequest;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.modelview.account.CreditView;
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
	private EntityManager entityManager;
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
				String fileName = imageUtil.uploadImage(baseLocation, itemId.toString(), file);
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
			item.setCosting(BigDecimal.valueOf(0));
			item.setWholePrice(BigDecimal.valueOf(0));
			item.setEnable(true);
			item.setBarCode(requestItem.getBarCode());
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
				itemBr.setStockIn(BigDecimal.valueOf(0));
				itemBr.setStockOut(BigDecimal.valueOf(0));
				itemBr.setCosting(BigDecimal.valueOf(0));
				itemBr.setWholePrice(BigDecimal.valueOf(0));
				itemBr.setPrice(item.getPrice());
				itemBr.setDiscount(item.getDiscount());
				itemBr.setInvenQty(BigDecimal.valueOf(1));
				itemBr.setPoint((short) 0);
				itemBr.setReward((short) 0);
				itemBr.setAddPercent((short) 0);
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
				pending = saleTemporaryRepository.existsByItemId(items.getId());
				if (pending)
					throw new ConflictException("The item is pending ordered", "10");
			});

			item.setCode(requestItem.getCode());
			item.setType(requestItem.getType());
			item.setName(requestItem.getName());
			item.setNameKh(requestItem.getNameKh());
			item.setStock(requestItem.isStock());
			item.setBarCode(requestItem.getBarCode());
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
				if (items.getItemBalance().doubleValue() > 0) {
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

	private ItemBranch createItemBranch(Item item) {
		try {
			ItemBranch itemBranch = new ItemBranch();
			itemBranch.setStockIn(BigDecimal.valueOf(0));
			itemBranch.setStockOut(BigDecimal.valueOf(0));
			itemBranch.setEnable(item.isEnable());
			itemBranch.setDiscount(item.getDiscount());
			itemBranch.setPrice(item.getPrice());
			itemBranch.setUseItemConfiguration(true);
			itemBranch.setItem(item);
			itemBranch.setBranch(userProfile.getProfile().getBranch());
			itemBranch.setAddOnItems(new ArrayList<>());
			itemBranch.setCosting(item.getCosting());
			itemBranch.setWholePrice(item.getWholePrice());
			itemBranch.setReward((short) 0);
			itemBranch.setPoint((short) 0);
			itemBranch.setAddPercent((short) 0);
			itemBranchRepository.save(itemBranch);
			return itemBranch;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BadRequestException(e.getMessage(), "not success!");
			// TODO: handle exception
		}
	}

	@SuppressWarnings("resource")
	@Transactional
	public Integer Uploadexcel(@RequestParam("file") MultipartFile file) throws IOException {
		XSSFWorkbook workbook = null;
		try {
			List<ItemBranch> mainItem = new ArrayList<>();
			List<ItemBranch> subItem = new ArrayList<>();
			List<ItemBranch> listItemBranch = new ArrayList<>();
			ArrayList<Item> listItem = new ArrayList<>();
			List<Long> code = new ArrayList<>();
			workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
				XSSFRow row = sheet.getRow(i + 1);
				ItemType type = itemTypeRepository.findById((int) row.getCell(7).getNumericCellValue())
						.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
				if (userProfile.getProfile().getCorporate().getId() != type.getCorporate().getId()) {
					throw new UnauthorizedException("you are putting other corporate item type");
				}
				Item item = new Item();
				System.out.println(i);
				item.setCode(row.getCell(0).getStringCellValue());
				item.setType(row.getCell(1).getStringCellValue());
				item.setName(row.getCell(2).getStringCellValue());
				item.setNameKh(row.getCell(3).getStringCellValue());
				item.setPrice(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
				item.setDiscount(Short.valueOf((short) row.getCell(5).getNumericCellValue()));
				item.setStock(row.getCell(6).getBooleanCellValue());

				item.setItemType(type);
				item.setVersion((short) 0);
				item.setEnable(true);
				if (row.getCell(10) != null) {
					item.setSub(row.getCell(10).getStringCellValue());
				} else if (row.getCell(10) == null) {
					item.setSub("");
				}
				item.setCosting(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
				item.setWholePrice(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
				itemRepository.save(item);
				ItemBranch itemBranch = createItemBranch(item);
				if (itemBranch.getType().equalsIgnoreCase("MAINITEM")) {
					mainItem.add(itemBranch);
				}
				if (itemBranch.getType().equalsIgnoreCase("SUBITEM")) {
					subItem.add(itemBranch);
				}
				listItemBranch.add(itemBranch);
				listItem.add(item);

			}
			entityManager.flush();
			entityManager.clear();
			// Add Sub
			for (int k = 0; k < mainItem.size(); k++) {
				if (!mainItem.get(k).getItem().getSub().isBlank()) {
					code.clear();
					String str = mainItem.get(k).getItem().getSub();
					String[] arrStr = str.split("/");
					if (arrStr.length >= 1) {
						for (int i = 0; i < arrStr.length; i++) {
							for (int j = 0; j < subItem.size(); j++) {
								if (arrStr[i].equalsIgnoreCase(subItem.get(j).getCode())) {
									code.add(subItem.get(j).getId());
									mainItem.get(k).setAddOnItems(code);
									itemBranchRepository.save(mainItem.get(k));
								}
							}
						}
					}
				} else {
					for (int j = 0; j < subItem.size(); j++) {
						if (mainItem.get(k).getItem().getSub().equalsIgnoreCase(subItem.get(j).getCode())) {
							code.add(subItem.get(j).getId());
							mainItem.get(k).setAddOnItems(code);
							itemBranchRepository.save(mainItem.get(k));
						}
					}
				}
			}
			workbook.close();
			return listItemBranch.size();
		} catch (Exception e) {
			workbook.close();
			System.out.println(e.getMessage());
			throw new BadRequestException(e.getMessage(), "not success!");
			// TODO: handle exception
		}
	}

//	List<Item> listupdate = new ArrayList<>();
//	@Transactional
//	public List<Item> updatexcel(@RequestParam("file") MultipartFile file) {
//		subItemup.clear();
//		mainItemup.clear();
//		try {
//			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
//				Item item = new Item();
//				XSSFRow row = sheet.getRow(i + 1);
//				item.setCode(row.getCell(0).getStringCellValue());
//				item.setType(row.getCell(1).getStringCellValue());
//				item.setName(row.getCell(2).getStringCellValue());
//				item.setNameKh(row.getCell(3).getStringCellValue());
//				item.setPrice(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
//				item.setDiscount(Short.valueOf((short) row.getCell(5).getNumericCellValue()));
//				item.setStock(row.getCell(6).getBooleanCellValue());
//				ItemType type = new ItemType();
//				type = itemTypeRepository.findById((int) row.getCell(7).getNumericCellValue()).orElse(null);
//				item.setItemType(type);
//				item.setVersion(Short.valueOf((short) row.getCell(8).getNumericCellValue()));
//				item.setEnable(row.getCell(9).getBooleanCellValue());
//				if (row.getCell(10) != null) {
//					item.setSub(row.getCell(10).getStringCellValue());
//				} else if (row.getCell(10) == null) {
//					item.setSub("");
//				}
//				item.setCosting(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()));
//				item.setWholePrice(BigDecimal.valueOf(row.getCell(12).getNumericCellValue()));
//				Optional<Item> find = itemRepository.findByCode(item.getCode());
//				if (find.isPresent()) {
//					itemRepository.findByCode(find.get().getCode()).map(itemupdate -> {
//						itemupdate.setPrice(item.getPrice());
//						itemupdate.setName(item.getName());
//						itemupdate.setEnable(item.isEnable());
//						itemupdate.setNameKh(item.getNameKh());
//						itemupdate.setDiscount(item.getDiscount());
//						itemupdate.setSub(item.getSub());
//						itemupdate.setCosting(item.getCosting());
//						itemupdate.setWholePrice(item.getWholePrice());
//						listupdate.add(itemupdate);
//						return itemRepository.save(itemupdate);
//					}).orElseThrow(() -> new ResourceNotFoundException("no value in database"));
//					updatexcelbranch(item);
//				} else {
//					itemRepository.save(item);
//					createItemBranch(item);
//				}
//			}
//			for (int k = 0; k < mainItemup.size(); k++) {
//				if (!mainItemup.get(k).getItem().getSub().isBlank()) {
//					update.clear();
//					String strr = mainItemup.get(k).getItem().getSub();
//					String[] arrStrr = strr.split("/");
//					for (int i = 0; i < arrStrr.length; i++) {
//						Optional<ItemBranch> item5 = itemBranchRepository.findByitemCode(arrStrr[i]);
//						if (arrStrr.length >= 1) {
//							if (item5.isPresent()) {
//								if (arrStrr[i].equalsIgnoreCase(item5.get().getCode())) {
//									update.add(item5.get().getId());
//									mainItemup.get(k).setAddOnItems(update);
//									itemBranchRepository.save(mainItemup.get(k));
//								}
//							} else {
//								System.out.println("Check database first atleast one updated"+","+" No SUBITEM.Code match with MAINTIEM.Sub"+" or Database doesn't have that SUBITEM");
//							}
//						}
//					}
//				} else {
//					for (int j = 0; j < mainItemup.size(); j++) {
//						if (mainItemup.get(k).getItem().getSub().isBlank()) {
//							mainItemup.get(k).setAddOnItems(new ArrayList<>());
//							itemBranchRepository.save(mainItemup.get(k));
//						}
//					}
//				}
//			}
//			workbook.close();
//			return listupdate;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw new BadRequestException(e.getMessage(), "Item update not success!");
//			// TODO: handle exception
//		}
//	}
//	List<ItemBranch> mainItemup = new ArrayList<>();
//	List<ItemBranch> subItemup = new ArrayList<>();
//	List<ItemBranch> listBranchupdate = new ArrayList<>();
//	List<Long> update = new ArrayList<>();
//	@Transactional
//	private List<ItemBranch> updatexcelbranch(Item item) {
//		try {
//			itemBranchRepository.findByitemCode(item.getCode()).map(itemBranchup -> {
//				itemBranchup.setStockIn((long) 0);
//				itemBranchup.setStockOut((long) 0);
//				itemBranchup.setEnable(item.isEnable());
//				itemBranchup.setDiscount(item.getDiscount());
//				itemBranchup.setPrice(item.getPrice());
//				itemBranchup.setUseItemConfiguration(true);
//				itemBranchup.setAddOnItems(new ArrayList<>());
//				itemBranchup.setBranch(userProfile.getProfile().getBranch());
//				itemBranchup.setCosting(item.getCosting());
//				itemBranchup.setWholePrice(item.getWholePrice());
//				entityManager.flush();
//				entityManager.clear();
//				if (itemBranchup.getType().equalsIgnoreCase("MAINITEM")) {
//					mainItemup.add(itemBranchup);
//
//				}
//				if (itemBranchup.getType().equalsIgnoreCase("SUBITEM")) {
//					subItemup.add(itemBranchup);
//
//				}
//				listBranchupdate.add(itemBranchup);
//				return itemBranchRepository.save(itemBranchup);
//			}).orElseThrow(() -> new ResourceNotFoundException("no value in database"));
//			return listBranchupdate;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			throw new BadRequestException(e.getMessage(), "12");
//			// TODO: handle exception
//		}
//	}
	@Transactional
	public Item createCredit(CreditView creditView) {
		ItemType itemType = itemTypeRepository
				.findByBranchIdandCreditTrue(userProfile.getProfile().getBranch().getCorporate().getId());
		Item item = new Item();
		item.setCode("0");
		item.setType("CREDIT");
		item.setName(creditView.getName());
		item.setNameKh(creditView.getName());
		item.setPrice(creditView.getPrice());
		item.setDiscount((short) 0);
		item.setStock(false);
		item.setItemType(itemType);
		item.setCosting(BigDecimal.valueOf(0));
		item.setWholePrice(BigDecimal.valueOf(0));
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
			itemBr.setStockIn(BigDecimal.valueOf(0));
			itemBr.setStockOut(BigDecimal.valueOf(0));
			itemBr.setCosting(BigDecimal.valueOf(0));
			itemBr.setWholePrice(BigDecimal.valueOf(0));
			itemBr.setPrice(item.getPrice());
			itemBr.setDiscount(item.getDiscount());
			itemBr.setInvenQty(BigDecimal.valueOf(0));
			itemBr.setPoint(creditView.getPoint());
			itemBr.setReward((short) 0);
			itemBr.setAddPercent((short) 0);
			itemBranchRepository.save(itemBr);

//	     else {
//	      itemBranch.get().setPrice(item.getPrice());
//	      itemBranch.get().setDiscount(item.getDiscount());
//	      itemBranchRepository.save(itemBranch.get());
//	      
//	     }
		});
		return item;
	}
	@Transactional
	 public ItemBranch updateCredit(Long itemId, String name) {
	  return itemRepository.findById(itemId).map((item) -> {
	   if (userProfile.getProfile().getCorporate().getId() != item.getItemType().getCorporate().getId()) {
	    throw new UnauthorizedException("Item is unauthorized");
	   }
	   item.setName(name);
	   item.setNameKh(name);
	   itemRepository.save(item);
	   return itemBranchRepository
	     .findFirstByBranchIdAndItemIdOrderByIdDesc(userProfile.getProfile().getBranch().getId(), itemId)
	     .orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	  }).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	 }
}
