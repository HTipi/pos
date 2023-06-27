package com.spring.miniposbackend.service.admin;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;
import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.BranchAdvertise;
import com.spring.miniposbackend.modelview.BranchAdvertiseView;
import com.spring.miniposbackend.repository.admin.BranchAdveriseRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class BranchAdvertiseService {

	@Autowired
	private UserProfileUtil userProfile;
	
	@Autowired
	private ImageUtil imageUtil;
	
	@Autowired
	private BranchAdveriseRepository branchAdvertiseRepository;
	

	
	@Value("${file.path.image.advertisement}")
	private String logoAdvertise;
	@Transactional
	public BranchAdvertise create(BranchAdvertiseView branchAdvertiseView) throws Exception {
		List<BranchAdvertise> branchAdvertise = branchAdvertiseRepository
				.findByBranchIds(userProfile.getProfile().getBranch().getId());
		BranchAdvertise ba = null;
		if (branchAdvertise.size() > 4) {
			throw new Exception("You can only post 5 photo of you're branch");
		} else {
			for (int i = 0; i < branchAdvertise.size(); i++) {
				if (!branchAdvertise.isEmpty()) {
					if (branchAdvertise.get(i).getSortOrder() == branchAdvertiseView.getSortOrder()) {
						throw new BadRequestException("ផ្ទួនលេខរៀង");
					}
				}
			}
			ba = new BranchAdvertise();
			ba.setEnable(!branchAdvertiseView.isEnable());
			ba.setBranch(userProfile.getProfile().getBranch());
			ba.setName(branchAdvertiseView.getName());
			ba.setSortOrder(branchAdvertiseView.getSortOrder());
			branchAdvertiseRepository.save(ba);
		}
		return ba;
	}
	
	@Transactional
	public BranchAdvertise update(int id,BranchAdvertiseView branchAdvertiseView) throws Exception {
		try {
			BranchAdvertise ba = branchAdvertiseRepository.findByIdandBranchId(id,
					userProfile.getProfile().getBranch().getId());
			if (ba == null) throw new ResourceAccessException("This advertise doesn't exit");
				if (ba.getSortOrder() != branchAdvertiseView.getSortOrder()) {
					ba.setSortOrder(branchAdvertiseView.getSortOrder());
					ba.setName(branchAdvertiseView.getName());
				} else {
					throw new Exception("ផ្ទួនលេខរៀង");
			}
			return branchAdvertiseRepository.save(ba);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<BranchAdvertise> get(Optional<Boolean> enable) {
		List<BranchAdvertise> ba;
		if(enable.get()== true) {
			ba = branchAdvertiseRepository.findByBranchIdandEnable(userProfile.getProfile().getBranch().getId(),enable.get());
		}else if(enable.get() == false) {
			ba = branchAdvertiseRepository.findByBranchIdandEnable(userProfile.getProfile().getBranch().getId(),enable.get());
		}else {
			ba = branchAdvertiseRepository.findByBranchIds(1);
		}		
		return ba;
	}
	public byte[] getFileData(Long branchAdvertiseId) {
		return  branchAdvertiseRepository.findById(branchAdvertiseId).map(branchAdvertise -> {
			try {
				return get(logoAdvertise + "/" + branchAdvertise.getId()+".png");
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
	
	public BranchAdvertise disable(Long id) {
		return branchAdvertiseRepository.findById(id).map((ba) -> {
			if (userProfile.getProfile().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
				throw new UnauthorizedException("Unauthorized");
			} 
			ba.setEnable(!ba.isEnable());
			return branchAdvertiseRepository.save(ba);
		}).orElseThrow(() -> new ResourceNotFoundException("Does not exist"));
	}
	
	public BranchAdvertise uploadimage(int id , MultipartFile file) throws Exception {
		BranchAdvertise branchAdvertise = branchAdvertiseRepository.findByIdandBranchId(id,userProfile.getProfile().getBranch().getId());
		if (branchAdvertise == null) throw new BadRequestException("this branch doesn't exit","01");
		String baseLocation = logoAdvertise;	
			String fileName = imageUtil.uploadImage(
					baseLocation, branchAdvertise.getId().toString(),file);
			branchAdvertise.setImage(fileName);
		return branchAdvertiseRepository.save(branchAdvertise);
	}
	
}
