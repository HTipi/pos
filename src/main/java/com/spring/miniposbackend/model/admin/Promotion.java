package com.spring.miniposbackend.model.admin;


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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "promotions")
@Setter @Getter
@DynamicUpdate
public class Promotion extends AuditModel{
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false,length = 128)
    private String name;
	
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	
	@Column(name = "_date", nullable = false)
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "corporate_id",nullable = false)
    @JsonIgnore
    private Corporate corporate;
	
	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
    private Short discount;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable = true;
}
