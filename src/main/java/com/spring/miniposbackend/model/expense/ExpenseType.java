package com.spring.miniposbackend.model.expense;

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
@Table(name = "expense_type")
@Setter
@Getter
@DynamicUpdate
public class ExpenseType extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "code", nullable = false, length = 3)
	private String code;

	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "name_kh", nullable = false, length = 32)
	private String nameKh;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;

	@Column(name = "enable", nullable = false)
	@ColumnDefault("true")
	private boolean enable;
}
