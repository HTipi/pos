
package com.spring.miniposbackend.model.sale;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;
import com.spring.miniposbackend.model.admin.User;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invoices")
@Setter
@Getter
@DynamicUpdate
public class Invoice extends AuditModel {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "remark", nullable = false)
	private String remark;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<SaleTemporary> saleTemporaries;
	@Transient
	private double grandTotal;

	public double getGrandTotal() {
		grandTotal = 0;
		saleTemporaries.forEach(saleTemp -> {
			if (saleTemp.getParentSaleTemporary() == null) {
				grandTotal += saleTemp.getTotal();
			}
					
		});
		return grandTotal;
	}
	public String getCustomer() {
		if(saleTemporaries.size() == 0)
			return "";
		if(saleTemporaries.get(0).getCustomer() == null)
			return "";
		return saleTemporaries.get(0).getCustomer().getNameKh() + " " + saleTemporaries.get(0).getCustomer().getPrimaryPhone();
	}

	public Date getValueDate() {
		return createdAt;
	}
}
