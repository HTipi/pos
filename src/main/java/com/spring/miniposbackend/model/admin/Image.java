package com.spring.miniposbackend.model.admin;


import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.miniposbackend.model.AuditModel;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter @Setter
public class Image extends AuditModel{

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", nullable = false)
    private UUID id;
	
	@Column(name = "name", nullable = false, length = 128)
    private String name;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "corporate_id")
//    @JsonIgnore
//    private Corporate corporate;
}
