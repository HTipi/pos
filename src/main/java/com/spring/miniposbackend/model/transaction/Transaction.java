package com.spring.miniposbackend.model.transaction;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.account.Account;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name="previous_balance", nullable = false)
	private BigDecimal previousBalance;
	
	@Column(name="current_balance", nullable = false)
	private BigDecimal currentBalance;
	
	@Column(name="transaction_amount", nullable = false)
	private BigDecimal transactionAmount;
	
	@Column(name = "value_date", nullable = false)
	private Date valueDate;
	
	@Column(name="remark", nullable = true)
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "transaction_type_id", nullable = false)
	@JsonIgnore
	private TransactionType transactionType;
	
	@OneToOne(fetch = FetchType.LAZY,
            mappedBy = "transaction")
	@JsonIgnore
    private TransactionSale transactionSale;
	
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	@JsonIgnore
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	public boolean getOperator() {
		
		return transactionType.isOperater();
	}
	
	public long getSaleId()
	{
		if(transactionSale != null)
		{
			return transactionSale.getSaleId();
		}
		return 0;
	}
	public int getTransactionTypeId() {
		
		return transactionType.getId();
	}
	public String getTransactionName()
	{
		return transactionType.getName();
	}
	public String getTransactionNameKh()
	{
		return transactionType.getNameKh();
	}
	
	public String getReceiptNumber()
	{
		if(transactionSale != null)
		{
			return transactionSale.getSale().getReceiptNumber();
		}
		return "";
	}

}
