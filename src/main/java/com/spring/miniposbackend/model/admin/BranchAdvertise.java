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
import org.hibernate.annotations.ColumnDefault;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="branch_advertises")
@Setter
@Getter
public class BranchAdvertise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Integer id;
	
	@Column(name = "image",length = 64 , nullable = false)
    private String image;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;
	
	@Column(name = "sort_order", nullable = false)
	@ColumnDefault("0")
	private Integer sortOrder;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	private Branch branch;
	
}
