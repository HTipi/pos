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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.Seat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales_temp")
@Setter @Getter
public class SaleTemporary {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "value_date", nullable = false, updatable = false)
    private Date valueDate;
	
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
	
	@Column(name = "quantity", nullable = false)
	@ColumnDefault("1")
    private Short quantity;
	
	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
    private Short discount;
	
	@Column(name = "is_printed", nullable = false)
    @ColumnDefault("false")
    private boolean isPrinted;
	
	@Column(name = "cancel", nullable = false)
    @ColumnDefault("false")
    private boolean cancel;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", nullable = false)
    @JsonIgnore
    private Seat seat;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore
    private Item item;
	
	public String getItemCode() {
		return item.getCode();
	}
	
	public String getItemName() {
		return item.getName();
	}
	
	public double getDiscountAmount() {
		return Math.round(price.doubleValue()*quantity*discount/100*100)/100.0;
	}
	
	public double getSubTotal() {
		return Math.round(price.doubleValue()*quantity*100)/100.0;
	}
	public double getTotal() {
		return getSubTotal()-getDiscountAmount();
	}
}
