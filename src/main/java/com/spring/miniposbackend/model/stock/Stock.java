package com.spring.miniposbackend.model.stock;

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
import com.spring.miniposbackend.model.admin.StockType;
import com.spring.miniposbackend.model.admin.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@Setter
@Getter
public class Stock extends AuditModel{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "value_date", nullable = false, updatable = false)
    private Date valueDate;
	
	@Column(name = "description", nullable = false)
    private String description;
	
	@Column(name = "posted", nullable = false)
    @ColumnDefault("false")
    private boolean posted;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_type_code", nullable = false)
    @JsonIgnore
    private StockType stockType;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
	
	public String getStockCode() {
		return stockType.getCode();
	}
}