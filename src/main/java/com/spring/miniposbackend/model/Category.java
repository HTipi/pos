package com.spring.miniposbackend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(callSuper = false)
@Data
@DynamicUpdate
public class Category extends AuditModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "name_kh", nullable = false)
    private String nameKh;
    
    @Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;
    
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Corporate> corporates = new ArrayList<Corporate>();
}