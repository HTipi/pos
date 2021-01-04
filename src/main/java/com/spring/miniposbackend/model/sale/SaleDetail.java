package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sale_details")
@Setter
@Getter
@DynamicUpdate
public class SaleDetail extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "value_date", nullable = false)
	private Date valueDate;

	@Column(name = "price", nullable = false, length = 10, precision = 2)
	private BigDecimal price;

	@Column(name = "quantity", nullable = false)
	private Short quantity;

	@Column(name = "discount", nullable = false, length = 10, precision = 2)
	private Short discount;

	@Column(name = "discount_amt", nullable = false, length = 10, precision = 2)
	private Double discountAmount;

	@Column(name = "total", nullable = false, length = 10, precision = 2)
	private Double total;

	@Column(name = "reverse", nullable = false)
	@ColumnDefault("false")
	private boolean reverse;

	@Column(name = "reverse_date")
	private Date reverseDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sale_id", nullable = false)
	@JsonIgnore
	private Sale sale;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_branch_id", nullable = false)
	@JsonIgnore
	private ItemBranch itemBranch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_sale_id", nullable = true)
	@JsonIgnore
	private SaleDetail parentSaleDetail;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_sale_id", nullable = true)
	private List<SaleDetail> addOns;

	public String getItemTypeName() {
		return itemBranch.getItemTypeName();
	}

	public Long getItemId() {
		return itemBranch.getId();
	}

	public String getItemTypeNameKh() {
		return itemBranch.getItemTypeNameKh();
	}

	public String getItemCode() {
		return itemBranch.getCode();
	}

	public String getItemName() {
		return itemBranch.getName();
	}

	public String getItemNameKh() {
		return itemBranch.getNameKh();
	}

	public String getReceiptNumber() {
		return sale.getReceiptNumber();
	}

	public boolean getReverse() {
		return sale.isReverse();
	}

	public Date getReverseDate() {
		return sale.getReverseDate();
	}

	public String getSeatName() {
		return sale.getSeatName();
	}

	public String getBranchName() {
		return branch.getNameKh();
	}

	public String getUserName() {
		return user.getUsername();
	}

	public Double getCashIn() {
		return sale.getCashIn();
	}

	public Double getDiscountTotal() {
		return sale.getDiscountTotal();
	}

	public Double getChange() {
		return sale.getChange();
	}

	public BigDecimal getRate() {
		return sale.getBranchCurrency().getRate();
	}
	public String getCurrencyCode() {
		return sale.getBranchCurrency().getCode();
	}
	public Double getGrandTotal() {
		return sale.getTotal();
	}
}
