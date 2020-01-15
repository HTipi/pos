package com.spring.miniposbackend.models;

import java.util.ArrayList;
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

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "corporates")
@Getter @Setter
public class Corporate extends AuditModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "name_kh", nullable = false)
    private String nameKh;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    
    @OneToMany(mappedBy = "corporate", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Branch> branches = new ArrayList<Branch>();
    
    @Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;
}