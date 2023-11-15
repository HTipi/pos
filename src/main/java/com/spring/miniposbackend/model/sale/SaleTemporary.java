package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import com.spring.miniposbackend.model.customer.Customer;
import com.spring.miniposbackend.modelview.dashboard.ItemPromotion;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.ItemBranchPromotion;
import com.spring.miniposbackend.model.admin.PaymentChannel;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "value_date", nullable = false, updatable = false)
	private Date valueDate;

	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "quantity", nullable = false)
	@ColumnDefault("1")
	private float quantity;

	@Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discountAmount;

	@Column(name = "bill_number", nullable = true)
	@ColumnDefault("0")
	private Long billNumber;

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
	@JoinColumn(name = "invoice_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Invoice invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_sale_id", nullable = true)
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SaleTemporary parentSaleTemporary;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_sale_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<SaleTemporary> addOns;

	@ManyToOne
	@JoinColumn(name = "payment_channel_id", nullable = true)
	@JsonIgnore
	private PaymentChannel paymentChannel;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = true)
	private Customer customer;

	@Type(type = "list-array")
	@Column(name = "add_promo", columnDefinition = "int[]",nullable = true)
	private List<Integer> addPromo;
	
	@Column(name = "qr_num", nullable = true)
	private UUID qrnumber;
	
	@Column(name = "add_percent", nullable = true)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short addPercent;
	
	@Column(name = "remark", nullable = true)
	private String remark;

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

	public Long getItemCorpId() {
		return itemBranch.getItem_Id();
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

	public Long getInvoice_id() {
		if (invoice == null) {
			return null;
		}
		return invoice.getId();
	}

	public boolean getStock() {

		return itemBranch.getItem().isStock();
	}

	public Integer getItemTypeId() {
		return itemBranch.getItemTypeId();
	}

	public Integer getPaymentChannelId() {
		if (paymentChannel == null)
			return 0;
		return paymentChannel.getId();
	}
	public short getReward() {
		return itemBranch.getReward();
	}
	public short getPoint() {
		return itemBranch.getPoint();
	}
	public List<ItemPromotion> getItemPromotion() {
		List<ItemPromotion> items = new ArrayList<ItemPromotion>();
		ItemPromotion pro = null;
		List<Integer> proList = addPromo == null ? new ArrayList<Integer>() : addPromo;
		List<ItemBranchPromotion> itemPro = itemBranch.getItemBranchPromotions();
		for (int i = 0; i < proList.size(); i++) {
			for (int j = 0; j < itemPro.size(); j++) {

				if (proList.get(i) == itemBranch.getItemBranchPromotions().get(j).getBranchPromotionId()) {
					pro = new ItemPromotion();
					pro.setBranchPromotionId(itemPro.get(j).getBranchPromotionId());
					pro.setPromotionCode(itemPro.get(j).getPromotionCode());
					pro.setPromotionDiscount(itemPro.get(j).getPromotionDiscount());
					pro.setPromotionName(itemPro.get(j).getPromotionName());
					items.add(pro);
					break;
				}
			}

		}
		return items;
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
	public Double getSubTotalNoDis() {
		return Math.round(price.doubleValue() * quantity * Math.pow(10, getDecimalPlace()))
				/ Math.pow(10, getDecimalPlace());
	}

	public double getTotal() {
		return getSubTotal() - getDiscountTotal();
	}
	public String getType() {
		return itemBranch.getType();
	}
}
