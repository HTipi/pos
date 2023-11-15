package com.spring.miniposbackend.modelview;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.Branch;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BranchImageView {

	@JsonIgnore
	private Branch branch;
	
	private byte[] logo;
	private int id;
	private String name;
	private String nameKh;
	private String qr;
	private String telephone;
	private String addressDesc;
	private Boolean enable;
	private Boolean main;
	private BigDecimal pointExchange;
	private BigDecimal rewardExchange;
	
	public BranchImageView(Branch branch, byte[] logo) {
		super();
		this.branch = branch;
		this.logo = logo;
		this.id = branch.getId();
		this.name = branch.getName();
		this.nameKh = branch.getNameKh();
		this.qr = branch.getQr();
		this.telephone = branch.getTelephone();
		this.addressDesc = branch.getAddressDesc();
		this.enable = branch.isEnable();
		this.main = branch.isMain();
		this.pointExchange = branch.getPointExchange();
		this.rewardExchange = branch.getRewardExchange();
	}
	
	
	
	
	
}
