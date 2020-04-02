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
@Table(name = "currency")
@Setter @Getter
@DynamicUpdate
public class Currency extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "code", nullable = false,length = 3)
    private String code;
	
	@Column(name = "name", nullable = false,length = 32)
    private String name;
	
	@Column(name = "symbol", nullable = false,length = 1)
    private String symbol;
	
	@Column(name = "home", nullable = false)
    @ColumnDefault("false")
    private boolean home;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;
}
