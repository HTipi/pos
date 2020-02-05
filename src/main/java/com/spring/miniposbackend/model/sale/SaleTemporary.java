package com.spring.miniposbackend.model.sale;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Item;
import com.spring.miniposbackend.model.admin.Seat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales_temp")
@Setter @Getter
public class SaleTemporary {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "value_date", nullable = false)
    private Date valueDate;
	
	@Column(name = "price", nullable = false, length = 10, precision = 2)
    private Float price;
	
	@Column(name = "quantity", nullable = false)
    private Integer quantity;
	
	@Column(name = "discount", nullable = false, length = 10, precision = 2)
    private Float discount;
	
	@Column(name = "total", nullable = false, length = 10, precision = 2)
    private Float total;
	
	@Column(name = "is_printed", nullable = false)
    @ColumnDefault("false")
    private boolean isPrinted;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
	
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
	
	public String getItemNameKh() {
		return item.getNameKh();
	}
}
