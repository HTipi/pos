package com.spring.miniposbackend.model.sale;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
import com.spring.miniposbackend.model.admin.User;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "receipt_number", nullable = false, length = 32)
	private String receiptNumber;

	@Column(name = "seat_name", nullable = false, length = 32)
	private String seatName;

	@Column(name = "value_date", nullable = false)
	private Date valueDate;

	@Column(name = "reverse", nullable = false)
	@ColumnDefault("false")
	private boolean reverse;

	@Column(name = "reverse_date", nullable = true)
	private Date reverseDate;

	@Column(name = "sub_total", nullable = true, length = 10, precision = 2)
	@ColumnDefault("0")
	private BigDecimal subTotal;

	@Column(name = "discount_total", nullable = true, length = 10, precision = 2)
	@ColumnDefault("0")
	private BigDecimal discountTotal;

	public Double getTotal() {
		return subTotal.doubleValue() - discountTotal.doubleValue();
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	@Column(name = "cash_in", nullable = true, length = 10, precision = 2)
	@ColumnDefault("0")
	private Double cashIn;

	@Column(name = "change", nullable = true, length = 10, precision = 2)
	@ColumnDefault("0")
	private Double change;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cur_id", nullable = true)
	@JsonIgnore
	private BranchCurrency branchCurrency;

}
