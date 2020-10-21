package com.spring.miniposbackend.model.stock;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.ItemBranch;
import com.spring.miniposbackend.model.admin.StockType;
import com.spring.miniposbackend.model.admin.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_entries")
@Setter
@Getter
public class StockEntry extends AuditModel{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_type_code", nullable = false)
    @JsonIgnore
    private StockType stockType;
	
	@Column(name = "value_date", nullable = false, updatable = false)
    private Date valueDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_branch_id", nullable = false)
    @JsonIgnore
    private ItemBranch itemBranch;
	
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
	
	@Column(name = "discount", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
    private Short discount;

    @Column(name = "quantity", nullable = false)
    @ColumnDefault("1")
    private Short quantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    @JsonIgnore
    private Stock stock;
    
    @Column(name = "total", nullable = true, precision = 10, scale = 2)
    private BigDecimal total;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
	
	public String getItemCode() {
		return itemBranch.getCode();
	}
	
	public String getItemName() {
		return itemBranch.getName();
	}
	public Long getStockBalance() {
		return itemBranch.getItemBalance();
	}
	public String getStockCode() {
		return stockType.getCode();
	}
	public Long getItemId() {
		return itemBranch.getId();
	}
}
