package com.spring.miniposbackend.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUtil {

	public String uploadImage(String baseLocation, String newName, MultipartFile file) throws IOException {
		System.out.println(baseLocation + "/" + file.getOriginalFilename());
		// read and write the file to the selected location-
		File directory = new File(baseLocation);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		System.out.println(baseLocation + "/" + file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		String newFileName = newName + fileName.substring(fileName.lastIndexOf("."));
		System.out.println(baseLocation + "/" + newFileName);
		Path path = Paths.get(baseLocation + "/" + newFileName);
		Files.write(path, file.getBytes());
		return newFileName;
	}

	public byte[] getImage(String fileLocation) throws IOException {
		if (fileLocation.isEmpty()) {
			return null;
		}
		File file = new File(fileLocation);
		byte[] bArray = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		fis.read(bArray);
		fis.close();
		return bArray;
	}
}
