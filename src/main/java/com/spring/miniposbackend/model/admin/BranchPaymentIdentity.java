package com.spring.miniposbackend.model.admin;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BranchPaymentIdentity implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "payment_channel_id", nullable = false)
	@JsonIgnore
	private PaymentChannel channel;

	public BranchPaymentIdentity() {
		branch = null;
		channel = null;
	}

	public BranchPaymentIdentity(Branch branch, PaymentChannel channel) {
		this.branch = branch;
		this.channel = channel;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BranchPaymentIdentity)) return false;
        BranchPaymentIdentity that = (BranchPaymentIdentity) obj;
        return Objects.equals(getBranch(), that.getBranch()) &&
                Objects.equals(getChannel(), that.getChannel());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getBranch(), getChannel());
    }
}
