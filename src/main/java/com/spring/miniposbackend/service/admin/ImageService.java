package com.spring.miniposbackend.service.admin;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Image;
import com.spring.miniposbackend.repository.admin.ImageRepository;
import com.spring.miniposbackend.util.ImageUtil;

@Service
public class ImageService {

	@Autowired
	private ImageUtil imageUtil;
	@Autowired
	private ImageRepository imageRepository;
	
	@Value("${file.path.image.item}")
	private String itemPath;
	@Value("${file.path.image.item-type}")
	private String itemTypePath;
	
	public Image create(String type,MultipartFile file) {
		if (file.isEmpty()) {
			throw new ResourceNotFoundException("File content does not exist");
		}
		String imagePath ="";
		if(type.compareToIgnoreCase("item")==0) {
			imagePath=itemPath;
		}else {
			imagePath=itemTypePath;
		}
		try {
			// read and write the file to the selected location-
			//String baseLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base"));
			String baseLocation = imagePath;
			UUID id = UUID.randomUUID();
			String fileName = imageUtil.uploadImage(baseLocation, id.toString(), file);
			Image image  =new Image();
			image.setId(id);
			image.setName(fileName);
			image.setType(type);
			return imageRepository.save(image);
		} catch (IOException e) {
			throw new ConflictException("Upable to upload File");

		} catch (Exception e) {
			throw new ConflictException(e.getMessage());
		}
	}
	
	public Image update(UUID imageId, MultipartFile file) {
		if (file.isEmpty()) {
			throw new ResourceNotFoundException("File content does not exist");
		}
		Image image = imageRepository.findById(imageId)
				.orElseThrow(() -> new ResourceNotFoundException("Image does not exist"));
		String imagePath ="";
		if(image.getType().compareToIgnoreCase("item")==0) {
			imagePath=itemPath;
		}else {
			imagePath=itemTypePath;
		}
		try {
			// read and write the file to the selected location-
			//String baseLocation = String.format("%s/" + imagePath, System.getProperty("catalina.base"));
			String baseLocation = imagePath;
			String fileName = imageUtil.uploadImage(baseLocation, image.getName().substring(0, image.getName().lastIndexOf(".")-1), file);
			image.setName(fileName);
			return imageRepository.save(image);
		} catch (IOException e) {
			throw new ConflictException("Upable to upload File");

		} catch (Exception e) {
			throw new ConflictException(e.getMessage());
		}
	}
	
	public Page<Image> getImages(String type, int page, int length){
		Pageable pageable = PageRequest.of(page, length);
		Page<Image> images = imageRepository.findByType(type, pageable);
		return images;
	}
	
	
	
}
