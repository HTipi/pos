package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;
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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.BranchCurrency;
import com.spring.miniposbackend.model.admin.PaymentChannel;
import com.spring.miniposbackend.model.admin.User;
import com.spring.miniposbackend.model.customer.Customer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales")
@Setter
@Getter
@DynamicUpdate
public class Sale extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "receipt_number", nullable = false, length = 32)
	private String receiptNumber;
	
	@Column(name = "bill_number", nullable = true)
	@ColumnDefault("0")
    private Long billNumber;

	@Column(name = "seat_name", nullable = false, length = 32)
	private String seatName;

	@Column(name = "value_date", nullable = false)
	private Date valueDate;

	@Column(name = "reverse", nullable = false)
	@ColumnDefault("false")
	private boolean reverse;

	@Column(name = "reverse_date", nullable = true)
	private Date reverseDate;

	@Column(name = "sub_total", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal subTotal;
	
	@Column(name = "discount_amount", nullable = false,  precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discountAmount;

	@Column(name = "discount_sale_detail", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
	private BigDecimal discountSaleDetail;
	
	public Double getDiscountTotal() {
		return discountSaleDetail.doubleValue() + discountAmount.doubleValue();
	}

	public Double getTotal() {
		return subTotal.doubleValue() - getDiscountTotal();
	}
	public Double getTotalVS() {
		return subTotal.doubleValue() - getDiscountTotal() + vat + serviceCharge;
	}
	public String getFullName() {
		return user.getFullName();
	}
	@Column(name = "remark", nullable = true)
	private String remark;
	@ManyToOne
	@JoinColumn(name = "payment_channel_id", nullable = true)
	@JsonIgnore
	private PaymentChannel paymentChannel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = true)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@Column(name = "cash_in", nullable = false,  precision = 10, scale = 2)
	@ColumnDefault("0")
	private Double cashIn;

	@Column(name = "change", nullable = false,  precision = 10, scale = 2)
	@ColumnDefault("0")
	private Double change;
	
	@Column(name = "service_charge", nullable = true,  precision = 10, scale = 2)
	@ColumnDefault("0")
	private Double serviceCharge;
	
	@Column(name = "vat", nullable = true,  precision = 10, scale = 2)
	@ColumnDefault("0")
	private Double vat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cur_id", nullable = false)
	@JsonIgnore
	private BranchCurrency branchCurrency;

}
