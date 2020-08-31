package com.spring.miniposbackend.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_types")
@Setter
@Getter
public class StockType {

	@Id
	@Column(name = "code", nullable = false, length = 32)
	private String code;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@Column(name = "name_kh", nullable = false, length = 128)
	private String nameKh;
}
