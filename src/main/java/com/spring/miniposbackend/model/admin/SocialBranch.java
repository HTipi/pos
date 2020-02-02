package com.spring.miniposbackend.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "socials_branchs")
@EqualsAndHashCode(callSuper = false)
@Data
@DynamicUpdate
public class SocialBranch extends AuditModel {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "social_id")
    @JsonIgnore
    private Social social;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "corporate_id", nullable = true)
    @JsonIgnore
    private Corporate corporate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;

    @Column(name = "url", nullable = true)
    private String url;

    public Date getUpdatedAt() {
        return updatedAt;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

}
