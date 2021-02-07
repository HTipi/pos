package com.spring.miniposbackend.model.customer;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Branch;

import javax.persistence.*;

@Entity
@Table(name = "claim_points")
@Setter @Getter
public class ClaimPoint extends AuditModel{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", nullable = false)
    private CustomerPoint customerPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "amount", length = 10, precision = 2)
    private Float amount;

    @Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;
}
