package com.spring.miniposbackend.model.admin;


import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	@Column(name = "name", nullable = false, length = 64)
    private String name;
	
	@Column(name="type", nullable = false)
	private String type;
	
	@Column(name="image", nullable = true)
	private String image;
	
	@Transient
	private byte[] base64;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_type_id",nullable = false)
    @JsonIgnore
    private ImageType imageType;
}
