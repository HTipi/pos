package com.spring.miniposbackend.model.admin;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats")
@Setter @Getter
@DynamicUpdate
public class Seat extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false,length = 32)
    private String name;
	
	@Column(name = "sequence", nullable = false)
    private Integer sequence=1;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
	
	@Column(name = "free", nullable = true)
    @ColumnDefault("false")
    private boolean free;
	
	@Column(name = "printed", nullable = true)
    @ColumnDefault("false")
    private boolean printed;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;
}
