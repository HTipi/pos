package com.spring.miniposbackend.service.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.repository.admin.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CorporateRepository corporateReposity;

	@Value("${file.path.image.item}")
	private String imagePath;

//	@Transactional(readOnly = true)
//    public List<Item> shows(){
//        return itemRepository.findAll();
//    }
//	
//    @Transactional(readOnly = true)
//    public List<Item> shows(boolean enable){
//        return itemRepository.findAllWithEnable(enable);
//    }

	public List<Item> showByCorpoateId(Integer corporateId, Optional<Boolean> enable) {
		return corporateReposity.findById(corporateId).map(corporate -> {
			if (!corporate.isEnable()) {
				throw new ConflictException("Corporate is disable");
			}
			if (enable.isPresent()) {
				return itemRepository.findByCorporateIdWithEnable(corporateId, enable.get());
			} else {
				return itemRepository.findByCorporateId(corporateId);
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Corporate does not exist"));

	}

	public Item uploadImage(Long itemId, MultipartFile file) {
		return itemRepository.findById(itemId).map(item -> {
			if (file.isEmpty()) {
				throw new ResourceNotFoundException("File content does not exist");
			}
			try {
				// read and write the file to the selected location-
				String baseLocation = String.format("%s/"+imagePath, System.getProperty("catalina.base"));
				File directory = new File(baseLocation);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				String fileName = file.getOriginalFilename();
				String newFileName = item.getId() + fileName.substring(fileName.lastIndexOf("."));
				Path path = Paths.get(baseLocation + "/" + newFileName);
				Files.write(path, file.getBytes());
				item.setImage(newFileName);
				item.setVersion((short) (item.getVersion() + 1));
				return itemRepository.save(item);
			} catch (IOException e) {
				throw new ConflictException("Upable to upload File");

			} catch (Exception e) {
				throw new ConflictException(e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
	}


//    @Transactional(readOnly = true)
//    public Item show(Integer itemId){
//        return itemRepository.findById(itemId)
//        		.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
//    }

//	@Transactional(readOnly = true)
//    public List<Item> showByItemTypeId(Integer itemTypeId, boolean enable){
//		return itemTypeRepository.findById(itemTypeId)
//				.map(itemType -> {
//					return itemRepository.findByItemTypeIdWithEnable(itemTypeId, enable);
//				})
//				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
//	}

//    @Transactional
//    public Item create(Integer itemTypeId,Item item) {
//        return itemTypeRepository.findById(itemTypeId)
//				.map(itemType ->{
//					item.setItemType(itemType);
//					try {
//						return itemRepository.save(item);
//					}catch (Exception e) {
//						throw new BadRequestException(e.getMessage());
//					}
//					
//				})
//				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
//    }

//    @Transactional
//    public Item update(Integer itemId, Integer itemTypeId,Item requestItem) {
//    	return itemTypeRepository.findById(itemTypeId)
//				.map(itemType ->{
//					return itemRepository.findById(itemId)
//							.map(item -> {
//								item.setItemType(itemType);
//								item.setCode(requestItem.getCode());
//								item.setName(requestItem.getName());
//								item.setNameKh(requestItem.getNameKh());
//								item.setPrice(requestItem.getPrice());
//								item.setDiscount(requestItem.getDiscount());
//								item.setEnable(requestItem.isEnable());
//								try {
//        							return itemRepository.save(item);
//        						}catch (Exception e) {
//									throw new BadRequestException(e.getMessage());
//								}
//							})
//							.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
//				
//				})
//				.orElseThrow(() -> new ResourceNotFoundException("Item Type does not exist"));
//    }

//    @Transactional
//    public Item setPrice(Integer itemId, BigDecimal price) {
//    	return itemRepository.findById(itemId)
//    			.map(item -> {
//    				item.setPrice(price);
//    				try {
//						return itemRepository.save(item);
//					}catch (Exception e) {
//						throw new BadRequestException(e.getMessage());
//					}
//    			})
//    			.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
//    }
//    
//    @Transactional
//    public Item setDiscount(Integer itemId, Short discount) {
//    	return itemRepository.findById(itemId)
//    			.map(item -> {
//    				item.setDiscount(discount);
//    				try {
//						return itemRepository.save(item);
//					}catch (Exception e) {
//						throw new BadRequestException(e.getMessage());
//					}
//    			})
//    			.orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
//    }
//    
//    @Transactional
//    public Item setEnable(Integer itemId,boolean enable) {
//        return  itemRepository.findById(itemId)
//				.map(item -> {
//					item.setEnable(enable);
//					try {
//						return itemRepository.save(item);
//					}catch (Exception e) {
//						throw new BadRequestException(e.getMessage());
//					}
//				}).orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
//    }

}
