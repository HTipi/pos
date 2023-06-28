package com.spring.miniposbackend.model.admin;

import java.math.BigDecimal;
import java.util.List;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.vladmihalcea.hibernate.type.array.ListArrayType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_branches", uniqueConstraints = @UniqueConstraint(columnNames = { "item_id", "branch_id" }))
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Setter
@Getter
@DynamicUpdate
public class ItemBranch extends AuditModel {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false)
	@JsonIgnore
	Item item;

	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Column(name = "costing", nullable = true, precision = 10, scale = 2)
	@ColumnDefault("0")
    private BigDecimal costing;
	
	@Column(name = "whole_price", nullable = true, precision = 10, scale = 2)
	@ColumnDefault("0")
    private BigDecimal wholePrice;

	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short discount;

	@Column(name = "stock_in", nullable = false)
	@ColumnDefault("0")
	private Long stockIn;

	@Column(name = "stock_out", nullable = false)
	@ColumnDefault("0")
	private Long stockOut;

	@Type(type = "list-array")
	@Column(name = "add_on", columnDefinition = "bigint[]")
	private List<Long> addOnItems;
	
	@Type(type = "list-array")
	@Column(name = "add_on_inven", columnDefinition = "bigint[]")
	private List<Long> addOnInven;
	
	
	@Column(name = "inven_qty", nullable = true)
	@ColumnDefault("1")
	private Short invenQty;
	
	@JsonIgnore
	@OneToMany(mappedBy = "itemBranchPromotionIdentity.itemBranch", fetch = FetchType.LAZY)
	List<ItemBranchPromotion> itemBranchPromotions;
	
	@Column(name = "point", nullable = true)
	@ColumnDefault("0")
	private Short point;

	@Column(name = "reward", nullable = true)
	@ColumnDefault("0")
	private Short reward;
	
	@Column(name = "add_percent", nullable = true)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short addPercent;
	

	public Long getId() {
		return id;
	}

	public Long getItem_Id() {
		return item.getId();
	}

	@JsonIgnore
	public Item getItem() {
		return item;
	}

	@JsonIgnore
	public Branch getBranch() {
		return branch;
	}

	public String getCode() {
		return item.getCode();
	}

	public String getType() {
		return item.getType();
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
//	public BigDecimal getPrice() {
//		if(useItemConfiguration) {
//			return item.getPrice();
//		}else {
//			return price;
//		}
//	}

//	public double getDiscount() {
//		if (useItemConfiguration) {
//			return item.getDiscount();
//		} else {
//			return discount;
//		}
//	}

	public boolean isEnable() {
		return enable;
	}

	public boolean isStock() {
		return item.isStock();
	}

	public Integer getItemTypeId() {
		return item.getItemType().getId();
	}

	public String getItemTypeName() {
		return item.getItemType().getName();
	}

	public String getItemTypeNameKh() {
		return item.getItemType().getNameKh();
	}

	public Short getVersion() {
		return item.getVersion();
	}

	public Long getItemBalance() {
		return stockIn - stockOut;
	}

	public String getBranchName() {
		return branch.getNameKh();
	}

	public String getPhoto() {
		return item.getPhoto();
	}
	public boolean getVisible() {
		
		return item.getItemType().isVisible();
	}
	public List<ItemBranchPromotion> getItemBranchPromotion(){
		
		return itemBranchPromotions;
	}

}
