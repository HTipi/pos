package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.modelview.ImageResponse;
import com.spring.miniposbackend.repository.admin.BranchRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;
//    @Autowired
//    private CorporateRepository corporateRepository;
//    @Autowired
//    private AddressRepository addressRepository;
    
	@Value("${file.path.image.branch}")
	private String imagePath;
    
    public Branch uploadImage(Integer branchId, MultipartFile file) {
		return branchRepository.findById(branchId).map(branch -> {
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
				String newFileName = branch.getId() + fileName.substring(fileName.lastIndexOf("."));
				Path path = Paths.get(baseLocation + "/" + newFileName);
				Files.write(path, file.getBytes());
				branch.setLogo(newFileName);
				return branchRepository.save(branch);
			} catch (IOException e) {
				throw new ConflictException("Upable to upload File");

			} catch (Exception e) {
				throw new ConflictException("Exception :"+e.getMessage());
			}
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}
    
    public ImageResponse getImage(Integer branchId) {
		return branchRepository.findById(branchId).map(branch -> {
			return getImage(branch);
		}).orElseThrow(() -> new ResourceNotFoundException("Item type does not exist"));
	}
    
	public ImageResponse getImage(Branch branch) {
		if(branch.getLogo().isEmpty()) {
			return new ImageResponse(branch.getId().longValue(), null, null);
		}
		try {
			String fileLocation = String.format("%s/"+imagePath, System.getProperty("catalina.base"))+ "/"
					+ branch.getLogo();
			File file = new File(fileLocation);
			byte[] bArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
			return new ImageResponse(branch.getId().longValue(), bArray, null);

		} catch (Exception e) {
			return new ImageResponse(branch.getId().longValue(), null, null);
		}
	}
    
//    public Branch create(Integer corporateId, Integer addressId, Branch branchRequest) {
//
//        if (!this.corporateRepository.existsById(corporateId))
//            throw new ResourceNotFoundException("The Corporate is not found!" + corporateId);
//
//        if (!this.addressRepository.existsById(addressId))
//            throw new ResourceNotFoundException("The Address is not found!" + addressId);
//
//        return this.corporateRepository.findById(corporateId)
//                .map(corporate -> {
//                    return this.addressRepository.findById(addressId)
//                            .map(address -> {
//                                branchRequest.setCorporate(corporate);
//                                branchRequest.setAddress(address);
//                                return this.branchRepository.save(branchRequest);
//                            }).orElseThrow(() -> new ResourceNotFoundException("Address not found" + addressId));
//                }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//    }
//
//    public Branch update(Integer branchId ,Integer corporateId, Integer addressId, Branch branchRequest) {
//
//        if (!this.corporateRepository.existsById(corporateId))
//            throw new ResourceNotFoundException("The Corporate is not found!" + corporateId);
//
//        if (!this.addressRepository.existsById(addressId))
//            throw new ResourceNotFoundException("The Address is not found!" + addressId);
//
//        if(!this.branchRepository.existsById(branchId))
//            throw new ResourceNotFoundException("The Branch is not found!" + branchId);
//
//        return this.corporateRepository.findById(corporateId).map(corporateData -> {
//
//            return this.addressRepository.findById(addressId).map(addressData -> {
//
//                return this.branchRepository.findById(branchId)
//                        .map(branch -> {
//
//                            branch.setCorporate(corporateData);
//                            branch.setAddress(addressData);
//                            branch.setName(branchRequest.getName());
//                            branch.setNameKh(branchRequest.getNameKh());
//                            branch.setTelephone(branchRequest.getTelephone());
//                            branch.setMain(branchRequest.isMain());
//                            return this.branchRepository.save(branchRequest);
//
//                        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//            }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//        }).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
//
//    }
//
//    @Transactional(readOnly = true)
//    public Branch show(int id) {
//        return this.branchRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Branch not found" + id));
//    }
//
//    @Transactional(readOnly = true)
//    public List<Branch> shows() {
//        return this.branchRepository.findAll();
//    }
//
//    public Branch enable(int branchId) {
//
//        return this.branchRepository.findById(branchId)
//                .map(branch -> {
//                    branch.setEnable(true);
//                    return this.branchRepository.save(branch);
//                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<Branch> showAllActive() {
//        return this.branchRepository.findAllActive();
//    }
//
//    public Branch disable(int branchId) {
//
//        return this.branchRepository.findById(branchId)
//                .map(branch -> {
//                    branch.setEnable(false);
//                    return this.branchRepository.save(branch);
//                }).orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + branchId));
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<Branch> showAllActiveMainBranch(){
//        return this.branchRepository.findAllActiveMainBranch();
//    }
//
//    public Branch updateStatus(Integer branchId, Boolean status) {
//
//        return this.branchRepository.findById(branchId)
//                .map(branch -> {
//                    branch.setEnable(status);
//                    return this.branchRepository.save(branch);
//                }).orElseThrow(() -> new ResourceNotFoundException("Branch does not exist"));
//
//    }
//
}
