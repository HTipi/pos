package com.spring.miniposbackend.model.admin;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "branch_payment_channels")
@Getter
@Setter
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

	public Integer getPaymentChannelId() {
		return getPaymentChannel().getId();
	}

	public String getPaymentChannelName() {
		return getPaymentChannel().getName();
	}

	@Column(name = "show", nullable = true)
	private boolean show;
	@Column(name = "percentage", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short percentage;

}
