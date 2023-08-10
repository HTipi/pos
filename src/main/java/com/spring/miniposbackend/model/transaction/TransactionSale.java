package com.spring.miniposbackend.model.transaction;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.sale.Sale;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction_sales")
@Getter
@Setter
public class TransactionSale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false)
	@JsonIgnore
	private Sale sale;

	@OneToOne
	@JoinColumn(name = "transaction_id", nullable = false)
	@JsonIgnore
	private Transaction transaction;

	@Column(name = "qr_num", nullable = true)
	private UUID qrNumber;

	public long getSaleId() {
		return sale.getId();
	}

}
