package com.spring.miniposbackend.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction_types")
@Getter
@Setter
public class TransactionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "name_kh", nullable = false)
	private String nameKh;

	@Column(name = "color", nullable = false)
	private String color;

	@Column(name = "enable", nullable = false)
	private boolean enable;

	@Column(name = "operater", nullable = false)
	private boolean operater;

}
