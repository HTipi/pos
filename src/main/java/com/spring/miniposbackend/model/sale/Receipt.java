package com.spring.miniposbackend.model.sale;


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
import com.spring.miniposbackend.model.admin.Branch;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "receipts")
@Setter @Getter
@DynamicUpdate
public class Receipt extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "receipt_number", nullable = false,length = 32)
    private Long receiptNumber;
	
	@Column(name = "bill_number", nullable = true)
	@ColumnDefault("0")
    private Long billNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", unique = true, nullable = false)
    @JsonIgnore
    private Branch branch;
	
}
