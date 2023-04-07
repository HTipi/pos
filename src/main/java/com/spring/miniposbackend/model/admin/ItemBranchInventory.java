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

@Setter
@Getter
@Table(name="item_branch_inventory")
@Entity
public class ItemBranchInventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_branch_inventory_id", nullable = false)
	@JsonIgnore
	private ItemBranch inventories;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_branch_id", nullable = false)
	@JsonIgnore
	private ItemBranch itembranch;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;
	
	@Column(name = "qty", nullable = false)
	private int qty;
	
	public Long getItemBranchId() {
		return itembranch.getId();
	}
	public Long getInvenId() {
		return inventories.getId();
	}
}
