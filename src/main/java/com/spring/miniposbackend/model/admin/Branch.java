package com.spring.miniposbackend.model.admin;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@Column(name = "name_kh", nullable = false)
	private String nameKh;

	@Column(name = "logo", length = 64)
	private String logo;

	@Column(name = "telephone", nullable = false, length = 20)
	private String telephone;
	
	@Column(name = "address_desc", nullable = true, length = 100)
	private String addressDesc;

	@Column(name = "is_main", nullable = false)
	@ColumnDefault("false")
	private boolean isMain;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = true)
	@JsonIgnore
	private Address address;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = true)
	@JsonIgnore
	private List<BranchCurrency> branchCurrencies;

	@ManyToOne
	@JoinColumn(name = "corporate_id", nullable = false)
	@JsonIgnore
	private Corporate corporate;

	@OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<DeliveryContact> deliveryContacts = new ArrayList<DeliveryContact>();
	

	@OneToMany(mappedBy = "branchPaymentIdentity.branch", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<BranchPaymentChannel> branchPaymentChannels = new ArrayList<BranchPaymentChannel>();


	@Column(name = "enable", nullable = false)
	@ColumnDefault("true")
	private boolean enable;
}
