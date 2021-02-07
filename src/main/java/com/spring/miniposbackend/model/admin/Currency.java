package com.spring.miniposbackend.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.spring.miniposbackend.model.AuditModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "currencies")
@Setter @Getter
@DynamicUpdate
public class Currency extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "code", nullable = false,length = 3)
    private String code;
	
	@Column(name = "name", nullable = false,length = 32)
    private String name;
	
	@Column(name = "name_kh", nullable = false,length = 32)
    private String nameKh;
	
	@Column(name = "symbol", nullable = false,length = 1)
    private String symbol;
	
	@Column(name = "decimal_place", nullable = false)
    private Integer decimalPlace;
	
	@Column(name = "min_denomination", nullable = false)
    private Float minDenom;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
}
