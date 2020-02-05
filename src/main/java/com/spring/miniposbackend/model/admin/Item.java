package com.spring.miniposbackend.model.admin;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items")
@Setter @Getter
@DynamicUpdate
public class Item extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "code", nullable = false,length = 32)
    private String code;
	
	@Column(name = "name", nullable = false,length = 128)
    private String name;
	
	@Column(name = "name_kh", nullable = false)
    private String nameKh;
	
	@Column(name = "image", length = 10, precision = 2)
    private String image;
	
	@Column(name = "price", nullable = false, length = 10, precision = 2)
    private Float price;
	
	@Column(name = "discount", nullable = false,length = 3)
    private Integer discount;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_type_id", nullable = false)
    @JsonIgnore
    private ItemType itemType;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
}
