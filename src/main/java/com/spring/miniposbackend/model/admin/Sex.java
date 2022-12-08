package com.spring.miniposbackend.model.admin;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sex")
@Setter @Getter
public class Sex {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@Column(name = "name", nullable = false,length = 10)
    private String name;
	
	@Column(name = "name_kh", nullable = false,length = 10)
    private String nameKh;
	
	@Column(name = "code", nullable = false,length = 1)
    private String code;
	
	@Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;
}
