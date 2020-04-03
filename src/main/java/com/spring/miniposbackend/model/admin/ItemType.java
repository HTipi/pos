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
@Table(name = "item_types")
@Setter @Getter
@DynamicUpdate
public class ItemType extends AuditModel{
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false,length = 128)
    private String name;
	
	@Column(name = "name_kh", nullable = false,length = 128)
    private String nameKh;
	
	@Column(name = "image",length = 64)
    private String image;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "corporate_id",nullable = false)
    @JsonIgnore
    private Corporate corporate;
	
	@Column(name ="version", nullable = false)
	private Short version= 1;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
}
