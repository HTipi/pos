package com.spring.miniposbackend.service.admin;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.ItemType;
import com.spring.miniposbackend.repository.admin.CorporateRepository;
import com.spring.miniposbackend.repository.admin.ItemTypeRepository;

@Service
public class ItemTypeService {

	@Autowired
	private ItemTypeRepository itemTypeRepository;
	@Autowired
	private CorporateRepository corporateRepository;

	@Value("${file.path.image.item-type}")
	private String imagePath;

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

	public boolean uploadImage(Integer itemTypeId, MultipartFile file) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			if (file.isEmpty()) {
				throw new ResourceNotFoundException("File content does not exist");
			}
			try {
				// read and write the file to the selected location-
				String baseLocation = Paths.get("").toAbsolutePath().toString() + "/" + imagePath + "/"
						+ itemType.getCorporate().getId();
				File directory = new File(baseLocation);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				String fileName = file.getOriginalFilename();
				String newFileName = itemType.getId() + fileName.substring(fileName.lastIndexOf("."));
				Path path = Paths.get(baseLocation + "/" + newFileName);
				Files.write(path, file.getBytes());
				itemType.setImage(newFileName);
				itemTypeRepository.save(itemType);
				return true;
			} catch (IOException e) {
				throw new ConflictException("Upable to upload File");
			} catch (Exception e) {
				throw new ConflictException(e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

	public byte[] getImage(Integer itemTypeId) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			try {
				String fileLocation = Paths.get("").toAbsolutePath().toString() + "/" + imagePath + "/"
						+ itemType.getCorporate().getId() + "/" + itemType.getImage();
				File file = new File(fileLocation);
				byte[] bArray = new byte[(int) file.length()];
				FileInputStream fis = new FileInputStream(file);
				fis.read(bArray);
	            fis.close();
	            return bArray;
				
			} catch (Exception e) {
				throw new ConflictException("Upable to upload File");
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

	public byte[] getImage1(Integer itemTypeId) {
		return itemTypeRepository.findById(itemTypeId).map(itemType -> {
			String baseLocation = Paths.get("").toAbsolutePath().toString() + "/" + imagePath + "/"
					+ itemType.getCorporate().getId();
			try {
				BufferedImage bufferedImage = ImageIO.read(new File(baseLocation + "/" + itemType.getImage()));
				WritableRaster raster = bufferedImage.getRaster();
				DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
				return data.getData();
			} catch (IOException e) {
				throw new ConflictException("Upable to upload File");
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}

//	public ItemType create(ItemType itemType) {
//		return itemTypeRepository.save(itemType);
//	}
//	
//	public ItemType update(Integer itemTypeId,ItemType requestItemType) {
//		return itemTypeRepository.findById(itemTypeId)
//				.map(itemType -> {
//					itemType.setName(requestItemType.getName());
//					itemType.setNameKh(requestItemType.getNameKh());
//					itemType.setImage(requestItemType.getImage());
//					itemType.setEnable(requestItemType.isEnable());
//					return itemTypeRepository.save(itemType);
//				}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
//	}
//	
//	public ItemType setEnable(Integer itemTypeId,boolean enable) {
//        return  itemTypeRepository.findById(itemTypeId)
//				.map(itemType -> {
//					itemType.setEnable(enable);
//					return itemTypeRepository.save(itemType);
//				}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
//    }
}
