package com.spring.miniposbackend.model.packages;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.sale.SaleDetail;

import lombok.Getter;
import lombok.Setter;

@Table(name = "package_sales")
@Entity
@Setter
@Getter
public class PackageSale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_detail_id", nullable = false)
	@JsonIgnore
	private SaleDetail saleDetail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = true)
	@JsonIgnore
	private Account account;

	@Column(name = "reverse", nullable = false)
	private boolean reverse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_branch_id", nullable = true)
	@JsonIgnore
	private PackageBranch packageBranch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_corporate_id", nullable = true)
	@JsonIgnore
	private PackageCorporate packageCorporate;

	@Column(name = "qr_num", nullable = true)
	private UUID qrNumber;

	public double getQty() {
		return saleDetail.getQuantity();
	}

	public String getItemName() {
		return saleDetail.getItemName();
	}

	public String getItemNameKh() {
		return saleDetail.getItemNameKh();
	}
	
	public double getPrice() {
		return saleDetail.getPrice().doubleValue();
	}
	public Date getEndDate() {
		return saleDetail.getValueDate();
	}

}
