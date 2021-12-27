package com.spring.miniposbackend.model.admin;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "branch_payment_channels")
@Getter @Setter
public class BranchPaymentChannel {
	
	@EmbeddedId
	@JsonIgnore
	private BranchPaymentIdentity branchPaymentIdentity;
	
	@JsonIgnore
	public Branch getBranch() {
		return branchPaymentIdentity.getBranch();
	}
	
	@JsonIgnore
	public PaymentChannel getPaymentChannel() {
		return branchPaymentIdentity.getChannel();
	}
	
	public String getPaymentChannelName() {
		return getPaymentChannel().getName();
	}
	
}
