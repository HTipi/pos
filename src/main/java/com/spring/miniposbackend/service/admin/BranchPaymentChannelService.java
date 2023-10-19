package com.spring.miniposbackend.service.admin;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ConflictException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.exception.UnauthorizedException;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchPaymentIdentity;
import com.spring.miniposbackend.model.admin.PaymentChannel;
import com.spring.miniposbackend.modelview.BranchPaymentRequest;
import com.spring.miniposbackend.modelview.PaymentChannelView;
import com.spring.miniposbackend.repository.admin.BranchPaymentChannelRepository;
import com.spring.miniposbackend.repository.admin.BranchRepository;
import com.spring.miniposbackend.repository.admin.PaymentChannelRepository;
import com.spring.miniposbackend.util.ImageUtil;
import com.spring.miniposbackend.util.UserProfileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BranchPaymentChannelService {

    @Autowired
    private BranchPaymentChannelRepository branchPaymentChannelRepository;
    @Autowired
    private PaymentChannelRepository paymentChannelRepository;
	  @Autowired
	  private UserProfileUtil userProfile;
	  @Autowired
	  private ImageUtil imageUtil;
	  @Value("${file.path.image.branch}")
	  private String imagePath;
	@Autowired
	private BranchRepository branchRepository;
	
	public List<BranchPaymentChannel> showAllChannels(Integer branchId) {
		return branchPaymentChannelRepository.findByBranchId(branchId);
	}
	public BranchPaymentChannel create(BranchPaymentRequest branchPaymentRequest) {
		Optional<PaymentChannel> payment = paymentChannelRepository.findById(branchPaymentRequest.getPaymentChannelId());
			if(!payment.isPresent() || payment == null) throw new BadRequestException("this payment channel doesn't exit");
			
		BranchPaymentIdentity branchPaymentIdentity = new BranchPaymentIdentity();
		branchPaymentIdentity.setBranch(userProfile.getProfile().getBranch());
		branchPaymentIdentity.setChannel(payment.get());
		if(branchPaymentChannelRepository.findById(branchPaymentIdentity).isPresent()) throw new BadRequestException("this channel already exit in this branch");
		BranchPaymentChannel branchpayment = new BranchPaymentChannel();
		branchpayment.setBranchPaymentIdentity(branchPaymentIdentity);
		branchpayment.setPercentage(branchPaymentRequest.getPercentage());
		branchpayment.setShow(branchPaymentRequest.getShow());
		branchPaymentChannelRepository.save(branchpayment);
		return branchpayment;
	}
	
	public BranchPaymentChannel update(Integer paymentChannelId, double percentage, Boolean show) {
		
		Optional<PaymentChannel> payment = paymentChannelRepository.findById(paymentChannelId);
		if(!payment.isPresent() || payment == null) throw new BadRequestException("this payment channel doesn't exit");
		
		BranchPaymentIdentity branchPaymentIdentity = new BranchPaymentIdentity();
		branchPaymentIdentity.setBranch(userProfile.getProfile().getBranch());
		branchPaymentIdentity.setChannel(payment.get());
		if(!branchPaymentChannelRepository.findById(branchPaymentIdentity).isPresent()) throw new BadRequestException("this channel doesn't exit in this branch yet");
		Optional<BranchPaymentChannel> bp = branchPaymentChannelRepository.findById(branchPaymentIdentity);

			bp.get().setPercentage(percentage);
			bp.get().setShow(show);
			
			branchPaymentChannelRepository.save(bp.get());
	
		return bp.get();
	}
	
	@Transactional
	public List<PaymentChannelView> deleteAndCreate(List<PaymentChannelView> paymentChannelView) {
		BranchPaymentChannel channel = null; 
		branchPaymentChannelRepository.deleteByBranchId(userProfile.getProfile().getBranch().getId());
		for(int i=0; i<paymentChannelView.size(); i++) {
			channel = new BranchPaymentChannel();
				if(paymentChannelView.get(i).isEnable()) {
					channel.setPercentage(paymentChannelView.get(i).getPercentage());
					if(paymentChannelView.get(i).getPercentage() > 0) {
						channel.setShow(!paymentChannelView.get(i).isShow());
					}else {
						 channel.setShow(paymentChannelView.get(i).isShow());
					}
					PaymentChannel allchannel = paymentChannelRepository.findById(paymentChannelView.get(i).getPaymentChannelId())
							.orElseThrow(()-> new ResourceNotFoundException("This Channel doesn't exit","01"));
					BranchPaymentIdentity branchPaymentIdentity = new BranchPaymentIdentity();
					branchPaymentIdentity.setBranch(userProfile.getProfile().getBranch());
					branchPaymentIdentity.setChannel(allchannel);
					channel.setBranchPaymentIdentity(branchPaymentIdentity);
						
					channel.setSort(paymentChannelView.get(i).getSort());
					branchPaymentChannelRepository.save(channel);
				}
			}
		return paymentChannelView;
	}
	
	 public BranchPaymentChannel uploadQr(int branchId, MultipartFile file,int paymentChannelId) {
			BranchPaymentChannel branchChannel = branchPaymentChannelRepository.findByBranchIdandPaymentChannelId(branchId,paymentChannelId);
				if (file.isEmpty()) {
					throw new ResourceNotFoundException("File content does not exist");
				}
				if(branchChannel.getBranch().getCorporate().getId() != userProfile.getProfile().getCorporate().getId()) {
					throw new UnauthorizedException("Corporate is unauthorized");
				}
				try {
					// read and write the file to the selected location-
					//String baseLocation = String.format("%s/"+imagePath, System.getProperty("catalina.base"));
					String baseLocation = imagePath;
					String fileName = imageUtil.uploadImage(baseLocation, branchChannel.getBranch().getName(), file);
					branchChannel.setQr(fileName);
					return branchPaymentChannelRepository.save(branchChannel);
				} catch (IOException e) {
					throw new ConflictException("Upable to upload File");

				} catch (Exception e) {
					throw new ConflictException("Exception :"+e.getMessage());
				}
		}
	 
	    
	 public byte[] getFileData(Integer paymentChannelId) {
			BranchPaymentChannel channel = branchPaymentChannelRepository.findByBranchIdandPaymentChannelId(userProfile.getProfile().getBranch().getId(),paymentChannelId);
			if(channel.getQr() == null) {
				throw new ResourceNotFoundException("this chennal doesn't has Qr code","01");
			}
				try {
					return get(imagePath + "/" + channel.getQr());
				} catch (Exception e) {
					System.out.println(e.getMessage());
					throw new ConflictException(e.getMessage());
				}
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

}
