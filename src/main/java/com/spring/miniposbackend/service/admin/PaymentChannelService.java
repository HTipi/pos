package com.spring.miniposbackend.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.miniposbackend.exception.BadRequestException;
import com.spring.miniposbackend.exception.ResourceNotFoundException;
import com.spring.miniposbackend.model.admin.BranchPaymentChannel;
import com.spring.miniposbackend.model.admin.BranchPaymentIdentity;
import com.spring.miniposbackend.model.admin.PaymentChannel;
import com.spring.miniposbackend.modelview.PaymentChannelView;
import com.spring.miniposbackend.repository.admin.BranchPaymentChannelRepository;
import com.spring.miniposbackend.repository.admin.PaymentChannelRepository;
import com.spring.miniposbackend.util.UserProfileUtil;

@Service
public class PaymentChannelService {

	@Autowired
	private PaymentChannelRepository paymentChannelRepository;
	@Autowired
	private BranchPaymentChannelRepository branchChannelRepository;
	@Autowired
	private UserProfileUtil userProfile;

	public List<PaymentChannel> showAll() {
		return paymentChannelRepository.findAll();
	}

	public PaymentChannel showById(Integer paymentChannelId) {
		return paymentChannelRepository.findById(paymentChannelId)
				.orElseThrow(() -> new ResourceNotFoundException("This payment channel dosen't exit"));
	}

	public PaymentChannel create(String name, String nameKh) {

		if (paymentChannelRepository.findByName(name) == true) {
			throw new BadRequestException("this payment channel is already exit");
		}
		PaymentChannel payment = new PaymentChannel();
		payment.setName(name);
		payment.setNameKh(nameKh);
		return paymentChannelRepository.save(payment);
	}

	public void delete(Integer paymentChannelId) {
		paymentChannelRepository.deleteById(paymentChannelId);
	}

	public List<PaymentChannelView> showAllChannel() {
		PaymentChannelView paymentView = null;
		List<PaymentChannelView> list = new ArrayList<>();
		List<PaymentChannel> allchannel = paymentChannelRepository.findAll();
		for(int i=0; i<allchannel.size(); i++) {
			BranchPaymentChannel branchChannel = branchChannelRepository.findByBranchIdandPaymentChannelId(userProfile.getProfile().getBranch().getId(),allchannel.get(i).getId());
				paymentView = new PaymentChannelView();	
				if(branchChannel != null) {
					paymentView.setPaymentChannelId(branchChannel.getPaymentChannelId());
					paymentView.setPaymentChannel(branchChannel.getPaymentChannelName());
					paymentView.setShow(branchChannel.isShow());
					paymentView.setPercentage(branchChannel.getPercentage());
					paymentView.setEnable(true);	
					paymentView.setSort(branchChannel.getSort());
				}else {
					paymentView.setPaymentChannelId(allchannel.get(i).getId());
					paymentView.setPaymentChannel(allchannel.get(i).getName());
					paymentView.setShow(false);
					paymentView.setPercentage(0);
					paymentView.setEnable(false);	
					paymentView.setSort(0);
				}
				list.add(paymentView);	
			}
		return list;
	}
	
	
	
}
