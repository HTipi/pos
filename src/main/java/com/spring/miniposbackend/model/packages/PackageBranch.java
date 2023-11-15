package com.spring.miniposbackend.model.packages;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.sale.Sale;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="package_branches")
public class PackageBranch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "quantity", nullable = false)
	private double qty;
	
	@Column(name = "expiry_date", nullable = true)
	private Date expiryDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_branch_id", nullable = false)
	@JsonIgnore
	private ItemBranch itemBranch;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", nullable = false)
	@JsonIgnore
	private ItemBranch packages;
	
	@Column(name = "reverse", nullable = false)
	private boolean reverse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	@JsonIgnore
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id", nullable = false)
	@JsonIgnore
	private Sale sale;

}
