package com.spring.miniposbackend.model.admin;

import java.math.BigDecimal;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "branch_currencies",  uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id","currency_id"}))
@Setter @Getter
@DynamicUpdate
public class BranchCurrency extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "rate", nullable = false, precision = 24, scale = 12)
    private BigDecimal rate;
	
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "currency_id")
    @JsonIgnore
    private Currency currency;
	
	@Column(name = "home_currency", nullable = false)
    @ColumnDefault("false")
    private boolean isHome;
	
	public String getCode() {
		return currency.getCode();
	}
	
	public String getName() {
		return currency.getName();
	}
	public String getNameKh() {
		return currency.getNameKh();
	}
	
	public String getSymbol() {
		return currency.getSymbol();
	}
	
}
