package com.spring.miniposbackend.model.admin;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Setter @Getter
@DynamicUpdate
public class Item extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "code", nullable = false,length = 32)
    private String code;
	
	@Column(name = "type", nullable = true,length = 32)
    private String type;
	
	@Column(name = "name", nullable = false,length = 128)
    private String name;
	
	@Column(name = "name_kh", nullable = false, length = 128)
    private String nameKh;
	
	@Column(name = "image",length = 64)
    private String image;
	@Column(name = "photo",length = 64)
    private String photo;
	
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
	
	@Column(name = "costing", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
    private BigDecimal costing;
	
	@Column(name = "whole_price", nullable = false, precision = 10, scale = 2)
	@ColumnDefault("0")
    private BigDecimal wholePrice;
	@Transient
	private String sub;
	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
    private Short discount;
	
	@Column(name = "is_stock", nullable = false)
    @ColumnDefault("false")
    private boolean stock;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", nullable = false)
    @JsonIgnore
    private ItemType itemType;
	
	@Column(name ="version", nullable = false)
	private Short version= 0;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
	
	@Column(name = "bar_code", nullable = true,length = 128)
    private String barCode;
	
	public int getItemType_Id() {
        return itemType.getId();
    }
	public Long getItem_Id() {
		return id;
	}
	@Transient
	private short point;
}
