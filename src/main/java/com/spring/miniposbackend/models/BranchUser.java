package com.spring.miniposbackend.models;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "branchs_users")
@Getter @Setter
public class BranchUser extends AuditModel{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;


}
