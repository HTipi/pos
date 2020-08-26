package com.spring.miniposbackend.model.expense;

import java.math.BigDecimal;
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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "expense")
@Setter
@Getter
@DynamicUpdate
public class Expense extends AuditModel {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "expense_amt", nullable = false, precision = 10, scale = 2)
	private BigDecimal expenseAmt;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "expense_type_id", nullable = false)
	@JsonIgnore
	private ExpenseType expenseType;

	@Column(name = "value_date", nullable = false)
    private Date valueDate;
	
	@Column(name = "expense_remark", nullable = false)
    private String remark;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

	@Column(name = "reverse", nullable = false)
    @ColumnDefault("false")
    private boolean reverse;
	
	@Column(name = "reverse_date", nullable = true)
    private Date reverseDate;
	
	public String getExpenseTypeName() {
		return expenseType.getName();
	}


}
