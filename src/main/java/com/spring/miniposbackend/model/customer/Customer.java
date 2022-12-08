package com.spring.miniposbackend.model.customer;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.Sex;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "customers")
@Getter @Setter
@DynamicUpdate
public class Customer extends AuditModel {


	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "name_kh", nullable = false)
    private String nameKh; 
    
    @Column(name = "primary_phone", nullable = false)
    private String primaryPhone;
    
    @Column(name = "secondary_phone", nullable = true)
    private String secondaryPhone;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sex_id", nullable = true)
	@JsonIgnore
	private Sex sex;
    
    @Column(name = "point_balance", nullable = false)
    private Integer pointBalance;
    
    @Column(name = "point_branch", nullable = false)
    private boolean pointBranch;
    
	@Column(name = "discount", nullable = false)
	@Min(0)
	@Max(100)
	@ColumnDefault("0")
    private Short discount;
    
    @Column(name = "enable", nullable = false)
    private boolean enable = true;
    
    public int getSexId() {
    	
    	return sex.getId();
    }
    
}