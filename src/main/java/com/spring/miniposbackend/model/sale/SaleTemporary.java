package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.User;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.Seat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales_temp")
@Setter
@Getter
public class SaleTemporary extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "value_date", nullable = false, updatable = false)
	private Date valueDate;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "quantity", nullable = false)
	@ColumnDefault("1")
	private Short quantity;

	@Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discountAmount;

	@Column(name = "discount_percentage", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short discountPercentage;

	@Column(name = "is_printed", nullable = false)
	@ColumnDefault("false")
	private boolean isPrinted;

	@Column(name = "cancel", nullable = false)
	@ColumnDefault("false")
	private boolean cancel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", nullable = true)
	@JsonIgnore
	private Seat seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "useredit_id", nullable = false)
	@JsonIgnore
	private User userEdit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_branch_id", nullable = false)
	@JsonIgnore
	private ItemBranch itemBranch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_sale_id", nullable = true)
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SaleTemporary parentSaleTemporary;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_sale_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<SaleTemporary> addOns;

	public List<Long> getAddOnItems() {
		return itemBranch.getAddOnItems();
	}

	public String getItemCode() {
		return itemBranch.getCode();
	}

	public String getItemName() {
		return itemBranch.getNameKh();
	}

	public Long getItemId() {
		return itemBranch.getId();
	}

	public Integer getSeat_id() {
		if (seat == null)
			return 0;
		return seat.getId();
	}

	public String getSeat_name() {
		if (seat == null)
			return "";
		return seat.getName();
	}

	public Integer getUser_id() {
		return user.getId();
	}

	public Integer getUseredit_id() {
		return userEdit.getId();
	}

	private Integer getDecimalPlace() {
		Optional<BranchCurrency> branchCurrency = itemBranch.getBranch().getBranchCurrencies().stream()
				.filter(x -> x.isHome()).findFirst();
		if (branchCurrency.isPresent()) {
			return branchCurrency.get().getDecimalPlace();
		} else {
			return 0;
		}
	}

	public Double getDiscountTotal() {
		return Math.round(price.doubleValue() * quantity * discountPercentage / 100 * Math.pow(10, getDecimalPlace()))
				/ Math.pow(10, getDecimalPlace()) + discountAmount.doubleValue();
	}

	public Double getSubTotal() {
		return Math.round(price.doubleValue() * quantity * Math.pow(10, getDecimalPlace()))
				/ Math.pow(10, getDecimalPlace());
	}

	public double getTotal() {
		return getSubTotal() - getDiscountTotal();
	}
}
