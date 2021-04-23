package com.spring.miniposbackend.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "printers")
@Getter
@Setter
public class PrinterItemType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "printer_id", nullable = false)
	@JsonIgnore
	Printer printer;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_type_id", nullable = false)
	@JsonIgnore
	ItemType itemType;
	
	public Integer getItemTypeId() {
		return itemType.getId();
	}
	
}
