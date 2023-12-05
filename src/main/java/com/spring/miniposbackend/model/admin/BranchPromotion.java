package com.spring.miniposbackend.model.admin;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "branch_promotions")
@Setter
@Getter
public class BranchPromotion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "code", nullable = false,length = 3)
    private String code;
	
	@Column(name = "name", nullable = true)
    private String name;
	
	@Column(name = "name_kh", nullable = true)
    private String nameKh;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;
	

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;
	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
	private Short discount;

	@Column(name = "enable", nullable = false)
	@ColumnDefault("true")
	private boolean enable = true;
}
