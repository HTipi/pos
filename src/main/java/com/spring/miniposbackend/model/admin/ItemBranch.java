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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

@Entity
@Table(name = "item_branches", uniqueConstraints = @UniqueConstraint(columnNames = {"item_id","branch_id"}))
@DynamicUpdate
public class ItemBranch extends AuditModel{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", nullable = false)
	@JsonIgnore
	Item item;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	Branch branch;
	
	@Column(name = "use_item_configuration", nullable = false)
    @ColumnDefault("true")
    private boolean useItemConfiguration;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
	
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
	
	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
    private Short discount;
	
	public Long getId() {
		return id;
	}
	
	public String getCode() {
		return item.getCode();
	}
	public String getName() {
		return item.getName();
	}
	public String getNameKh() {
		return item.getNameKh();
	}
	public String getImage() {
		return item.getImage();
	}
	public BigDecimal getPrice() {
		if(useItemConfiguration) {
			return item.getPrice();
		}else {
			return price;
		}
	}
	
	public Short getDiscount() {
		if(useItemConfiguration) {
			return item.getDiscount();
		}else {
			return discount;
		}
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public Integer getItemTypeId() {
		return item.getItemType().getId();
	}
}
