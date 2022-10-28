package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "value_date", nullable = false)
	private Date valueDate;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "quantity", nullable = false)
	private Short quantity;

	@Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discountAmount;

	@Column(name = "discount_percentage", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short discountPercentage;

	@Column(name = "reverse", nullable = false)
	@ColumnDefault("false")
	private boolean reverse;

	@Column(name = "reverse_date")
	private Date reverseDate;

	@Column(name = "discount_total", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discountTotal;

	@Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal subTotal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sale_id", nullable = false)
	@JsonIgnore
	private Sale sale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
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
	
	public Integer getItemTypeId() {
		return itemBranch.getItem().getItemType_Id();
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

	public Long getItemCorpId() {
		return itemBranch.getItem_Id();
	}

	public double getTotal() {
		return subTotal.doubleValue() - discountTotal.doubleValue();
	}

	public double getCashIn() {
		return sale.getCashIn();
	}

	public double getChange() {
		return sale.getChange();
	}

	public BigDecimal getRate() {
		return sale.getBranchCurrency().getRate();
	}

	public String getCurrencyCode() {
		return sale.getBranchCurrency().getCode();
	}

	public double getGrandDiscountAmount() {
		return sale.getDiscountAmount().doubleValue();
	}

	public double getGrandDiscountTotal() {
		return sale.getDiscountTotal().doubleValue();
	}

	public double getGrandTotal() {
		return sale.getTotal();
	}

	public BigDecimal getTotalBeforeDiscount() {
		return sale.getSubTotal();
	}

	public String getPaymentChannel() {
		if (sale.getPaymentChannel() == null)
			return "";
		return sale.getPaymentChannel().getName();
	}
	public String getRemark() {
		if (sale.getRemark() == null)
			return "";
		return sale.getRemark();
	}
	public Long getBillNumber() {
		if (sale.getBillNumber() == null)
			return (long) 0;
		return sale.getBillNumber();
	}

}
